package br.com.pontorh.registro;

import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class RegistroDAOHibernate implements RegistroDAO {
	
	private Session session; //Declaração do objeto da Classe Session do Hibernate.
	
	public void setSession(Session session) { // O método "setSession" realiza a injeção da Session do Hibernate na classe RegistroDAOHibernate
	this.session = session;
	}
	
	public void salvar(Registro registro) {
		this.session.saveOrUpdate(registro);
	}
	
	public void excluir(Registro registro) {
		this.session.delete(registro);
	}
	
	public Registro carregar(Integer registro) {
		return (Registro) this.session.get(Registro.class, registro);
	}
	
	@SuppressWarnings("unchecked")
	public List<Registro> listar(Date dataInicio, Date dataFim) { //O método listar permite buscar os lançamentos entre as datas início e data fim. 
		Criteria criteria = this.session.createCriteria(Registro.class); //Instancia do objeto da Classe Criteria forçando uma consulta a tabela de "registro" no Banco de Dados. 
		
		if (dataInicio != null && dataFim != null) { //Se a "dataInicio" e a "dataFim" forem diferentes de "null".
			criteria.add(Restrictions.between("data", dataInicio, dataFim)); //Será feita uma consulta com restrição entre aos atributos "dataInicio" e "dataFim". 
		} else if (dataInicio != null) { //Caso apenas a "dataInicio" for diferente de "null"
			criteria.add(Restrictions.ge("data", dataInicio)); //Será feita uma consulta com restrição maior que ou igual ao atributo "dataInicio".
		} else if (dataFim != null) { //Caso apenas a "dataFim" for diferente de "null"
			criteria.add(Restrictions.le("data", dataFim)); //Será feita uma consulta com restrição menor que ou igual ao atributo "dataFim".
		}
		
		criteria.addOrder(Order.asc("data")); //Realiza-se uma ordenação dos registros da data mais antiga para a mais recente.
		return criteria.list(); //Retorna-se o resultado da lista.
	}

}
