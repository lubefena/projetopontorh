package br.com.pontorh.web.webfilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.com.pontorh.util.HibernateUtil;

@WebFilter(urlPatterns = { "*.jsf" }) //Configurando qual o tipo de requisi��o web a Classe Filter ir� interceptar

public class ConexaoHibernateFilter implements Filter {
	private SessionFactory sf;

	public void init(FilterConfig config) throws ServletException { //M�todo respons�vel pela cria��o da SessionFactory
		this.sf = HibernateUtil.getSessionFactory();
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException { //M�todo respons�vel pela intercepta��o das requisi��es

		Session currentSession = this.sf.getCurrentSession();

		Transaction transaction = null;

		try {
			transaction = currentSession.beginTransaction(); //Iniciando uma transa��o no Bando de Dados chamando o m�todo beginTransaction()
			chain.doFilter(servletRequest, servletResponse); //Processamento � passado adiante, para execu��o normal
			transaction.commit(); //Todo o processamento requisitado originalmente j� foi realizado 
			if (currentSession.isOpen()) {
				currentSession.close();
			}
		} catch (Throwable ex) {
			try {
				if (transaction.isActive()) {
					transaction.rollback();
				}
			} catch (Throwable t) { //Tratamento de qualquer erro que tenha sido lan�ado no processamento
				t.printStackTrace();
			}
			throw new ServletException(ex);
		}
	}

	public void destroy() {
	}
}


