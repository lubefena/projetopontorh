package br.com.financeiropapw.lancamento;

import java.math.BigDecimal;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.financeiropapw.conta.Conta;

public class LancamentoDAOHibernate implements LancamentoDAO {
	
	private Session session; //Declaração do objeto da Classe Session do Hibernate.
	
	public void setSession(Session session) { // O método "setSession" realiza a injeção da Session do Hibernate na classe LancamentoDAOHibernate
	this.session = session;
	}
	
	public void salvar(Lancamento lancamento) {
		this.session.saveOrUpdate(lancamento);
	}
	
	public void excluir(Lancamento lancamento) {
		this.session.delete(lancamento);
	}
	
	public Lancamento carregar(Integer lancamento) {
		return (Lancamento) this.session.get(Lancamento.class, lancamento);
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) { //O método listar permite buscar os lançamentos entre as datas início e data fim de uma determinada conta. 
		Criteria criteria = this.session.createCriteria(Lancamento.class); //Instancia do objeto da Classe Criteria forçando uma consulta a tabela de "lancamento" no Banco de Dados. 
		
		if (dataInicio != null && dataFim != null) { //Se a "dataInicio" e a "dataFim" forem diferentes de "null".
			criteria.add(Restrictions.between("data", dataInicio, dataFim)); //Será feita uma consulta com restrição entre aos atributos "dataInicio" e "dataFim". 
		} else if (dataInicio != null) { //Caso apenas a "dataInicio" for diferente de "null"
			criteria.add(Restrictions.ge("data", dataInicio)); //Será feita uma consulta com restrição maior que ou igual ao atributo "dataInicio".
		} else if (dataFim != null) { //Caso apenas a "dataFim" for diferente de "null"
			criteria.add(Restrictions.le("data", dataFim)); //Será feita uma consulta com restrição menor que ou igual ao atributo "dataFim".
		}
		
		criteria.add(Restrictions.eq("conta", conta)); //Realiza-se uma consulta com restrição igual ao atributo "conta".
		criteria.addOrder(Order.asc("data")); //Realiza-se uma ordenação dos lançamentos da data mais antiga para a mais recente.
		return criteria.list(); //Retorna-se o resultado da lista.
	}
	
	public float saldo(Conta conta, Date data) { //O método saldo permite calcular o saldo total dos lançamentos até a data recebida por parâmetro.
		StringBuffer sql = new StringBuffer(); //Instancia do objeto da Classe "StringBuffer" para concatenar a query do comando SQL.
		sql.append("select sum(l.valor * c.fator)");
		sql.append(" from lancamento l,");
		sql.append(" Categoria c");
		sql.append(" where l.categoria = c.codigo");
		sql.append(" and l.conta = :conta");
		sql.append(" and l.data <= :data");
		
		SQLQuery query = this.session.createSQLQuery(sql.toString()); //Realiza-se a intancia de um objeto da Classe "SQLQuery" recebendo query do objeto "sql" aplicado o "toString". 
		query.setParameter("conta", conta.getConta()); //Realiza-se a vinculação do valor da conta através do "conta.getConta()" ao paramêtro "conta". 
		query.setParameter("data", data); //Realiza-se a vinculação do valor da data através ao paramêtro "data".
		BigDecimal saldo = (BigDecimal) query.uniqueResult(); //Instancia-se o objeto da Classe "BigDecimal" atribuindo-se o resultado da consulta.
		if (saldo != null) { //Se o valor do saldo apurado for diferente de "null".
			return saldo.floatValue(); //Retorna-se o saldo convertido em float.
		}
		return 0f; //Caso contrário retorna ???
	}

}
