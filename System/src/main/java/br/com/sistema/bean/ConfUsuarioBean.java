package br.com.sistema.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.sistema.data.UsuarioDAO;
import br.com.sistema.entity.Usuario;
import br.com.sistema.util.UtilFaces;

@ManagedBean
@RequestScoped
public class ConfUsuarioBean{

	private Usuario usuario;

	public ConfUsuarioBean() {
		usuario = new Usuario();
	}

	public void carregarUsuario() {

		UsuarioDAO usuarioDAO = null;
		LoginBean loginBean;

		try {
			loginBean = (LoginBean) UtilFaces.getObjetoSessao("loginBean");			
			usuarioDAO = new UsuarioDAO();
			
			usuario = usuarioDAO.consultaEntidade(Usuario.class, loginBean.getUsuario().getId());
			
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}

	public String confirmar()
	{
		LoginBean lusuario;
		UsuarioDAO usuarioDAO = null;
		
		try {
			lusuario = (LoginBean) UtilFaces.getObjetoSessao("loginBean");
			usuarioDAO = new UsuarioDAO();
			
			
			usuarioDAO.fieldUpdate("nome", usuario.getNome());
			usuarioDAO.fieldUpdate("email", usuario.getEmail());
			usuarioDAO.fieldUpdate("idioma", usuario.getIdioma());
			usuarioDAO.update("id = ?", lusuario.getUsuario().getId());
			
			lusuario.getUsuario().setNome(usuario.getNome());
			lusuario.getUsuario().setEmail(usuario.getEmail());
			lusuario.getUsuario().setIdioma(usuario.getIdioma());
			
			lusuario.alterarIdioma();
			
			UtilFaces.setObjetoSessao("loginBean", lusuario); 
			
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		} 
		return "";
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
