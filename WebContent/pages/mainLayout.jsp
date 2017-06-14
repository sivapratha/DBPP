<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title><tiles:getAsString name="title" ignore="true" /></title>
    </head>
    <body>
        <table height="100%" width="100%" >
            <tr>
                <td height="20%" >
                    <tiles:insert attribute="header" ignore="true" />
                </td>
            </tr>
            <tr>               
                <td >
                    <tiles:insert attribute="body" />
                </td>
            </tr>
            <tr>
                <td height="20%">
                    <tiles:insert attribute="footer" />
                </td>
            </tr>
        </table>
    </body>
</html>
