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
<title>Welcome to DBPP</title>

<script language="JavaScript"  type="text/javascript">
 function fnSubmit(){

		newPassWord = document.loginForm.newPassWord.value;
		oldPassWord = document.loginForm.oldPassWord.value;
		userId = document.loginForm.userId.value;

		var xmlHttpReq = false;
		var self = this;
		// Mozilla/Safari
		if (window.XMLHttpRequest) {
			self.xmlHttpReq = new XMLHttpRequest();
		}
		// IE
		else if (window.ActiveXObject) {
			self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
		}
		var url = "changePassword.do?";
		var parameters="newPassWord=" + newPassWord+"&oldPassWord="+oldPassWord
						+"&userId="+userId;

		self.xmlHttpReq.open('POST', url, false);
		self.xmlHttpReq.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		self.xmlHttpReq.onreadystatechange = function() {
			if (self.xmlHttpReq.readyState == 4) {
				retValue = self.xmlHttpReq.responseText;
				if(retValue == "Success"){
					alert("Updated Successfully");
					window.close();
				} else {
					alert("Change Password Failed");
				}
				
				
			}
		}
		self.xmlHttpReq.send(parameters);				   		   
	}
 </script>
</head>
  
<body   background="images/mailBg.jpg" >
<html:form action="changePassword.do" method="POST" enctype="multipart/form-data" >   


   <div>
   
   <table class="plainTable">
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
<tr><td>&nbsp;&nbsp;</td><td> <p class="fontstyle-green">Enter the old and new password </p></td></tr>
<tr><td colspan="2" >&nbsp;&nbsp;</td></tr>
<tr>
<td>&nbsp;&nbsp;</td>
<td align="center">
<table class="plainTable">
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>User Id  </b></font></td>
		<td><html:text property="userId"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>Old Password    </b></font></td>
		<td><html:password property="oldPassWord"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green"><font color="#006300" ><b>New Password   </b></font></td>
		<td><html:password property="newPassWord"/></td>
	</tr>
	<tr><td colspan="2" >&nbsp;</td></tr>
	<tr><td colspan="2" align="center"><html:button property="save" onclick="fnSubmit()" styleClass="button">Change Password</html:button></td></tr>

</table>
</td>
</tr>
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
</table>
   </div>
</html:form>
</body>
</html>