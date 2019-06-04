package br.com.financeiropapw.usuario;

import java.util.List;

import br.com.financeiropapw.categoria.CategoriaRN;
import br.com.financeiropapw.util.DAOFactory;

public class UsuarioRN {
	
	private UsuarioDAO usuarioDAO; //Declaração de propriedade da interface UsuarioDAO
	
	public UsuarioRN() { 
		this.usuarioDAO = DAOFactory.criarUsuarioDAO(); //Instanciação da propriedade utilizando o DAOFactory no construtor
	}
	
	public Usuario carregar(Integer codigo) { //Método que carrega uma única instância de um usuário com base em seu código
		return this.usuarioDAO.carregar(codigo);
	}
	
	public Usuario buscaPorLogin(String login) { //Método que carrega as informações do usuário logo após o login
		return this.usuarioDAO.buscarPorLogin(login);
	}
	
	public void salvar(Usuario usuario) { //Método que realiza o cadastro ou atualização de dados de um usuário
		Integer codigo = usuario.getCodigo();
		if (codigo == null || codigo == 0) { //Se o código do usuário for nulo ou zero, é salvo um novo objeto no banco de dados
			usuario.getPermissao().add("ROLE_USUARIO"); //Quando o usuário cadastrado for novo, é que será adicionada a permissão "ROLE_USUARIO" ao set de permissões
			this.usuarioDAO.salvar(usuario);
			CategoriaRN categoriaRN = new CategoriaRN(); //Instância do objeto "categoriaRN" da Classe CategoriaRN através do método construtor.
			categoriaRN.salvaEstruturaPadrao(usuario); //Execução do método "salvaEstruturaPadrao".
		} else { //Se não, é feita a atualização do objeto já existente no banco de dados
			this.usuarioDAO.atualizar(usuario);
		}
	}
	
	public void excluir(Usuario usuario) { //Método que realiza a exclusão de um usuário
		CategoriaRN categoriaRN = new CategoriaRN(); //Instância do objeto "categoriaRN" da Classe CategoriaRN através do método construtor.
		categoriaRN.excluir(usuario); //Execução do método "excluir". 
		this.usuarioDAO.excluir(usuario);
		
	}
	
	public List<Usuario> listar() { //Método que fornece uma lista com todos os usuários
		return this.usuarioDAO.listar();
	}
}
