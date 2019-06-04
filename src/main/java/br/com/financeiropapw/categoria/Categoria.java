package br.com.financeiropapw.categoria;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.financeiropapw.usuario.Usuario;

@Entity
public class Categoria implements Serializable { //Representação da tabela no BD
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@ManyToOne //Um relacionamento muitos para um sempre gera uma chave estrangeira. Para que eu o Hibernate não gera um nome aletório, é permitido usar um nome amigável através da annotation "@ForeignKey".
	@JoinColumn(name = "categoria_pai", nullable = true, foreignKey = @ForeignKey(name = "fk_categoria_categoria")) //A "categoria_pai" será utilizada para criar um campo que autorreferencia a tabela. Pela primeira vez usa-se o atribuo "nullable" como true pois o primeiro nível de categorias (RECEITA e DESPESA) de um usuário não terá uma categoria pai.
	private Categoria pai;
	
	@ManyToOne
	@JoinColumn(name = "usuario", foreignKey = @ForeignKey(name = "fk_categoria_usuario"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Usuario usuario;
	
	private String descricao;
	
	private int fator;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //A annotation "@OneToMany" é utilizada para fazer a carga da lista da categoria filhos. Além disso, o atributo CascadeType.REMOVE configura a exclusão dos filhos e netos, caso a categoria seja excluída.
	@JoinColumn(name = "categoria_pai", updatable = false) //O uso da "annotation" @JoinColumn irá carregar todas categorias cujo campo "categoria_pai" seja igual ao código da categoria atual. O atributo "updatable = false" permite editar uma categoria sem afetar seus filhos.
	@org.hibernate.annotations.OrderBy(clause = "descricao asc") //Configuração da ordenação da carga de registros filhos.
	private List<Categoria> filhos;
	
	public Categoria() {} //O Hibernate exige a criação de um construtor vazio nas classes de entidade em que há um construtor personalizado como o caso abaixo.
	
	public Categoria(Categoria pai, Usuario usuario, String descricao, int fator) { //Método construtor que será utilizado para facilitar a criação de categorias no momento de montar a estrutura padrão do usuário no momento do cadastro.
		this.pai = pai;
		this.usuario = usuario;
		this.descricao = descricao;
		this.fator = fator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	public List<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Categoria> filhos) {
		this.filhos = filhos;
	}
	
}
