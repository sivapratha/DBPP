<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>        
        <% 
  String user = "";
  if (session.getAttribute("logged-in") != null)
	  user = (String) session.getAttribute("logged-in");
%>
    </head>
    <body >
      <% if (user != null && user != "" && user.equals("Admin")) { %>
<table border="0" background="images/MenuImgAdmin.jpg" height="290"
	width="247" class="plainTable">
	<tr>
		<td align="center" height="40"><a href="loadHome.do"><font
			color="#FFFFFF"><b>Home</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="viewDBConfig.do"><font
			color="#FFFFFF"><b>DB Configuration</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="compareDB.do"><font
			color="#FFFFFF"><b>Compare DB</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="validateObject.do"><font
			color="#FFFFFF"><b>Validate DB Objects</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="healthCheck.do"><font
			color="#FFFFFF"><b>Health Check up</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="userConfig.do"><font
			color="#FFFFFF"><b>User Configuration</b></font></a></td>
	</tr>

</table>
<% } else { %>
       
<table border="0" background="images/MenuImg.jpg" height="290"
	width="247" class="plainTable">
	<tr>
		<td align="center" height="40"><a href="loadHome.do"><font
			color="#FFFFFF"><b>Home</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="viewDBConfig.do"><font
			color="#FFFFFF"><b>DB Configuration</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="compareDB.do"><font
			color="#FFFFFF"><b>Compare DB</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="validateObject.do"><font
			color="#FFFFFF"><b>Validate DB Objects</b></font></a></td>
	</tr>
	<tr>
		<td align="center" height="40"><a href="healthCheck.do"><font
			color="#FFFFFF"><b>Health Check up</b></font></a></td>
	</tr>
</table>
      <% } %>  
     
    </body>
</html>
