package br.com.financeiropapw.web;

import java.util.*;
import javax.faces.bean.*;
import javax.faces.model.SelectItem;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.financeiropapw.categoria.*;

@ManagedBean(name = "categoriaBean")
@RequestScoped
public class CategoriaBean {

	private TreeNode categoriasTree; //Ávores de categoria do primefaces.
	private Categoria editada = new Categoria(); //Objeto que alimentará o formulário e receberá a categoria selecionada na árvore.
	private List<SelectItem> categoriasSelect; //Lista que alimentará a caixa de seleção de categoria pai.
	private boolean mostraEdicao = false;

	@ManagedProperty(value = "#{contextoBean}")
	private ContextoBean contextoBean;

	public void novo() { //Método que será utilizado tanto pelo botão "Novo" quanto pelo "Nova subcategoria".
		Categoria pai = null; //Atribuição da categoria pai como null.
		if (this.editada.getCodigo() != null) { //Verifica se o código do objeto "editada" é diferente de null.
			CategoriaRN categoriaRN = new CategoriaRN(); //Caso seja, será instanciado um objeto "categoriaRN" da clase CategoriaRN.
			pai = categoriaRN.carregar(this.editada.getCodigo()); //O novo objeto instanciado irá carregar o código do objeto "editada" da classe Categoria e será atribuído a variável "Pai".
		}
		this.editada = new Categoria(); //O objeto "editada" será instanciado em uma nova categoria.
		this.editada.setPai(pai); //O objeto "editada" se torna uma categoria pai.
		this.mostraEdicao = true; //A nova categoria terá atribuição como "true"
	}

	public void selecionar(NodeSelectEvent event) { //Método reponsável por selecionar uma categoria e inserí-la para edição.
		this.editada = (Categoria) event.getTreeNode().getData(); //Procedimento para atribuir a categoria selecionada da árvore.
		Categoria pai = this.editada.getPai(); //Procedimento para atribuir a categoria pai da categoria selecionada.
		if (this.editada != null && pai != null && pai.getCodigo() != null) { //Verifica se a categoria selecionada, o pai da categoria selecionada e o código do pai são diferentes de null.
			this.mostraEdicao = true; //Atribui-se como "true"
		} else { //Caso contrário
			this.mostraEdicao = false; //Atribui-se como "false"
		}
	}

	public String salvar() { //Método que serve para salvar uma categoria de determinado usuário
		CategoriaRN categoriaRN = new CategoriaRN(); //É instanciado um objeto categoriaRN da Classe ContaRN
		this.editada.setUsuario(this.contextoBean.getUsuarioLogado()); //Atribui-se o objeto Usuario do usuario logado na categoria em edição, pois toda categoria está relacionada a um usuário.
		categoriaRN.salvar(this.editada); //O objeto realiza o metodo de salvar a categoria no Banco de Dados
		this.editada = null; //Procedimento para reiniciar a instância que acabou de ser salva.
		this.mostraEdicao = false; //Procedimento para não mostrar informação no formulário de edição.
		this.categoriasTree = null; //Procedimento para forçar o carregamento dos dados da árvore de categorias.
		this.categoriasSelect = null; //Procedimento para forçar o carregamento dos dados da caixa de seleção de categorias pai.
		return null;
	}

	public String excluir() { //Método que serve para exlcuir uma categoria de determinado usuário
		CategoriaRN categoriaRN = new CategoriaRN(); //É instanciado um objeto categoriaRN da Classe ContaRN
		categoriaRN.excluir(this.editada); //O objeto realiza o metodo de salvar a categoria no Banco de Dados
		this.editada = null; //Procedimento para reiniciar a instância que acabou de ser salva.
		this.mostraEdicao = false; //Procedimento para não mostrar informação no formulário de edição
		this.categoriasTree = null; //Procedimento para forçar o carregamento dos dados da árvore de categorias.
		this.categoriasSelect = null; //Procedimento para forçar o carregamento dos dados da caixa de seleção de categorias pai.
		return null;
	}

