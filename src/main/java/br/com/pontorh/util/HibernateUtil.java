package br.com.pontorh.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil { //Classe respons�vel por instanciar a sessionFactory e retorn�-la quando solicitado
	
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static SessionFactory buildSessionFactory() { //M�todo respons�vel por configurar o hibernate e retornar uma SessionFactory
		try {
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml"); //M�todo para informar o local e o arquivo 
			StandardServiceRegistryBuilder registradorServico = new StandardServiceRegistryBuilder();
			registradorServico.applySettings(cfg.getProperties());
			StandardServiceRegistry servico = registradorServico.build();
			return cfg.buildSessionFactory(servico); 
		} catch (Throwable e ) {
			System.out.println("Cria��o inicial do objeto SessionFactory falhou. Erro " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
