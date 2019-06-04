package br.com.financeiropapw.util;

import br.com.financeiropapw.categoria.CategoriaDAO;
import br.com.financeiropapw.categoria.CategoriaDAOHibernate;
import br.com.financeiropapw.conta.*;
import br.com.financeiropapw.lancamento.*;
import br.com.financeiropapw.usuario.*;

public class DAOFactory {

	public static UsuarioDAO criarUsuarioDAO() { //Médodo que instanciará um objeto da Classe UsuarioDAOHibernate e realizará a atribuição da Session ao objeto instanciado.
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}
	
	public static ContaDAO criarContaDAO() { //Médodo que instanciará um objeto da Classe ContaDAOHibernate e realizará a atribuição da Session ao objeto instanciado.
		ContaDAOHibernate contaDAO = new ContaDAOHibernate();
		contaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return contaDAO;
	}
	
	public static CategoriaDAO criarCategoriaDAO() { //Médodo que instanciará um objeto da Classe CategoriaDAOHibernate e realizará a atribuição da Session ao objeto instanciado.
		CategoriaDAOHibernate categoriaDAO = new CategoriaDAOHibernate();
		categoriaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return categoriaDAO;
	}
	
	public static LancamentoDAO criarLancamentoDAO() { //Médodo que instanciará um objeto da Classe LancamentoDAOHibernate e realizará a atribuição da Session ao objeto instanciado.
		LancamentoDAOHibernate lancamentoDAO = new LancamentoDAOHibernate();
		lancamentoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return lancamentoDAO;
	}
}