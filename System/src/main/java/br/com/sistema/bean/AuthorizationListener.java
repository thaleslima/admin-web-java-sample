package br.com.sistema.bean;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpSession;

public class AuthorizationListener implements javax.faces.event.PhaseListener {
	private static final long serialVersionUID = 1L;
	
	
	public void afterPhase(PhaseEvent event) {		
		FacesContext lfacesContext = event.getFacesContext();
		String lcurrentPage = lfacesContext.getViewRoot().getViewId();
		
		boolean lisConfig = (lcurrentPage.lastIndexOf("config.xhtml") > -1);
		
		if (!lisConfig)
		{
			boolean lisLoginPage = (lcurrentPage.lastIndexOf("login.xhtml") > -1);
			
			HttpSession lsession = (HttpSession) lfacesContext.getExternalContext().getSession(true);
			boolean lcurrentUser = false;
			
			if(lsession.getAttribute("loginBean") != null){
				lcurrentUser = ((br.com.sistema.bean.LoginBean) lsession.getAttribute("loginBean")).isLogado();
			}
			
			if (!lisLoginPage && !lcurrentUser) {
				lcurrentPage = lcurrentPage.replace(".xhtml", "");
				NavigationHandler nh = lfacesContext.getApplication().getNavigationHandler();
				nh.handleNavigation(lfacesContext, null, "./../login.xhtml?faces-redirect=true&continuePage="+lcurrentPage);
			}
			
			if (lisLoginPage && lcurrentUser) {
				NavigationHandler nh = lfacesContext.getApplication().getNavigationHandler();
				nh.handleNavigation(lfacesContext, null, "./Inicial/index.xhtml?faces-redirect=true");
			}
		}
	}

	
	public void beforePhase(PhaseEvent arg0) {
	}

	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	 

 
}
