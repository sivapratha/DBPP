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
<SCRIPT language="javascript">
function fnSubmit() {	
	
	var returnObj = new Object();			
	returnObj.userId =document.userForm.userId.value;
	returnObj.firstName = document.userForm.firstName.value;
	returnObj.lastName = document.userForm.lastName.value;
	returnObj.passWord = document.userForm.passWord.value;
	returnObj.emailID = document.userForm.emailID.value;
	
	window.returnValue = returnObj;
	window.close();				   		   
}
</SCRIPT>
<title>DBPP Configure Databases</title>
</head>
  
<body  background="images/mailBg.jpg">
<html:form action="addUserAction.do" method="POST" enctype="multipart/form-data" >   


<table class="plainTable">
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
<tr><td>&nbsp;&nbsp;</td><td> <p class="fontstyle-green">Enter the below details to add a User </p></td></tr>
<tr><td colspan="2" >&nbsp;&nbsp;</td></tr>
<tr>
<td>&nbsp;&nbsp;</td>
<td align="center">
<table class="plainTable">
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>User Id     </b></font></td>
		<td><html:text property="userId"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>First Name   </b></font></td>
		<td><html:text property="firstName"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Last Name   </b></font></td>
		<td><html:text property="lastName"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Email Id   </b></font></td>
		<td><html:text property="emailID"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Password   </b></font></td>
		<td><html:password property="passWord"/></td>
	</tr>
	<tr><td colspan="2" >&nbsp;</td></tr>
	<tr><td colspan="2" align="center"><html:button property="save" onclick="fnSubmit()" styleClass="button">Save</html:button></td></tr>

</table>
</td>
</tr>
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
</table>

   </div>
</html:form>
</body>
</html>