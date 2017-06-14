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
 <SCRIPT language="javascript">
    function submitAction(action){
    	var dbName = document.getElementById('dbName');
    	var actionName = action;
    	var url = action+"?dbName="+dbName.value+"&actionName="+actionName;
    	if(actionName=='validateObjConfig.do'){
    		var result = window.showModalDialog(url, null, 
			"dialogWidth:400px;dialogHeight:400px;center:yes;scroll:yes;status:no;resizable:yes");
    	}else{
    		var result = window.showModalDialog(url, null, 
			"dialogWidth:600px;dialogHeight:600px;center:yes;scroll:yes;status:no;resizable:yes");
    	}
		
		
    }
    

    </SCRIPT>
    
<title>Welcome to DBPP</title>
</head>
  
<body >
<html:form action="validateObject.do" method="POST" enctype="multipart/form-data" > 
<table class="plainTable">
<tr><td>&nbsp;</td></tr>
<tr><td>&nbsp;&nbsp;</td><td class="fontstyle-green" align="left" valign="top" width="300px">Select the schema &nbsp;&nbsp;
	<html:select property="dbName" styleId="dbName" name="validateObjectForm">
	<font class="fontstyle1">
		<html:optionsCollection property="dbNameList" name="validateObjectForm" label="dbName" value="dbName"/>	
	</font>
	</html:select>
	&nbsp;&nbsp;</td>
	<td width="250px">
		<table>
			<tr><td><a href="#" onclick="submitAction('invalidObjAction.do')"><font color="black" class="fontstyle1">Invalid Object List</font></a></tr>
			<tr><td><a href="#" onclick="submitAction('poorReadability.do')"><font color="black" class="fontstyle1">Poor Readability</font></a></tr>
			<tr><td><a href="#" onclick="submitAction('rollbackCheck.do')"><font color="black" class="fontstyle1">Rollback Check</font></a></tr>
			<tr><td><a href="#" onclick="submitAction('poorVersionManagement.do')"><font color="black" class="fontstyle1">Non versioned objects</font></a></tr>
			<tr><td><a href="#" onclick="submitAction('validateObjConfig.do')"><font color="black" class="fontstyle1">Configure Naming Standard</font></a></td></tr>
			<tr><td><a href="#" onclick="submitAction('loadValidateNamingStandard.do')"><font color="black" class="fontstyle1">Naming Standard Violations</font></a></td></tr>		
		</table>
	</td>
	
	</tr>	

</table>
   </div>

		
   
</html:form>
</body>
</html>