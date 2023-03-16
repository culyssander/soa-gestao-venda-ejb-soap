package com.dxc.moduloproduto.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.dxc.moduloproduto.modulo.Produto;

@Remote
public interface ProdutoBeanRemote {
	
	List<Produto> findAll();
	Produto findById(Long id);
	Produto save(Produto produto);
	boolean removePeloId(Long id);
	
	String hello();

}
