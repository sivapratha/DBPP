<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<html>
<head>
 <SCRIPT language="javascript">
    function submitAction(action){
    	var dbName = document.getElementById('dbName');
    	var actionName = 'healthCheckResult';
    	if("checkTableSpace.do" == action){
    		actionName = 'viewTableSpace';
    	}
    	
    	var url = action+"?dbName="+dbName.value+"&actionName="+actionName;
    	if(action=="partitionedTable.do" || action=="blockContention.do"){
    		var result = window.showModalDialog(url, null, 
			"dialogWidth:400px;dialogHeight:650px;center:yes;scroll:yes;status:no;resizable:yes");
    	}else{
    		var result = window.showModalDialog(url, null, 
			"dialogWidth:1000px;dialogHeight:700px;center:yes;scroll:yes;status:no;resizable:yes");
    	}
		
		
    }
    

    </SCRIPT>
    
<title>Welcome to DBPP</title>
</head>
  
<body>
<html:form action="healthCheck.do" method="POST" enctype="multipart/form-data" >   
   <div>
   <p class="fontstyle-warning">&nbsp;&nbsp;You should have DBA permission to do health check up for the schema</p>
<table class="plainTable">
<tr><td>&nbsp;</td></tr>
<tr><td>&nbsp;&nbsp;</td><td  class="fontstyle-green" align="left" valign="top" width="300px">Select the schema &nbsp;&nbsp;
	<html:select property="dbName" styleId="dbName" name="healthCheckForm">
		<font class="fontstyle1">
		<html:optionsCollection property="dbNameList" name="healthCheckForm" label="dbName" value="dbName"/>	
		</font>
	</html:select>
	&nbsp;&nbsp;</td>
	<td width="250px" >
	<table>
	<tr><td><a href="#" onclick="submitAction('checkTableSpace.do')"><font color="black" class="fontstyle1">TableSpace</font></a></td></tr>
	<tr><td><a href="#" onclick="submitAction('partitionedTable.do')"><font color="black" class="fontstyle1">Partitioned Table</font></a></td></tr>
	<tr><td><a href="#" onclick="submitAction('blockContention.do')"><font color="black" class="fontstyle1">Block Contention</font></a></td></tr>
	<tr><td><a href="#" onclick="submitAction('availableJobs.do')"><font color="black" class="fontstyle1">Available Jobs</font></a></td></tr>
	<tr><td><a href="#" onclick="submitAction('jobsBroken.do')"><font color="black" class="fontstyle1">Jobs Broken</font></a></td></tr>
	<tr><td><a href="#" onclick="submitAction('jobsRunning.do')"><font color="black" class="fontstyle1">Jobs Running</font></a></td></tr>
	</table>
	</td>
	</tr>	

</table>
   </div>
   <br/>

   
</html:form>
</body>
</html>