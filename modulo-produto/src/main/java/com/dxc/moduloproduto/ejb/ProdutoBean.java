package com.dxc.moduloproduto.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dxc.moduloproduto.dao.ProdutoDao;
import com.dxc.moduloproduto.modulo.Produto;

@Stateless(mappedName = "ProdutoBean")
@LocalBean
public class ProdutoBean implements ProdutoBeanRemote {
	
	@PersistenceContext(unitName = "produtoPU")
	private EntityManager entityManager;
	
	public List<Produto> findAll() {
		ProdutoDao dao = new ProdutoDao(entityManager);
		return dao.findAll();
	}
	

	public String hello() {
		return "Ola pessoal!!! Bemvindo ao EJB!!!" + new Date();
	}


	public Produto findById(Long id) {
		ProdutoDao dao = new ProdutoDao(entityManager);
		return dao.findById(id);
	}


	public Produto save(Produto produto) {
		ProdutoDao dao = new ProdutoDao(entityManager);
		return dao.save(produto);
	}


	public boolean removePeloId(Long id) {
		ProdutoDao dao = new ProdutoDao(entityManager);
		return dao.removePeloId(id);
	}

}