	public TreeNode getCategoriasTree() { //Método equivalente ao getter dos dados do componente Tree. Sua principal função é o carregamento da estrutura hierárquica das categorias do usuário.
		if (this.categoriasTree == null) { //Verifica se a ávore de categoria está atribuída como null.
			CategoriaRN categoriaRN = new CategoriaRN(); //Caso esteja, será instanciada um objeto "categoriaRN" da classe "CategoriaRN".
			List<Categoria> categorias = categoriaRN.listar(this.contextoBean.getUsuarioLogado()); //Será realizada a montagem da lista de categorias do usuário logado.
			this.categoriasTree = new DefaultTreeNode(null, null); //A ávore de categoria atual irá instanciar um construtor vazio.
			this.montaDadosTree(this.categoriasTree, categorias); //A árvore receberá a estrutura hierárquica de Tree Node através do método "montaDadosTree".
		}
		return this.categoriasTree; //É feito o retorno da árvore.
	}

	private void montaDadosTree(TreeNode pai, List<Categoria> lista) { //Método que tem um funcionamento recursivo para percorrer todas as categorias do usuário e chamar a si mesmo.
		if (lista != null && lista.size() > 0) { //Se a lista da árvore não for vazia e o seu tamanho for maior que 0.
			TreeNode filho = null; //Atribui-se a categoria filho como null.
			for (Categoria categoria : lista) { //Percorre-se todas as categorias da lista da árvore. 
				filho = new DefaultTreeNode(categoria, pai); //Instancia o construtor do filho, atribuindo o nome da categoria e informando a categoria pai.
				this.montaDadosTree(filho, categoria.getFilhos()); //Novamente o método "montaDadosTree" é chamado atribuindo-se a categoria filho como ávore pai e a listagem dos filhos abaixo de modo a gerar a recursividade. 
			}
		}
	}

	public List<SelectItem> getCategoriasSelect() { //Método equivalente ao "getCategoriasTree", porém este gera uma lista plana de categorias baseada nas categorias do usuário.
		if (this.categoriasSelect == null) { //Verifica se a lista de categorias está atribuída como null.
			this.categoriasSelect = new ArrayList<SelectItem>(); //Caso esteja, será instanciado o objeto "categoriaSelect" como um ArrayList de seleção de itens.
			CategoriaRN categoriaRN = new CategoriaRN(); //Caso esteja, será instanciado um objeto "categoriaRN" da classe "CategoriaRN".
			List<Categoria> categorias = categoriaRN.listar(this.contextoBean.getUsuarioLogado()); //Será realizada a montagem da lista de categorias do usuário logado.
			this.montaDadosSelect(this.categoriasSelect, categorias, ""); //A árvore receberá uma listagem de categorias com estrutura hierárquica através do método "montaDadosSelect".
		}
		return categoriasSelect; //É feito o retorno da lista.
	}

	private void montaDadosSelect(List<SelectItem> select, List<Categoria> categorias, String prefixo) { //Método responsável por montar uma lista de SelectItem, com deslocamento de espaço (&nbsp) para representar uma estrutura hierárquica.
		SelectItem item = null; //Atribui-se o objeto "item" da classe SelectItem" como null.
		if (categorias != null) { //Verifica se a lista de categorias é diferente de null.
			for (Categoria categoria : categorias) { //Caso esteja, será realizada um for percorrendo as categorias de toda a lista.
				item = new SelectItem(categoria, prefixo + categoria.getDescricao()); //Cada Item receberá uma instancia do construtor de SelectItem contendo dois parâmetros (Valor do item, Texto que deve ser exibido). 
				item.setEscape(false); //O método setEscape(false) servirá para que o JSF deixe o navegador interpretar o comando &nbsp como um espaço.
				select.add(item); //Método responsável pela adição do item a listagem de categorias.
				this.montaDadosSelect(select, categoria.getFilhos(), prefixo + "&nbsp;&nbsp;"); //Método responsável pela recursividade.
			}
		}
	}

	public Categoria getEditada() {
		return editada;
	}

	public void setEditada(Categoria editada) {
		this.editada = editada;
	}

	public boolean isMostraEdicao() {
		return mostraEdicao;
	}

	public void setMostraEdicao(boolean mostraEdicao) {
		this.mostraEdicao = mostraEdicao;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

}

