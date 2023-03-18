package com.dxc.modulocliente.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.dxc.modulocliente.exception.EntidadeNaoEncontradaException;
import com.dxc.modulocliente.exception.NegocioException;
import com.dxc.modulocliente.modelo.Cliente;

public class ClienteDao {

	private EntityManager entityManager;
	
	public ClienteDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Cliente> findAll() {
		return entityManager.createQuery("FROM Cliente c", Cliente.class).getResultList();
	}
	
	private List<Cliente> findByCFPAll(String cpf) {
		return entityManager.createQuery("FROM Cliente c WHERE c.cpf =:cpf", Cliente.class)
				.setParameter("cpf", cpf)
				.getResultList();
	}
	
	public Cliente findByCPF(String cpf) throws EntidadeNaoEncontradaException {
		List<Cliente> clientes = findByCFPAll(cpf);
		
		if (clientes.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cliente com cpf %s nao encontrado.", cpf));
		}
		
		return clientes.get(0);
	}
	
	public Cliente save(Cliente cliente) throws NegocioException {
		if (cliente.getId() == null) {
			validaCPF(cliente.getCpf());
			cliente.setDatahoracriacao(new Date());
			entityManager.persist(cliente);
		} else {
			if (!entityManager.contains(cliente)) {
				if (entityManager.find(Cliente.class, cliente.getId()) == null) {
					
				}
			}
			
			entityManager.merge(cliente);
		}
		
		return cliente;
	}
	
	private void validaCPF(String cpf) throws NegocioException {
		List<Cliente> clientes = findByCFPAll(cpf);
		
		if (!clientes.isEmpty()) {
			throw new NegocioException(String.format("CPF %s Ja existe cadastrado.", cpf));
		}
	}

	public boolean removeByCPF(String cpf) {
		List<Cliente> clientes = findByCFPAll(cpf);
		
		if (!clientes.isEmpty()) {
			Cliente cliente = clientes.get(0);
			entityManager.remove(cliente);
			return true;
		}
		
		return false;
	}
}
