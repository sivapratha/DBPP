<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="static/DBPP.css" rel="stylesheet" type="text/css">
            <title><tiles:getAsString name="title" ignore="true" /></title>
    </head>
    <body>
        <table height="100%" width="100%" >
            <tr>
                <td height="20%" colspan="2">
                    <tiles:insert attribute="header" ignore="true" />
                </td>
            </tr>
            <tr>
            <td >
	            <table>
	            <tr>
	             	<td width="260" height="400" >
	                    <tiles:insert attribute="menu" />
	                </td>
	                   <td>
	                    <tiles:insert attribute="body" />
	                </td>
	            </tr>
	            </table>
            </td>
               
            </tr>
            <tr>
                <td height="20%"  colspan="2" >
                    <tiles:insert attribute="footer" />
                </td>
            </tr>
        </table>
    </body>
</html>
