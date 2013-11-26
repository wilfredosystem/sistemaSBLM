package com.sblm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


public class FuncionesHelper {

	private static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat dfh = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//	public static String directorioPrincipalLibreria = "C:\\Users\\Desarrollo\\Desktop\\sblmSysv0.3\\sistemaSBLM\\WebContent\\resources\\documents\\";
//	public static String directorioPrincipalLibreria = "C:\\Users\\Informatica\\Desktop\\WokspaceGit\\sistemaSBLM\\WebContent\\resources\\documents\\";
	public static String curDir = System.getProperty("user.dir");
	public static String directorioPrincipalLibreria=curDir	+ "\\webapps\\sistemaSBLM\\resources\\documents\\";
	public static int idperfilseleccionado;
	public static String nombreperfilSeleccionado;

		
	public static Integer getValEntero(Boolean valor) {
        Integer activo=0;
        if (valor){
        	activo=1;
        }
    	return activo;
    }
	
	public static String notNullFechaHora(Date fecha){
		if (fecha == null){
			return "NULL";
		}else{
			return "'"+fechaHoraToString(fecha)+"'";
		}
	}
	
	public static String fechaHoraToString(Date fecha){
		return dfh.format(fecha);
	}
	
	public static String fechaToString(Date fecha){
		return df.format(fecha);
	}
	
	public static String getTerminal(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getRemoteAddr().toString();
	}
	
	public static Object getUsuario(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		Object segUsu = (Object) request.getSession().getAttribute("usuario");
		return segUsu;
	}
	
	public static String getURL(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer 	requestURL=request.getRequestURL();
		String queryString = request.getQueryString();
		
		  if (queryString == null) {
		        return requestURL.toString();
		    } else {
		        return requestURL.append('?').append(queryString).toString();
		    }
		
		
	}

	
	
}
