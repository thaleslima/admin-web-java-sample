package br.com.sistema.util;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

//import net.sf.jasperreports.engine.JRExporterParameter;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

public class UtilFaces {
	public UtilFaces() {
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public static ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}
	
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}
	
	public static HttpSession getHttpSession(){
		return (HttpSession) getFacesContext().getExternalContext().getSession(false);
	}
	
	public static ServletContext getServletContext(){
		return (ServletContext) getExternalContext().getContext();
	}
	
	public static Object getObjetoSessao(String pNome) {
		return (Object) getHttpSession().getAttribute(pNome);
	}
	
	public static void setObjetoSessao(String pNome, Object pObject) {
		getHttpSession().setAttribute(pNome, pObject);
	}
	
	
	public static void msg(String pDestination, String pMsg){
		FacesMessage lMessage = new FacesMessage(pMsg);
		getFacesContext().addMessage(pDestination, lMessage);
	}
	
	public static void addErrorMessage(Exception pEx, String pDefaultMsg) {
		String msg = pEx.getLocalizedMessage();
		if (msg != null && msg.length() > 0) {
			addErrorMessage(msg);
		} else {
			addErrorMessage(pDefaultMsg);
		}
		msg = null;
	}

	public static void addErrorMessages(List<String> pMessages) {
		for (String message : pMessages) {
			addErrorMessage(message);
		}
	}

	
	public static String msgI18n(String pChave)
	{
		String lRetorno = "";
		Locale lLocale = null;
		ResourceBundle lBundle = null;
		
		try{
			lLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			lBundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), lLocale);
			lRetorno = lBundle.getString(pChave);
		}
		catch(Exception ex)
		{
			lRetorno = "";
		}
		finally
		{
			lLocale = null;
			lBundle = null;
		}
		return lRetorno;
	}
	
	public static void addErrorMessage(String pMsg) {
		FacesMessage lFacesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", pMsg);
		FacesContext.getCurrentInstance().addMessage(null, lFacesMsg);
	}
	
	public static void addSuccessMessage(String pMsg) {
		FacesMessage lFacesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", pMsg);
		FacesContext.getCurrentInstance().addMessage(null, lFacesMsg);
	}

	public static void addWarnMessage(String pMsg) {
		FacesMessage lFacesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", pMsg);
		FacesContext.getCurrentInstance().addMessage(null, lFacesMsg);
	}

	public static void addFatalMessage(String pMsg) {
		FacesMessage lFacesMsg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "", pMsg);
		FacesContext.getCurrentInstance().addMessage(null, lFacesMsg);
	}

	public static void toPdf(List<? extends Object> collection, String path, String nameReport ) throws Exception{
		
		
		String realPath = getServletContext().getRealPath("resources\\Reports\\" + path);
		JasperReport report = JasperCompileManager.compileReport(realPath);
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(collection);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap<String, Object>(), beanCollectionDataSource);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\""+nameReport+".pdf\"");
		httpServletResponse.setHeader("Cache-Control", "no-cache");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		servletOutputStream.flush();
		servletOutputStream.close();
		FacesContext.getCurrentInstance().renderResponse();
		FacesContext.getCurrentInstance().responseComplete();
	}

//	public static void toDocx(List<? extends Object> collection, String pathReport, String nameReport) throws Exception {
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(collection);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(pathReport, new HashMap<String, Object>(), beanCollectionDataSource);
//
//		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\""+nameReport+".docx\"");
//		httpServletResponse.setHeader("Cache-Control", "no-cache");
//
//		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//		JRDocxExporter docxExporter = new JRDocxExporter();
//		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
//		docxExporter.exportReport();
//	}
//
//	public static void toXlsx(List<? extends Object> collection, String pathReport, String nameReport) throws Exception {
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(collection);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(pathReport, new HashMap<String, Object>(), beanCollectionDataSource);
//
//		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\""+nameReport+".xlsx\"");
//		httpServletResponse.setHeader("Cache-Control", "no-cache");
//
//		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//		JRXlsxExporter xlsxExporter = new JRXlsxExporter();
//		xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
//		xlsxExporter.exportReport();
//
//	}
//
//	public static void toPPTX(List<? extends Object> collection, String pathReport, String nameReport) throws Exception {
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(collection);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(pathReport, new HashMap<String, Object>(), beanCollectionDataSource);
//
//		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\""+nameReport+".pptx\"");
//		httpServletResponse.setHeader("Cache-Control", "no-cache");
//
//		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//		JRPptxExporter xlsxExporter = new JRPptxExporter();
//		xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
//		xlsxExporter.exportReport();
//
//	}
//
//	public static void toOdt(List<? extends Object> collection, String pathReport, String nameReport) throws Exception {
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(collection);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(pathReport, new HashMap<String, Object>(), beanCollectionDataSource);
//
//		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\""+nameReport+".odt\"");
//		httpServletResponse.setHeader("Cache-Control", "no-cache");
//
//		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//		JROdtExporter xlsxExporter = new JROdtExporter();
//		xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
//		xlsxExporter.exportReport();
//	}
}
