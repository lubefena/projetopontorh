package br.com.pontorh.util;

import br.com.pontorh.registro.*;

public class DAOFactory {
	
	public static RegistroDAO criarRegistroDAO() { //M�dodo que instanciar� um objeto da Classe RegistroDAOHibernate e realizar� a atribui��o da Session ao objeto instanciado.
		RegistroDAOHibernate registroDAO = new RegistroDAOHibernate();
		registroDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return registroDAO;
	}
}