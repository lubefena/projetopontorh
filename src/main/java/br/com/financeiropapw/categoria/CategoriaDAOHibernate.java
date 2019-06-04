package br.com.financeiropapw.categoria;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.financeiropapw.usuario.Usuario;

public class CategoriaDAOHibernate implements CategoriaDAO {

	private Session session; //Declaração de propriedade da Classe Session do Hibernate
	
	public void setSession(Session session) { //Método padrão para injeção da Session do Hibernate na classe CategoriaDAOHibernate
		this.session = session;
	}
	
	public Categoria salvar(Categoria categoria) {
		Categoria merged = (Categoria) this.session.merge(categoria); //O método "session.merged" é responsável pela fusão de uma categoria que está fora do contexto de persistencia com uma instância que está na memória. Possui a mesma funcionalidade final do método saveOrUpdate.
		this.session.flush(); //Se o o objeto já existir no banco de dados, o SQL UPDATE só será executado quando o "session.flush" for chamado.
		this.session.clear(); //O método "session.clear()" remove da memória do Hibernate todos os objetos carregados.
		return merged;
	}
	
	public void excluir(Categoria categoria) {
		categoria = (Categoria) this.carregar(categoria.getCodigo()); //É feito o carga de todas as categorias.
		this.session.delete(categoria); //Método responsável pela exclusão em cascata.
		this.session.flush();
		this.session.clear();
	}
	
	public Categoria carregar(Integer categoria) {
		return (Categoria) this.session.get(Categoria.class, categoria); 
	}
	
	public List<Categoria> listar(Usuario usuario) {
	String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario"; //A HQL demonstra que o filtro "c.pai is null" garantira que somente os primeiros níveis de categoria (DESPESA e RECEITA) sejam carregados. E a carga dos demais níveis será feita pelo relacionamento @OneToMany dos filhos.
		Query query = this.session.createQuery(hql);
		query.setInteger("usuario", usuario.getCodigo());
		List<Categoria> lista = query.list();
		return lista;
	}
	
}
