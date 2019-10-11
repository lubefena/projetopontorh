package br.com.pontorh.registro;

import java.util.*;

import br.com.pontorh.util.DAOFactory;

public class RegistroRN {
	
	private RegistroDAO registroDAO;
	
	public RegistroRN() {
		this.registroDAO = DAOFactory.criarRegistroDAO();
	}
	
	public void salvar(Registro registro) {
		this.registroDAO.salvar(registro);
	}
	
	public void excluir(Registro registro) {
		this.registroDAO.excluir(registro);
	}
	
	public Registro carregar(Integer registro) {
		return this.carregar(registro);
	}
	
	public List<Registro> listar(Date dataInicio, Date dataFim) {
		return this.registroDAO.listar(dataInicio, dataFim);
	}
}
