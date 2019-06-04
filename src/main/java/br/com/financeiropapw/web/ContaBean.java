package br.com.financeiropapw.web;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import br.com.financeiropapw.conta.Conta;
import br.com.financeiropapw.conta.ContaRN;

@ManagedBean
@RequestScoped
public class ContaBean {
	private Conta selecionada = new Conta();
	private List<Conta> lista = null;
	@ManagedProperty(value = "#{contextoBean}")
	private ContextoBean contextoBean;
	
	public String salvar() { //Método que serve para salvar uma conta de determinado usuário
		this.selecionada.setUsuario(this.contextoBean.getUsuarioLogado()); //Atribui-se o objeto Usuario do usuario logado na conta em edição, pois toda conta está relacionada a um usuario
		ContaRN contaRN = new ContaRN(); //É instanciado um objeto contaRN da Classe ContaRN
		contaRN.salvar(this.selecionada); //O objeto realiza o metodo de salvar a conta no Banco de Dados
		this.selecionada = new Conta(); //Atribui-se um new Conta() à propriedade this.selecionada para limpar o formulário
		this.lista = null; //Atribui-se um null para a list de contas para forçar o recarregamento das contas no banco de dados quando o painel de listagem for recarregado
		return null;
	}
	
	public String excluir() { //Método que serve para excluir uma conta de determinado usuário
		ContaRN contaRN = new ContaRN();
		contaRN.excluir(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
		return null;
	}
	
	public String tornarFavorita() {
		ContaRN contaRN = new ContaRN();
		contaRN.tornarFavorita(this.selecionada);
		this.selecionada = new Conta();
		return null;
	}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	public List<Conta> getLista() {
		if (this.lista == null) {
			ContaRN contaRN = new ContaRN();
			this.lista = contaRN.listar(this.contextoBean.getUsuarioLogado());
		} 
		return this.lista;
	}


	public void setLisa(List<Conta> conta) {
		this.lista = conta;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
}
