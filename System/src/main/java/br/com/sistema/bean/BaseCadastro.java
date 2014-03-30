package br.com.sistema.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import br.com.sistema.core.BaseDAO;
import br.com.sistema.util.UtilFaces;


public abstract class BaseCadastro<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	private BaseDAO<T> baseDAO;
	private T entidade;
	private T entidadePes;
	private DataModel<T> dmEntidade;
	private List<T> listEntidades;
	private String tipo;
	private int tabIndex;
	private boolean exportarDados;
	private boolean relatorio;
	private InsertUpdate insertUpdate;
	private String orderBy;
	
	enum InsertUpdate {
		Insert, Update
	};
	
	enum Report {
		PDF, DOCX, XLSX, PPTX, ODT
	};

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public T getEntidadePes() {
		return entidadePes;
	}

	public void setEntidadePes(T entidadePes) {
		this.entidadePes = entidadePes;
	}

	public DataModel<T> getDmEntidade() {
		return dmEntidade;
	}

	public void setDmEntidade(DataModel<T> dmEntidade) {
		this.dmEntidade = dmEntidade;
	}

	public List<T> getListEntidades() {
		return listEntidades;
	}

	public void setListEntidades(List<T> listEntidades) {
		this.listEntidades = listEntidades;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isExportarDados() {
		return exportarDados;
	}

	public void setExportarDados(boolean exportarDados) {
		this.exportarDados = exportarDados;
	}

	public boolean isRelatorio() {
		return relatorio;
	}

	public void setRelatorio(boolean relatorio) {
		this.relatorio = relatorio;
	}

	public InsertUpdate getInsertUpdate() {
		return insertUpdate;
	}

	public void setInsertUpdate(InsertUpdate insertUpdate) {
		this.insertUpdate = insertUpdate;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void limpar()
	{
		iniciaCampos();
	}
	
	public BaseCadastro()
	{
		iniciaCampos();
	}
	
	protected void iniciaCampos() {
		entidade = getNewEntidade();
		entidadePes = getNewEntidade();
		dmEntidade = null;		
		tabIndex = 0;
		exportarDados = true;
		relatorio = true;
		insertUpdate = InsertUpdate.Insert;
		tipo = UtilFaces.msgI18n("cadastro.tipo.inserir");
	}
	
	public void rowToDelete() {
		entidade = dmEntidade.getRowData();
	}
	
	public void confirmarEntidade() {
		try {
			baseDAO = newBaseDAO();
			if (insertUpdate == InsertUpdate.Insert) {
				baseDAO.insert(getEntidadeInsertUpdate());
				UtilFaces.addSuccessMessage(UtilFaces.msgI18n("cadastro.inserirSucesso"));
				iniciaCampos();
			} else {
				baseDAO.update(getEntidadeInsertUpdate());
				pesquizarDados();
				UtilFaces.addSuccessMessage(UtilFaces.msgI18n("cadastro.atualizarSucesso"));
			}
			
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	public void removerEntidade() {
		try {
			baseDAO = newBaseDAO();
			baseDAO.delete(getEntidade());
			pesquizarDados();
			UtilFaces.addSuccessMessage(UtilFaces.msgI18n("cadastro.excluirSucesso"));
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	public void inserirEntidade()
	{	
		iniciaCampos();	
	}
	
	public void editarEntidade(){
		try
		{
			baseDAO = newBaseDAO();
			entidade = getDmEntidade().getRowData();		
			entidade = baseDAO.consultaEntidade(entidade.getClass(), retornaChave());
			insertUpdate = InsertUpdate.Update;
			tipo = UtilFaces.msgI18n("cadastro.tipo.atualizar");
			tabIndex = 0;
		}
		catch(Exception ex)
		{
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	public void pesquisarEntidades() {
		try {
			pesquizarDados();
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	protected void pesquizarDados() throws Exception {
		baseDAO = newBaseDAO();
		String where = "";
		List<Object> whereArgs = new ArrayList<Object>();
		preencheWhere(where, whereArgs);
		listEntidades = baseDAO.select(where, whereArgs.toArray());
		dmEntidade = newListDataModel(listEntidades);
		if (listEntidades.size() > 0) {
			relatorio = false;
			exportarDados = false;
		}
	}
	
	protected abstract T getNewEntidade();
	protected abstract T getEntidadeInsertUpdate();
	protected abstract void preencheWhere(String where, List<Object> whereArgs);
	protected abstract ListDataModel<T> newListDataModel(List<T> entidade);
	protected abstract Serializable retornaChave();
	protected abstract BaseDAO<T> newBaseDAO();
	public abstract void toPdf();
	public abstract void toDocx();
	public abstract void toXlsx();
	public abstract void toPptx();
	public abstract void toOdt();
}
