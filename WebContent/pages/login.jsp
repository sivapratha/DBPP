<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<html>
<head>
     <link href="static/DBPP.css" rel="stylesheet" type="text/css">
<title>Welcome to DBPP</title>
<SCRIPT language="javascript">
    function logInAction(){
    	document.loginForm.action = "loginAction.do";
		document.loginForm.submit();
		
    }
    
    function changePassword(){
    	
    	var result = window.showModalDialog('loadChangePassword.do', null, 
		"dialogWidth:300px;dialogHeight:300px;center:yes;scroll:no;status:no");
   }
    
    
    </SCRIPT>
    <% 
  String resultMsg = "";
  if (request.getAttribute("RESULT_MSG") != null)
	resultMsg = (String) request.getAttribute("RESULT_MSG");
%>
</head>
  
<body>
<html:form action="loginAction.do" method="POST" enctype="multipart/form-data" >  
<html:hidden property="newPassWord" styleId="newPassWord"  name="loginForm"/>	
<html:hidden property="oldPassWord" styleId="oldPassWord"  name="loginForm"/>	
<html:hidden property="userId" styleId="userId"  name="loginForm"/>	

<div>

</div> 
<table class="plainTable">
<tr><td width="500" align="right"><img height="400" width="346" src="images/login.jpg"/>&nbsp;&nbsp;</td><!-- height="400" width="346"  -->
<td align="justify">
   <div>
   <table width="100%" class="plainTable">
      <% if (resultMsg != null && resultMsg != "" && resultMsg.equals("Error")) { %> 	
      <tr>
        <td class="fontstyle-red"  align="justify">
         Failed to login. Please check your user name and password
        </td>
      </tr>  
   	  <% } %>   	   
   	</table> 
   <p class="fontstyle-green">Enter User name and Password to Login.</p>
   
<table class="plainTable">
	<tr>
		<td class="fontstyle-green">User Id : </td>
		<td><html:text property="userName"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">Password : </td>
		<td><html:password property="passWord"/></td>
	</tr>
	<tr><td><br/></td></tr>
	<tr align="center">		
		<td><html:button property="save" onclick="changePassword()"  styleClass="button">Change Password</html:button></td>
		<td align="center"><html:button property="save" onclick="logInAction()"  styleClass="button">Log In</html:button></td>
	</tr>

</table>
   </div>
   </td>
   </tr>
   </table>
</html:form>
</body>
</html>