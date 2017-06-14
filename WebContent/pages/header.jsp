<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 

<html>
    <head>
      <% 
  String user = "";
  if (session.getAttribute("logged-user") != null)
	  user = (String) session.getAttribute("logged-user");
%>
    </head>
    <body>
      <table  bgcolor="#C5E43B" width="100%">
      <tr><td width="20%"></td><td align="center" width="50%"><h1>DB POWER PACK</h1></td><td align="right" width="30%"><img height="60" width="60" src="images/headerImg.png"/></td></tr>
      <tr><td width="20%"></td><td align="center" width="50%">Version 1.0</td>
       <td align="right" class="fontstyle0" width="30%"><% if (user != null && user != "") { %>Logged user <b><%= user %> </b><% } %><a href="logOutAction.do" ><font size="2" color="#3EA99F"><b>Log out</b></font></a></td></tr>
      </table>
    </body>
</html>
