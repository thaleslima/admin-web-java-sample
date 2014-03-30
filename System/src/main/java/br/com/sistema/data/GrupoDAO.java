package br.com.sistema.data;

import org.hibernate.Session;

import br.com.sistema.core.BaseDAO;
import br.com.sistema.entity.Grupo;

public class GrupoDAO extends BaseDAO<Grupo> {

	public GrupoDAO()
	{
		
	}
	
	public GrupoDAO(Session session) {
		super(session);
	}
	
	@Override
	protected String nomeTabela() {
		return "Grupo";
	}

}
