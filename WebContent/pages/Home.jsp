<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to DB Power Pack</title>
    </head>
    <body>
    <html:form action="loadHome.do" method="POST" enctype="multipart/form-data" >
        <p class="fontstyle2"> Welcome to DB Power Pack.</p>
<br/>
	<p class="fontstyle1"> Developers , Architects , DBA uses different environments for coding , testing and deploying.
Often these environments get out of sync as engineers modify the design to either optimize the database or add new features.
DB Power pack is a tool for any project development team, which manages multiple copies of the same database. 
This tool provides a User Interface to identify the versions in different Database. Comparisons of multiple database objects 
are easily configurable and results are clearly presented so that you can easily compare and immediately see the conflicts in your databases. 
It helps to identify the difference and have them addressed quickly.</p>
	<div>
	<table class="plainTable">
	<tr>
	<td> Below are the databases supported in the Version 1 of this tool</td>
	</tr>
	<tr><td>Oracle 11g</td></tr>
	</table>
	</div>
	</html:form>
    </body>
</html>
