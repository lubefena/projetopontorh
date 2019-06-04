package br.com.financeiropapw.usuario;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAOHibernate implements UsuarioDAO { //Implementa a interface UsuarioDAO
	
	private Session session; //Declaração de propriedade da Classe Session do Hibernate
	
	public void setSession(Session session) { //Método padrão para injeção da Session do Hibernate na classe UsuarioDAOHibernate
		this.session = session;
	}
	
	public void salvar(Usuario usuario) { //Método que realiza a operação session.save para cadastrar um novo usuário
		this.session.save(usuario);
	}
	
	public void atualizar(Usuario usuario) { //Método que realiza a operação session.update para modificar informações de um usuário já cadastrado
		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) { //Verifica-se se o usuário não possui permissão
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo()); //Caso não tenha, é recarregado o conjunto de permissões do banco de dados
			usuario.setPermissao(usuarioPermissao.getPermissao()); //Após, transfere-se as permissões originais para o objeto usuario a ser atualizado
			this.session.evict(usuarioPermissao); //Retira-se do contexto persistence o objeto "usuarioPermissao", que só foi utilizado para copiar as permissões
		}
		this.session.update(usuario);
	}
	
	public void excluir(Usuario usuario) { //Método que realiza a operação session.delete para excluir um usuário 
		this.session.delete(usuario);
	}
	
	public Usuario carregar(Integer codigo) { //Método que realiza a operação session.get recebendo a classe e o código do usuário, para carregar todos os dados de usuário recebidos no parâmetro
		return (Usuario) this.session.get(Usuario.class, codigo);
	}
	
	public List<Usuario> listar() { //Método que realiza a operação session.createCriteria seguido por .list() forçando uma consulta ao bando de dados sem declarar qualquer condição
		return this.session.createCriteria(Usuario.class).list();
	}
	
	public Usuario buscarPorLogin(String login) { //Método que realiza uma consulta no banco de dados utilizando uma query, através da operação session.createQuery(hql)
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql); //É instanciado um objeto da Classe Query que realizará uma consulta com a String hql
		consulta.setString("login", login); //O texto :login será substituído pelo parâmetro do atribuído no método
		return (Usuario) consulta.uniqueResult(); //Como login é uma chave natural da tabela Usuário, é sabido que o resultado não virá mais que uma linha. Portanto se chama o método uniqueResult()
	}

}
