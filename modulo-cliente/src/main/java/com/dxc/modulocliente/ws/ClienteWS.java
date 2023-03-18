package com.dxc.modulocliente.ws;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dxc.modulocliente.dao.ClienteDao;
import com.dxc.modulocliente.exception.EntidadeNaoEncontradaException;
import com.dxc.modulocliente.exception.NegocioException;
import com.dxc.modulocliente.modelo.Cliente;
import com.dxc.modulocliente.modelo.Endereco;
import com.dxc.modulocliente.util.CEPUtil;


@WebService
public class ClienteWS {
	
	@PersistenceContext(unitName = "clientePU")
	private EntityManager entityManager;
	
	@WebMethod(operationName = "buscarTodos", action = "buscar_todos")
	@WebResult(name = "cliente")
	public List<Cliente> findAll() {
		ClienteDao dao = new ClienteDao(entityManager);
		return dao.findAll();
	}

	@WebMethod(operationName = "buscarPeloCPF", action = "busca_por_cpf")
	@WebResult(name = "cliente")
	public Cliente findCPF(@WebParam(name ="cpf") String cpf) throws EntidadeNaoEncontradaException {
		ClienteDao dao = new ClienteDao(entityManager);
		return dao.findByCPF(cpf);
	}
	

	@WebMethod(operationName = "removePeloCPF", action = "remove_por_cpf")
	public boolean removeCPF(@WebParam(name ="cpf") String cpf) throws EntidadeNaoEncontradaException {
		ClienteDao dao = new ClienteDao(entityManager);
		return dao.removeByCPF(cpf);
	}
	

	@WebMethod(operationName = "salvar", action = "salvar")
	@WebResult(name = "cliente")
	public Cliente save(@WebParam(name ="nome") String nome, @WebParam(name ="cpf") String cpf, @WebParam(name ="cep") String cep, 
			@WebParam(name ="complemento") String comp) 
			throws EntidadeNaoEncontradaException, NegocioException {
		
		ClienteDao dao = new ClienteDao(entityManager);
		CEPUtil cepUtil = new CEPUtil();
		Cliente cliente = new Cliente();
		
		Endereco endereco = cepUtil.buscarEnderecoPeloCEP(cep);
		
		if (endereco != null ) {
			cliente.setLocalidade(String.format("%s %s %S", 
					endereco.getLocalidade(), endereco.getBairro(), endereco.getComplemento()
					));
			cliente.setUf(endereco.getUf());
		}
		
		cliente.setNome(nome);
		cliente.setCpf(cpf);
		cliente.setComplemento(comp);
		
		return dao.save(cliente);
	}
	
	@WebMethod
	@WebResult(name = "saida")
	public String hello() {
		return "BEMVINDO AO WEB SERVICES!!!" + new Date();
	}
	
	
}
