package br.com.financeiropapw.web;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import br.com.financeiropapw.conta.Conta;
import br.com.financeiropapw.conta.ContaRN;
import br.com.financeiropapw.usuario.Usuario;
import br.com.financeiropapw.usuario.UsuarioRN;

@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = 4693275947797122717L;
	private int codigoContaAtiva = 0;
	
	public Usuario getUsuarioLogado() { //Método que serve para obter o login do usuário remoto e realizar a instância do usuário
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser(); //Recebendo os dados do login do usuário
		if (login != null) { //Se o login for diferente de null
			UsuarioRN usuarioRN = new UsuarioRN(); //É feita a instância da classe UsuarioRN
			return usuarioRN.buscaPorLogin(login); //É feito o retorno do objeto instanciado pelo login
		}
		return null;
	}
	
	private Conta getContaAtivaPadrao() { //Método que serve para determinar qual será a conta ativa padrão (favorita).
		ContaRN contaRN = new ContaRN();
		Conta contaAtiva = null;
		Usuario usuario = this.getUsuarioLogado();
		contaAtiva = contaRN.buscarFavorita(usuario); //Conta ativa será a conta favorita do usuário
		if (contaAtiva == null) { //Se a contaAtiva estiver nula
			List<Conta> contas = contaRN.listar(usuario); //É listada o conjunto de contas do usuários
			if (contas != null && contas.size() > 0) { //Se a lista de contas for diferente de null e maior que 0
				contaAtiva = contas.get(0); //A conta ativa será a conta na posição 0 da lista
			}
		}
		return contaAtiva; //É feito o retorno da conta ativa
	}
	
	public Conta getContaAtiva() { //Método que serve para fornecer a conta ativa (favorita) do momento. Se ainda não houve, ele obtém a conta favorita do usuário logado, ou a primeira cadastrada.
		Conta contaAtiva = null;
		if (this.codigoContaAtiva == 0) {
			contaAtiva = this.getContaAtivaPadrao();
		} else {
			ContaRN contaRN = new ContaRN();
			contaAtiva = contaRN.carregar(this.codigoContaAtiva);
		}
		if (contaAtiva != null) {
			this.codigoContaAtiva = contaAtiva.getConta();
			return contaAtiva;
		}
		return null;
	}
	
	public void changeContaAtiva(ValueChangeEvent event) { //Método que serve para alterar a conta ativa no sistema
		this.codigoContaAtiva = (Integer) event.getNewValue();
	}
	
}
