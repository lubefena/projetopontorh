package br.com.financeiropapw.lancamento;

import java.util.*;

import br.com.financeiropapw.conta.Conta;

public interface LancamentoDAO {
	
	public void salvar(Lancamento lancamento);
	public void excluir(Lancamento lancamento);
	public Lancamento carregar(Integer lancamento);
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim); //Como os Lançamentos estão relacionados a uma Conta, o método listar receberá uma Conta como parâmetro. 
	public float saldo(Conta conta, Date data);

}
