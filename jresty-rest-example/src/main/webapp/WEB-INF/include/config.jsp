<%
	boolean devMode = true; 
	
	String jsRootVersion = "201101131828"; 
	String jsRoot = "" + jsRootVersion + "/";
	
	String cssRootVersion = "201101131832";
	String cssRoot = "" + cssRootVersion + "/";
	
	String imageRoot = "" + cssRootVersion + "/image/";	
	
	
	if(devMode) {
		jsRoot = "/resources/js/" ;
		cssRoot = "/resources/css/";
		imageRoot = "/resources/image/";		
	}
%>
