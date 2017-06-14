<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
     <link href="static/DBPP.css" rel="stylesheet" type="text/css">
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<base target="_top" />
<SCRIPT language="javascript">
function fnSubmit() {	
		
		var returnObj = new Object();			
		returnObj.dbName =document.dbConfigForm.dbName.value;
		returnObj.serverName = document.dbConfigForm.serverName.value;
		returnObj.portNumber = document.dbConfigForm.portNumber.value;
		returnObj.sid = document.dbConfigForm.sid.value;
		returnObj.userName = document.dbConfigForm.userName.value;
		returnObj.passWord = document.dbConfigForm.passWord.value;
		
		window.returnValue = returnObj;
		window.close();				   		   
	}
</SCRIPT>
<title>DBPP Configure Databases</title>
</head>
 <% 
  String resultMsg = "";
  if (request.getAttribute("RESULT_MSG") != null)
	resultMsg = (String) request.getAttribute("RESULT_MSG");
%>
<body  background="images/mailBg.jpg" >
 
<html:form action="addDBConfig.do"  method="POST" enctype="multipart/form-data" >  

<table class="plainTable">
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
<tr><td>&nbsp;&nbsp;</td><td> <p class="fontstyle-green">Enter the below details to add a database </p></td></tr>
<tr><td colspan="2" >&nbsp;&nbsp;</td></tr>
<tr>
<td>&nbsp;&nbsp;</td>
<td align="center">
<table class="plainTable">
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Database Name     </b></font></td>
		<td><html:text property="dbName"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Server Name   </b></font></td>
		<td><html:text property="serverName"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Port Number   </b></font></td>
		<td><html:text property="portNumber"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Service Name   </b></font></td>
		<td><html:text property="sid"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>User Name   </b></font></td>
		<td><html:text property="userName"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Password   </b></font></td>
		<td><html:password property="passWord"/></td>
	</tr>
	<tr><td colspan="2" >&nbsp;</td></tr>
	<tr><td colspan="2" align="center"><html:button property="save" onclick="fnSubmit()" styleClass="button">Add Database</html:button></td></tr>

</table>
</td>
</tr>
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
</table>

   <div>
   <br/>
  
   

   </div>
</html:form>
</body>
</html>