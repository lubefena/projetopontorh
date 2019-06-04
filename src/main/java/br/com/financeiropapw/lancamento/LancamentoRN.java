package br.com.financeiropapw.lancamento;

import java.util.*;

import br.com.financeiropapw.conta.Conta;
import br.com.financeiropapw.util.DAOFactory;

public class LancamentoRN {
	
	private LancamentoDAO lancamentoDAO;
	
	public LancamentoRN() {
		this.lancamentoDAO = DAOFactory.criarLancamentoDAO();
	}
	
	public void salvar(Lancamento lancamento) {
		this.lancamentoDAO.salvar(lancamento);
	}
	
	public void excluir(Lancamento lancamento) {
		this.lancamentoDAO.excluir(lancamento);
	}
	
	public Lancamento carregar(Integer lancamento) {
		return this.carregar(lancamento);
	}
	
	public float saldo(Conta conta, Date data) {
		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = this.lancamentoDAO.saldo(conta, data);
		return saldoInicial + saldoNaData;
	}
	
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {
		return this.lancamentoDAO.listar(conta, dataInicio, dataFim);
	}
}
