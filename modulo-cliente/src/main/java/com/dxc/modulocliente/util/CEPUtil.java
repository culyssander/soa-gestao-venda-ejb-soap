package com.dxc.modulocliente.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.dxc.modulocliente.modelo.Endereco;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class CEPUtil {
	
	public Endereco buscarEnderecoPeloCEP(String cep) {
		HttpURLConnection conexao;
		String url = String.format("http://viacep.com.br/ws/%s/json", cep);
		
		if (cep == null || cep.isEmpty()) {
			return null;
		}
		
		try {
			URL urlNormal = new URL(url);
			
			conexao = (HttpURLConnection) urlNormal.openConnection();
			conexao.setConnectTimeout(250000);
			conexao.setRequestMethod("GET");
			conexao.setUseCaches (false);
			conexao.setDoInput(true);

			BufferedReader bin = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "UTF-8"));

			String linha = "";
			StringBuilder json = new StringBuilder();
			
			while ((linha = bin.readLine()) != null){
				json.append(linha);
			}
			
			bin.close();
			
			return converteJsonToObject(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Endereco converteJsonToObject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Endereco endereco = new Endereco();
		
		try {
			 endereco = mapper.readValue(json, Endereco.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return endereco;
	}

}
 