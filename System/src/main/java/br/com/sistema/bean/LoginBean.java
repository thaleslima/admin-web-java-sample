package br.com.sistema.bean;


import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.sistema.data.UsuarioDAO;
import br.com.sistema.entity.Usuario;
import br.com.sistema.util.UtilFaces;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private boolean logado;
	private String continuePage = "";
	private Locale locale;

	public String getContinuePage() {
		return continuePage;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setContinuePage(String continuePage) {
		this.continuePage = continuePage;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LoginBean() {
		usuario = new Usuario();
		logado = false;
	}

	public String validaLogin() {
		UsuarioDAO usuarioDAO = null;
		List<Usuario> listUsuario = null;
		
		try {
			
			if (this.usuario.getUsername().equals("") || this.usuario.getSenha().equals("")) {
				UtilFaces.addErrorMessage("Login inválido");
				return "login.jsf";
			}

			Logger logger = Logger.getLogger("lopes");

			logger.info("iniciando aplicação");
			
			usuarioDAO = new UsuarioDAO();			
			listUsuario = usuarioDAO.select("username = ? and senha = ?", this.usuario.getUsername(), this.usuario.getSenha());

			if (listUsuario.size() > 0) {
				this.usuario = listUsuario.get(0);
				logado = true;

				alterarIdioma();

				if (continuePage.trim().equals("")) {
					return "Inicial/index.jsf?faces-redirect=true";
				} else {
					return continuePage + ".jsf?faces-redirect=true";
				}
			} else {
				UtilFaces.addErrorMessage("Login não encontrado / Usuário ou senha incorretos.");
			}

		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
			return "login.jsf";
		}
		return "login.jsf";
	}

	public void alterarIdioma()
	{
		if (usuario.getIdioma().equals("1")) {
			locale = new Locale("pt", "BR");
		} else {
			locale = new Locale("en", "US");
		}
	}
	
	public String sair() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginBean");
		return "./../login.jsf?faces-redirect=true";
	}

	
	public boolean seguranca()
	{
		try {
		//	for (int i : codGrupo) {
				
		//		for (Grupo g : usuario.getGrupos()) {
		//			if(g.getId() == i)
		//			{
		//				return true;
		//			}
		//		}
			//}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
