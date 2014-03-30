package br.com.sistema.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.sistema.data.GrupoDAO;
import br.com.sistema.entity.Grupo;


public class Principal {

	/**
	 * @param args
	 */
	public static void main(String... args) {
		
		
		
		try {
			
			Logger logger = Logger.getLogger(Principal.class.getName());
			logger.info("iniciando aplicação");
			
			
			
			GrupoDAO grupoDAO = new GrupoDAO();
			List<Grupo> lsource = new ArrayList<Grupo>();
			
			//grupoDAO.columnsSelect("nome");
			//grupoDAO.orderBy("nome");
			
			lsource = grupoDAO.select("id > ?", 2);
			
			
			//grupoDAO.fieldUpdate("teste = teste + ?", 3);
			//grupoDAO.update("id = ?", 2);
			
			//grupoDAO.delete("");
			
			
			System.out.print(lsource.get(10));
			
			

		} catch (Exception e) {
			
			Logger.getLogger(Principal.class.getName()).error(e.getMessage());
			
			System.out.print(e.getMessage());
		}
				
	}

}
