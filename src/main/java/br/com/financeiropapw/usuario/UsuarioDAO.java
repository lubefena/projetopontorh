package br.com.financeiropapw.usuario;

import java.util.List;

public interface UsuarioDAO { //Contém a assinatura de todos os métodos ref. às operações em BD para o Usuário 
	
	public void salvar(Usuario usuario);
	public void atualizar(Usuario usuario);
	public void excluir(Usuario usuario);
	public Usuario carregar(Integer codigo);
	public Usuario buscarPorLogin(String string);
	public List<Usuario> listar();

}
