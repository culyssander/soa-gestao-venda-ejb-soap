package com.dxc.modulotest.test;

import java.math.BigDecimal;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.dxc.moduloproduto.ejb.ProdutoBeanRemote;
import com.dxc.moduloproduto.modulo.Produto;

public class ProdutoTest {
	
	public static void main(String[] args) throws NamingException {
		
		Hashtable<String, String> env = new Hashtable<>(5);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		env.put(Context.PROVIDER_URL, "t3://localhost:7001");
		
		InitialContext context = new InitialContext(env);
		
		ProdutoBeanRemote bean = (ProdutoBeanRemote) context.lookup("ProdutoBean#com.dxc.moduloproduto.ejb.ProdutoBeanRemote");
		
		long id = 2;
		
		Produto produto = new Produto();
		produto.setNome("FINAL TERMINAMOS!!!");
		produto.setPreco(BigDecimal.valueOf(500));
		//produto.setQ
		
		
		
		
		System.out.println(bean.save(produto));
	}

}
