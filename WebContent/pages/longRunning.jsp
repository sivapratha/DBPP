<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<html>
<head>
  <link href="static/DBPP.css" rel="stylesheet" type="text/css">
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<base target="_top" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript"  type="text/javascript">

function processOnLoad(){
	 

	 var selectedDb = document.compareDBForm.selectedDb.value;
	 var slctdDbObj = document.compareDBForm.slctdDbObj.value;
	 var structCompType = document.compareDBForm.structCompType.value;

	 var xmlHttpReq = false;
		var self = this;
		// Mozilla/Safari
		if (window.XMLHttpRequest) {
			self.xmlHttpReq = new XMLHttpRequest();
		}
		// IE
		else if (window.ActiveXObject) {
			self.xmlHttpReq = new ActiveXObject('Microsoft.XMLHTTP');
		}
		var url = 'ObjectContentResult.do?';
		var parameters='selectedDb='+selectedDb
						+'&slctdDbObj='+slctdDbObj
						+'&structCompType='+structCompType;
		self.xmlHttpReq.open('POST', url, true);
		self.xmlHttpReq.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		self.xmlHttpReq.onreadystatechange = function() {
			if (self.xmlHttpReq.readyState == 4) {
				document.compareDBForm.action = 'ObjectContCompRslt.do';
				document.compareDBForm.submit();
			}
		};
		self.xmlHttpReq.send(parameters);
	}
 
	
 </script>
</head>
  
<body onload="processOnLoad();">
<html:form action="compareDB.do" method="POST" enctype="multipart/form-data" >   
	<html:hidden property="dbName" styleId="dbName" name="compareDBForm"/>
    <html:hidden property="selectedDb" styleId="selectedDb" name="compareDBForm"/>
    <html:hidden property="slctdDbObj" styleId="slctdDbObj" name="compareDBForm"/>
    <html:hidden property="structCompType" styleId="structCompType" name="compareDBForm"/>

   <div>
   <img src="images/loading.gif" alt="Loading" />
   </div>
   
</html:form>

</body>
</html>