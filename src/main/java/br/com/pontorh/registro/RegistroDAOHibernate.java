package br.com.pontorh.registro;

import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class RegistroDAOHibernate implements RegistroDAO {
	
	private Session session; //Declara��o do objeto da Classe Session do Hibernate.
	
	public void setSession(Session session) { // O m�todo "setSession" realiza a inje��o da Session do Hibernate na classe RegistroDAOHibernate
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
	public List<Registro> listar(Date dataInicio, Date dataFim) { //O m�todo listar permite buscar os lan�amentos entre as datas in�cio e data fim. 
		Criteria criteria = this.session.createCriteria(Registro.class); //Instancia do objeto da Classe Criteria for�ando uma consulta a tabela de "registro" no Banco de Dados. 
		
		if (dataInicio != null && dataFim != null) { //Se a "dataInicio" e a "dataFim" forem diferentes de "null".
			criteria.add(Restrictions.between("data", dataInicio, dataFim)); //Ser� feita uma consulta com restri��o entre aos atributos "dataInicio" e "dataFim". 
		} else if (dataInicio != null) { //Caso apenas a "dataInicio" for diferente de "null"
			criteria.add(Restrictions.ge("data", dataInicio)); //Ser� feita uma consulta com restri��o maior que ou igual ao atributo "dataInicio".
		} else if (dataFim != null) { //Caso apenas a "dataFim" for diferente de "null"
			criteria.add(Restrictions.le("data", dataFim)); //Ser� feita uma consulta com restri��o menor que ou igual ao atributo "dataFim".
		}
		
		criteria.addOrder(Order.asc("data")); //Realiza-se uma ordena��o dos registros da data mais antiga para a mais recente.
		return criteria.list(); //Retorna-se o resultado da lista.
	}

}
