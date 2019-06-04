package br.com.financeiropapw.conta;

import java.util.List;

import br.com.financeiropapw.usuario.Usuario;

public interface ContaDAO {

	public void salvar(Conta conta);
	public void excluir(Conta conta);
	public Conta carregar(Integer conta);
	public List<Conta> listar(Usuario usuario); //Como toda Conta está relacionada a um Usuario, o método listar receberá um Usuario como parâmetro 
	public Conta buscarFavorita(Usuario usuario); //Como toda Conta favorita está relacionada a um Usuario, o método buscarFavorita receberá um Usuairo como parâmetro 
}
