package br.com.pontorh.web;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.*;

import br.com.pontorh.registro.Registro;
import br.com.pontorh.registro.RegistroRN;

@ManagedBean(name = "registroBean")
@ViewScoped //O escopo "ViewScoped" mant�m a mesma inst�ncia da classe Bean enquanto o usu�rio estiver na mesma tela, independente da quantidade de requisi��es que tenha sido feita para a mesma tela.
public class RegistroBean implements Serializable {
	
	//A classe ser� serializada por conta do escopo "ViewScoped"

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Registro> lista;
	private Registro editado = new Registro();
	
	public RegistroBean() { //M�todo construtor da classe que ir� chamar o m�todo "novo".
		this.novo();
	}
	
	public String novo() {
		this.editado = new Registro();
		this.editado.setData(new Date());
		return null;
	}
	
	public void editar() { 
		
	}
	
	public void salvar() {
		RegistroRN registroRN = new RegistroRN();
		registroRN.salvar(this.editado);
		
		this.novo();
		this.lista = null;
	}
	
	public void excluir() {
		RegistroRN registroRN = new RegistroRN();
		registroRN.excluir(this.editado);
		this.lista = null;
	}
	
	public List<Registro> getLista() { //M�todo respons�vel por fornecer os dados que alimentar�o a "dataTable" dos registros.
		if (this.lista == null) { //Se a lista de lan�amentos for vazia.
			
			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);
			
			RegistroRN registroRN = new RegistroRN();
			this.lista = registroRN.listar(inicio.getTime(), null);
			
		}
		return this.lista;
	}


	public Registro getEditado() {
		return editado;
	}

	public void setEditado(Registro editado) {
		this.editado = editado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLista(List<Registro> lista) {
		this.lista = lista;
	}
	
	

}
