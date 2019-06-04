package br.com.financeiropapw.categoria;

import java.util.List;

import br.com.financeiropapw.usuario.Usuario;
import br.com.financeiropapw.util.DAOFactory;

public class CategoriaRN {
	
	private CategoriaDAO categoriaDAO;
	
	public CategoriaRN() {
		this.categoriaDAO = DAOFactory.criarCategoriaDAO();
	}
	
	public List<Categoria> listar(Usuario usuario) {
		return this.categoriaDAO.listar(usuario);
	}
	
	public Categoria carregar(Integer categoria) {
		return this.categoriaDAO.carregar(categoria);
	}
	
	public void replicarFator(Categoria categoria, int fator) { //Método responsável por repassar a mudança de fator para todas as hierarquias abaixo da categoria alterada.
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				filho.setFator(fator);
				this.categoriaDAO.salvar(filho);
				this.replicarFator(filho, fator);
			}
		}
	}
	
	public Categoria salvar(Categoria categoria) {
		Categoria pai = categoria.getPai();
		if (pai == null) { //Teste para saber se a categoria que está sendo salva tem uma categoria pai. 
			String msg = "A categoria " + categoria.getDescricao() + " deve ter um pai definido"; //Se não tiver será apresentada uma mensagem informando que a categoria deve ter um pai definido.
			throw new IllegalArgumentException(msg); //Método responsável pela apresentação da mensagem.
		}
		
		categoria.setFator(pai.getFator()); //Transferência do fator da categoria pai para a categoria filha, netos etc.
		categoria = this.categoriaDAO.salvar(categoria); //Persistência da categoria no BD.
		
		boolean mudouFator = pai.getFator() != categoria.getFator(); //Se o fator da categoria pai for diferente da categoria do filho será instanciado o atributo "mudouFator" receberá o valor TRUE.
		
		if (mudouFator) { //Caso o atributo "mudouFator" seja true
			categoria = this.carregar(categoria.getCodigo()); //A categoria filho será carregada pelo seu código
			this.replicarFator(categoria, categoria.getFator()); //Será executado o método "replicarFator" para repassar a mudança de fator para todas as hierarquias abaixo da categoria alterada.
		}
		return categoria;
	}
	
	public void excluir(Categoria categoria) { //Método responsável pela exclusão da categoria
		this.categoriaDAO.excluir(categoria); //Exclusão realizada no BD.
	}
	
	public void excluir(Usuario usuario) { //Médoto responsável pela exclusão por usuário. De modo que ao ser exlcuído um usuário, automaticamente todas categorias serão excluídas juntamente.
		List<Categoria> lista = this.listar(usuario); //Listagem das categorias por usuário
		for (Categoria categoria:lista) { //Realiza uma carga de todas as categorias
			this.categoriaDAO.excluir(categoria); //Exclusão realizada no BD.
		}
	}
	
	public void salvaEstruturaPadrao(Usuario usuario) { //Método responsável por salvar uma estrutura padrão no início do acesso do usuário.

		Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
		despesas = this.categoriaDAO.salvar(despesas);
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Moradia", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Alimentação", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Vestuário", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Deslocamento", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Educação", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Saúde", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Lazer", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Despesas Financeiras", -1));

		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		receitas = this.categoriaDAO.salvar(receitas);
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Salário", 1));
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Restituições", 1));
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Rendimento", 1));
	}

}
