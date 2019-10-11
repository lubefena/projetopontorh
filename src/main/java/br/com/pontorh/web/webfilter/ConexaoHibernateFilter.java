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

@WebFilter(urlPatterns = { "*.jsf" }) //Configurando qual o tipo de requisição web a Classe Filter irá interceptar

public class ConexaoHibernateFilter implements Filter {
	private SessionFactory sf;

	public void init(FilterConfig config) throws ServletException { //Método responsável pela criação da SessionFactory
		this.sf = HibernateUtil.getSessionFactory();
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException { //Método responsável pela interceptação das requisições

		Session currentSession = this.sf.getCurrentSession();

		Transaction transaction = null;

		try {
			transaction = currentSession.beginTransaction(); //Iniciando uma transação no Bando de Dados chamando o método beginTransaction()
			chain.doFilter(servletRequest, servletResponse); //Processamento é passado adiante, para execução normal
			transaction.commit(); //Todo o processamento requisitado originalmente já foi realizado 
			if (currentSession.isOpen()) {
				currentSession.close();
			}
		} catch (Throwable ex) {
			try {
				if (transaction.isActive()) {
					transaction.rollback();
				}
			} catch (Throwable t) { //Tratamento de qualquer erro que tenha sido lançado no processamento
				t.printStackTrace();
			}
			throw new ServletException(ex);
		}
	}

	public void destroy() {
	}
}


