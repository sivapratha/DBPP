<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<html>
<head>
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<base target="_top" />
<title>DBPP Mail</title>
<script language="JavaScript"  type="text/javascript">
 function fnSubmit(){

		var returnObj = new Object();
		// This part of code is added to hold the series and body style variable before user clicks refresh
		var to = document.mailForm.to.value;
		var cc = document.mailForm.cc.value;
		var subject = document.mailForm.subject.value;
		var bodyContent = document.mailForm.bodyContent.value;
		returnObj.to = to;
		returnObj.cc = cc;
		returnObj.subject = subject;
		returnObj.bodyContent = bodyContent;
		window.returnValue = returnObj;
		window.close();				   		   
	}
 </script>
</head>
  
<body background="images/mailBg.jpg" >
<html:form action="loadMailAction.do" method="POST" enctype="multipart/form-data" >   
  
<table class="plainTable">
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
<td>&nbsp;</td>
<td>
<table class="plainTable">
	<tr>
		<td><font color="#006300" ><b>To   </b></font></td>
		<td><html:text property="to" styleId="to"  name="mailForm" size="50"/></td>
	</tr>
	<tr>
		<td><font color="#006300"><b>CC   </b></font></td>
		<td><html:text property="cc" styleId="cc"  name="mailForm"  size="50"/></td>
	</tr>
	<tr>
		<td><font color="#006300"><b>Subject   </b></font></td>
		<td><html:text property="subject" styleId="subject" name="mailForm"  size="50"/></td>
	</tr>
	<tr>
		<td><font color="#006300"><b>Body Content   </b></font></td>
		<td><html:textarea style="width: 500px; height: 200px; " property="bodyContent" styleId="bodyContent" name="mailForm"/></td>
	</tr>	
<tr><td colspan="2" align="center"><a href="#"  onclick="fnSubmit()"><img src="images/emailIcon.png" height="70" width="90" alt="Click here to Send Mail" onclick=""/></a></td></tr>
</table>
</td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
</table>

</html:form>
</body>
</html>