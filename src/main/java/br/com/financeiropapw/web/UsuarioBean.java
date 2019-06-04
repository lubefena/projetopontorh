package br.com.financeiropapw.web;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.financeiropapw.conta.Conta;
import br.com.financeiropapw.conta.ContaRN;
import br.com.financeiropapw.usuario.Usuario;
import br.com.financeiropapw.usuario.UsuarioRN;

@ManagedBean(name = "usuarioBean")
@RequestScoped

public class UsuarioBean {
	
	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;
	private Conta conta = new Conta();
	
	public String novo() { //Método responsável pela instancia de um objeto usuario
		this.destinoSalvar = "usuariosucesso";
		this.usuario = new Usuario();
		this.usuario.setAtivo(true); //Setando a propriedade Ativo como true
		return "/publico/usuario"; //Retorno da página a ser exibida
	}
	
	public String editar() { //Método responsável por abrir a página "usuario.xhtml com os dados do usuário a ser editado"
		this.confirmarSenha = this.usuario.getSenha(); //
		return "/publico/usuario";
	}
	
	public String salvar() { //Método responsável por salvar o usuário
		FacesContext context = FacesContext.getCurrentInstance(); //Instancia da FacesContext que será utilizada para adicionar as mensagens de erro que vierem a ser criadas
		
		String senha = this.usuario.getSenha();
		if (!senha.equals(this.confirmarSenha)) { //Comparação do campo senha com o campo confirma senha
			FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente"); //Criação da mensagem a ser exibida caso não haja a confirmação da senha e confirma senha
			context.addMessage(null, facesMessage); //Adição da mensagem ao FacesContext para exibição
			return null; //A execução da ação será nula, ou seja, não caíra em uma nova página
		} else {
			UsuarioRN usuarioRN = new UsuarioRN(); //Caso haja a confirmação do campo senha e confirma senha, será instanciado um usuário para persistência no banco de dados
			usuarioRN.salvar(this.usuario); //Realização da persistência no banco de dados
		}
		
		if (this.conta.getDescricao() != null) { //Se a descrição da Conta estiver diferente de null
			this.conta.setUsuario(this.usuario); //Atribui-se o usuário logado a conta
			this.conta.setFavorita(true); //Atribui-se a conta como favorita
			ContaRN contaRN = new ContaRN(); //Instancia um objeto da classe ContaRN
			contaRN.salvar(this.conta); //O objeto realiza a persistência no banco de dados com o método salvar
		}
		return this.destinoSalvar; //Retornar a página de destino
	}
	
	public String excluir() { //Método responsável pela exclusão de um usuário
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.excluir(this.usuario);
		this.lista = null; //Atribuição responsável por forçar a recargar da lista de usuário pelo método getLista()
		return null;
	}
	
	public String ativar() { //Método responsável por ativar/desativar um usuário
		if (this.usuario.isAtivo()) { //Se o usuário estiver ativo
			this.usuario.setAtivo(false);//Muda-se a situação ativa dele para false
		} else { //Se o usuário não estiver ativo
			this.usuario.setAtivo(true);//Muda-se a situação ativa dele para true
	}
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);
		return null;
	}
	
	public List<Usuario> getLista() { //Método responsável por fornecer os dados para alimentar a listagem de usuários na página
		if (this.lista == null) { //Condicional utilizada para economizar recursos do sistema durante a montagem da página.
			UsuarioRN usuarioRN = new UsuarioRN();
			this.lista = usuarioRN.listar();//Caso haja lista vazia, será instanciado um novo usuário e realizado a listagem
		} 
		return this.lista;//Retorno da listagem do usuário.
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public String atribuiPermissao(Usuario usuario, String permissao) {
		this.usuario = usuario;
		java.util.Set<String> permissoes = this.usuario.getPermissao();
		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return null;
	}
}
