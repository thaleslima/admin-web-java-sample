package br.com.sistema.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class BaseDAO<T> {

	private Session session;
	private Transaction tran;
	private boolean abriuSessao;
	private String orderBy = null;
	private String columns = null;
	private Map<String, Object> fieldsUpdate = null;
	
	public Session getSession() {
		if (session == null) {
			session = HibernateUtil.getSession();
			tran = session.beginTransaction();
		}

		return session;
	}

	protected BaseDAO(Session session) {
		this.session = session;
		abriuSessao = true;
	}

	protected BaseDAO() {

	}

	private void closeSession() {
		if (!abriuSessao) {
			tran.commit();
			session.close();
		}
		
		orderBy = null;
		columns = null;
		fieldsUpdate = null;
	}

	
	
	public void columnsSelect(String columns)
	{
		if (this.columns == null)
			this.columns = "";
		this.columns+=columns;
	}
	
	public void orderBy(String orderBy)
	{
		this.orderBy=orderBy;
	}
		
	public List<T> select() throws Exception {
		return select(null);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> select(String where, Object... whereArgs) throws Exception {
		List<T> retorno = new ArrayList<T>();

		Query q = null;
		String query;

		try {
			
			if(columns == null)
				columns = "";			
			if(orderBy == null)
				orderBy = "";
			if(where == null)
				where = "";
			
			if(columns != "")
				columns = "select " + columns;
			
			query = columns + " from " + nomeTabela() + "";
			query += where.length() > 0 ? " where " + where : "";
			query += orderBy.length() > 0 ? " order by " + orderBy : "";
			
			q = getSession().createQuery(query);
			
			for (int i = 0; i < whereArgs.length; i++) {
				q.setParameter(i, whereArgs[i]);
			}
			
			retorno = q.list();
		} finally {
			closeSession();
		}
		return retorno;
	}
	
	public int insert(T entidade) throws Exception {
		int retorno = 0;
		try {

			getSession().persist(entidade);

		} finally {
			closeSession();
		}

		return retorno;
	}

	public boolean update(T entidade) throws Exception {
		boolean retorno = false;
		try {
			getSession().merge(entidade);
		} finally {
			closeSession();
		}

		return retorno;
	}
	
	public void fieldUpdate(String column, Object value)
	{
		if(fieldsUpdate == null)
			fieldsUpdate = new HashMap<String, Object>();
		
		fieldsUpdate.put(column, value);
	}
	
	public boolean update(String where, Object... whereArgs) throws Exception {
		boolean retorno = false;
		Query q = null;
		StringBuilder query = new StringBuilder();
		List<Object> parametros = new ArrayList<Object>();
		boolean virgula = false;
		
		try {
			
			if(where == null)
				where = "";
			
			query.append("update "+ nomeTabela() + " set ");
			
			if(fieldsUpdate != null)
			{
				for (Entry<String, Object> field : fieldsUpdate.entrySet()) {
					
					if(virgula)
						query.append(" , ");
					else
						virgula = true;
					if (field.getKey().contains("="))
						query.append(field.getKey());
					else
						query.append(field.getKey() + " = ?");
					
					parametros.add(field.getValue());
				}
			}
			query.append(where.length() > 0 ? " where " + where : "");
			if(whereArgs.length > 0)
			{
				parametros.addAll(Arrays.asList(whereArgs));
			}
			q = getSession().createQuery(query.toString());
			
			for (int i = 0; i < parametros.size(); i++) {
				q.setParameter(i, parametros.get(i));
			}
			
			q.executeUpdate();
			
		} finally {
			closeSession();
		}
		return retorno;
	}
	
	public boolean delete(T entidade) throws Exception {
		boolean retorno = false;

		try {
			getSession().delete(entidade);
		} catch (Exception e) {
			throw e;
		} finally {
			closeSession();
		}
		return retorno;
	}

	public boolean delete(String where, Object... whereArgs) throws Exception {
		boolean retorno = false;
		Query q = null;
		StringBuilder query = new StringBuilder();
		
		try {
			
			if(where == null)
				where = "";
			
			query.append("delete "+ nomeTabela() + " ");
			query.append(where.length() > 0 ? " where " + where : "");
			
			q = getSession().createQuery(query.toString());
			
			for (int i = 0; i < whereArgs.length; i++) {
				q.setParameter(i, whereArgs[i]);
			}
			
			q.executeUpdate();
			
		} finally {
			closeSession();
		}
		return retorno;
	}
	
	
	@SuppressWarnings("unchecked")
	public T consultaEntidade(Class<? extends Object> class1, Serializable arg1) {
		T retorno = null;
		try {
			retorno = (T) getSession().byId(class1).load(arg1);
		} catch (Exception e) {
			return null;
		}
		
		return retorno;
	}

	protected abstract String nomeTabela();

}
