package br.com.sistema.data;

import java.util.List;

import org.hibernate.Session;

import br.com.sistema.core.BaseDAO;
import br.com.sistema.entity.Grupo;
import br.com.sistema.entity.Usuario;

public class UsuarioDAO extends BaseDAO<Usuario> {
	public UsuarioDAO() {
	}
	
	public UsuarioDAO(Session session) {
		super(session);
	}
	
	@Override
	protected String nomeTabela() {
		return "Usuario2";
	}

	
	public List<Grupo> retornaGrupos(int idUsuario, boolean notIn) throws Exception
	{
		GrupoDAO grupoDAO = new GrupoDAO();
		
		if(notIn)
		{
			return grupoDAO.select("id not in (select c.id from Grupo c inner join c.usuarios u where u.id = ?)", idUsuario);
		}
		else
		{
			return grupoDAO.select("id in (select c.id from Grupo c inner join c.usuarios u where u.id = ?)", idUsuario);
		}
	}
}
