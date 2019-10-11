package br.com.pontorh.util;

import br.com.pontorh.registro.*;

public class DAOFactory {
	
	public static RegistroDAO criarRegistroDAO() { //Médodo que instanciará um objeto da Classe RegistroDAOHibernate e realizará a atribuição da Session ao objeto instanciado.
		RegistroDAOHibernate registroDAO = new RegistroDAOHibernate();
		registroDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return registroDAO;
	}
}