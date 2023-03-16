package com.dxc.moduloproduto.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.dxc.moduloproduto.exception.EntidadeNaoEncontradaException;
import com.dxc.moduloproduto.exception.NegocioException;
import com.dxc.moduloproduto.modulo.Produto;

public class ProdutoDao {
	
	private EntityManager entityManager;
	
	public ProdutoDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Produto> findAll() {
		return entityManager.createQuery("FROM Produto p", Produto.class).getResultList();
	}
	
	public Produto findById(Long id) {
		Produto produto = entityManager.find(Produto.class, id);
		
		if (produto == null) {
			throw new EntidadeNaoEncontradaException("Produto nao encontrado.");
		}
		
		return produto;
	}
	
	public Produto save(Produto produto) {
		if (produto.getId() == null) {
			validaProdutoExiste(produto.getNome());
			entityManager.persist(produto);
		} else {
			
			if(!entityManager.contains(produto)) {
				if(entityManager.find(Produto.class, produto.getId()) == null) {
					throw new NegocioException("Entidade nao faz parte do contexto de gerenciamento de persistencia");
				}
			}
			
			entityManager.merge(produto);
		}
		
		return produto;
	}
	
	public boolean removePeloId(Long id) {
		Produto produto = findById(id);
		
		if (produto != null) {
			entityManager.remove(produto);
			return true;
		}
		
		return false;
	}

	private void validaProdutoExiste(String nome) {
		String sql = "FROM Produto p WHERE p.nome = :nome";
		List<Produto> produto = entityManager.createQuery(sql, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
		
		if (!produto.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Produto %s ja existe cadastrado.", nome));
		}
	}
	
}
