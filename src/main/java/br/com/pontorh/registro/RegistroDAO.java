package br.com.pontorh.registro;

import java.util.*;

public interface RegistroDAO {
	
	public void salvar(Registro lancamento);
	public void excluir(Registro lancamento);
	public Registro carregar(Integer lancamento);
	public List<Registro> listar(Date dataInicio, Date dataFim); //Como os Registros estão relacionados a uma Data, o método listar receberá uma Data como parâmetro. 

}
