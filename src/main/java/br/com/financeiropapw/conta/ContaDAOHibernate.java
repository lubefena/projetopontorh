package br.com.financeiropapw.conta;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.financeiropapw.usuario.Usuario;

public class ContaDAOHibernate implements ContaDAO { //Implementa interface ContaDAO

	private Session session; //Declaração de propriedade da Classe Session do Hibernate
	
	public void setSession(Session session) { //Método padrão para injeção da Session do Hibernate na classe ContaDAOHibernate
		this.session = session;
	}
	
	public void salvar(Conta conta) { //Método que realiza a operação session.save para cadastrar ou atualizar uma conta
		this.session.saveOrUpdate(conta);
	}
	
	public void excluir(Conta conta) { //Método que realiza a operação session.delete para excluir uma conta 
		this.session.delete(conta);
	}
	
	public Conta carregar(Integer conta) { //Método que realiza a operação session.get recebendo a classe e o número da conta, para carregar todos os dados da conta recebidos no parâmetro
		return (Conta) this.session.get(Conta.class, conta);
	}
	
	public List<Conta> listar(Usuario usuario) { //Método que realiza a operação session.createCriteria seguida por uma restrição (filtro) de Usuário e o retorno da lista
		Criteria criteria = this.session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		return criteria.list();
	}
	
	public Conta buscarFavorita(Usuario usuario) { //Método que realiza a operação session.createCriteria seguida por duas restrições (filtro) de Usuário e da conta favorita e o retorno de um único resultado.
		Criteria criteria = this.session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		criteria.add(Restrictions.eq("favorita", true));
		return (Conta) criteria.uniqueResult();
	}
}
