package br.com.pontorh.web;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.*;

import br.com.pontorh.registro.Registro;
import br.com.pontorh.registro.RegistroRN;

@ManagedBean(name = "registroBean")
@ViewScoped //O escopo "ViewScoped" mantém a mesma instância da classe Bean enquanto o usuário estiver na mesma tela, independente da quantidade de requisições que tenha sido feita para a mesma tela.
public class RegistroBean implements Serializable {
	
	//A classe será serializada por conta do escopo "ViewScoped"

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Registro> lista;
	private Registro editado = new Registro();
	
	public RegistroBean() { //Método construtor da classe que irá chamar o método "novo".
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
	
	public List<Registro> getLista() { //Método responsável por fornecer os dados que alimentarão a "dataTable" dos registros.
		if (this.lista == null) { //Se a lista de lançamentos for vazia.
			
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
