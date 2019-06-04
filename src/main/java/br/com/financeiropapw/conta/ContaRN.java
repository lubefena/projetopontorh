package br.com.financeiropapw.conta;

import java.util.Date;
import java.util.List;

import br.com.financeiropapw.usuario.Usuario;
import br.com.financeiropapw.util.DAOFactory;

public class ContaRN {

	private ContaDAO contaDAO; //Declaração de propriedade da interface contaDAO
	
	public ContaRN() {
		this.contaDAO = DAOFactory.criarContaDAO(); //Instanciação da propriedade utilizando o DAOFactory no construtor
	}
	
	public void salvar(Conta conta) { //Método que realiza o cadastro ou atualização de dados de uma conta
		conta.setDataCadastro(new Date()); //Durante o cadastro de uma conta, a data a ser informada é a data do ato do cadastro
		this.contaDAO.salvar(conta);
	}
	
	public void excluir(Conta conta) { //Método que realiza a exclusão de uma conta
		this.contaDAO.excluir(conta);
	}
	
	public Conta carregar(Integer conta) { //Método que carrega uma única instância de uma conta com base em seu código
		return this.contaDAO.carregar(conta);
	}
	
	public List<Conta> listar(Usuario usuario) { //Método que fornece uma lista com todas as contas
		return this.contaDAO.listar(usuario);
	}
	
	public Conta buscarFavorita(Usuario usuario) { //Método que carrega as informações da conta na tela logo após o login
		return this.contaDAO.buscarFavorita(usuario);
	}
	
	public void tornarFavorita(Conta contaFavorita) { //Método que registra uma determinada conta bancária como favorita do usuário
		Conta conta = this.buscarFavorita(contaFavorita.getUsuario()); //Se obtém a conta favorita atual
		if (conta != null) { //Se a conta estiver preenchida
			conta.setFavorita(false); //Atribui-se como false 
			this.contaDAO.salvar(conta); //E salva a conta atual como false 
		}
		contaFavorita.setFavorita(true); //Depois marca a conta recebida no parâmetro como true
		this.contaDAO.salvar(contaFavorita); //E salva conta recebida
	}
}
