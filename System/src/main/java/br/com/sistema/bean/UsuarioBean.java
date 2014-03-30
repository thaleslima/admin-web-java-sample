package br.com.sistema.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.primefaces.model.DualListModel;

import br.com.sistema.core.BaseDAO;
import br.com.sistema.data.GrupoDAO;
import br.com.sistema.data.UsuarioDAO;
import br.com.sistema.entity.Grupo;
import br.com.sistema.entity.Usuario;
import br.com.sistema.util.UtilFaces;

@ManagedBean
@ViewScoped
public class UsuarioBean extends BaseCadastro<Usuario> {
	private static final long serialVersionUID = 1L;
	private DualListModel<Grupo> grupos;
	

	public DualListModel<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(DualListModel<Grupo> grupos) {
		this.grupos = grupos;
	}

	@Override
	public void iniciaCampos()
	{	
		super.iniciaCampos();
		grupos = new DualListModel<Grupo>(new ArrayList<Grupo>(), new ArrayList<Grupo>());
	}
	
	@Override
	protected Usuario getNewEntidade() {
		return new Usuario();
	}
	
	@Override
	protected Usuario getEntidadeInsertUpdate() {
		
		getEntidade().setGrupos(new ArrayList<Grupo>());

		for (Object item : grupos.getTarget()) {
			getEntidade().getGrupos().add(new Grupo(Integer.parseInt(item.toString()), ""));
		}

		return getEntidade();
	}

	@Override
	protected void preencheWhere(String where, List<Object> whereArgs) {		
		try {
			if(getEntidadePes().getNome() != null &&  !getEntidadePes().getNome().equals(""))
			{
				where = "nome like ?";
				whereArgs.add(getEntidadePes().getNome() + "%");
			}

			if(getEntidadePes().getId() > 0)
			{
				where = (where.trim().length() > 0 ? " AND " : "");
				where = "id = ?";
				whereArgs.add(getEntidadePes().getId());
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void inserirEntidade()
	{
		GrupoDAO grupoDAO = null;
		List<Grupo> target = null; 
		List<Grupo> source = null;  
		
		try
		{
			super.inserirEntidade();
			grupoDAO = new GrupoDAO();
			target = new ArrayList<Grupo>();
			source = grupoDAO.select();
			grupos = new DualListModel<Grupo>(source, target);
		}
		catch(Exception ex)
		{
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	@Override
	public void editarEntidade() {
		UsuarioDAO usuario2DAO = null;
		List<Grupo> lsource = null;
		
		try
		{
			super.editarEntidade();
			usuario2DAO = new UsuarioDAO();
			lsource = usuario2DAO.retornaGrupos(getEntidade().getId(), true);
			grupos = new DualListModel<Grupo>(lsource, getEntidade().getGrupos());
		}
		catch(Exception ex)
		{
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	@Override
	protected ListDataModel<Usuario> newListDataModel(List<Usuario> entidade) {
		return new ListDataModel<Usuario>(entidade);
	}

	@Override
	protected Serializable retornaChave() {
		return getEntidade().getId();
	}

	@Override
	protected BaseDAO<Usuario> newBaseDAO() {
		return new UsuarioDAO();
	}

	@Override
	public void toPdf() {
		
	}

	@Override
	public void toDocx() {
		
	}

	@Override
	public void toXlsx() {
		
	}

	@Override
	public void toPptx() {
		
	}

	@Override
	public void toOdt() {
		
	}
	
	
}
