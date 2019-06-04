package br.com.financeiropapw.web;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.*;

import br.com.financeiropapw.categoria.Categoria;
import br.com.financeiropapw.conta.Conta;
import br.com.financeiropapw.lancamento.Lancamento;
import br.com.financeiropapw.lancamento.LancamentoRN;

@ManagedBean(name = "lancamentoBean")
@ViewScoped //O escopo "ViewScoped" mantém a mesma instância da classe Bean enquanto o usuário estiver na mesma tela, independente da quantidade de requisições que tenha sido feita para a mesma tela.
public class LancamentoBean implements Serializable {
	
	//A classe será serializada por conta do escopo "ViewScoped"
	private static final long serialVersionUID = 3792950636875297407L;
	private List<Lancamento> lista;
	private Conta conta;
	private List<Double> saldos;
	private float saldoGeral;
	private Lancamento editado = new Lancamento();
	
	@ManagedProperty(value = "#{contextoBean}")
	private ContextoBean contextoBean;
	
	
	public LancamentoBean() { //Método construtor da classe que irá chamar o método "novo".
		this.novo();
	}
	
	public String novo() {
		this.editado = new Lancamento();
		this.editado.setData(new Date());
		return null;
	}
	
	public void editar() { 
		
	}
	
	public void salvar() {
		this.editado.setUsuario(this.contextoBean.getUsuarioLogado());
		this.editado.setConta(this.contextoBean.getContaAtiva());
		
		LancamentoRN lancamentoRN = new LancamentoRN();
		lancamentoRN.salvar(this.editado);
		
		this.novo();
		this.lista = null;
	}
	
	public void excluir() {
		LancamentoRN lancamentoRN = new LancamentoRN();
		lancamentoRN.excluir(this.editado);
		this.lista = null;
	}
	
	public List<Lancamento> getLista() { //Método responsável por fornecer os dados que alimentarão a "dataTable" dos lançamentos.
		if (this.lista == null || this.conta != this.contextoBean.getContaAtiva()) { //Se a lista de lançamentos for vazia ou a conta selecionada não estiver ativa.
			this.conta = this.contextoBean.getContaAtiva(); //A conta selecionada torna-se ativa.
			
			Calendar dataSaldo = new GregorianCalendar(); //Instancia-se um objeto da Classe "Calendar" do tipo "GregorianCalendar";
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);
			
			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);
			
			LancamentoRN lancamentoRN = new LancamentoRN();
			this.saldoGeral = lancamentoRN.saldo(this.conta, dataSaldo.getTime());
			this.lista = lancamentoRN.listar(this.conta, inicio.getTime(), null);
			
			Categoria categoria = null;
			double saldo = this.saldoGeral;
			this.saldos = new ArrayList<Double>();
			for (Lancamento lancamento : this.lista) {
				categoria = lancamento.getCategoria();
				saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
				this.saldos.add(saldo);
			}
		}
		return this.lista;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Double> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<Double> saldos) {
		this.saldos = saldos;
	}

	public float getSaldoGeral() {
		return saldoGeral;
	}

	public void setSaldoGeral(float saldoGeral) {
		this.saldoGeral = saldoGeral;
	}

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLista(List<Lancamento> lista) {
		this.lista = lista;
	}
	
	

}
