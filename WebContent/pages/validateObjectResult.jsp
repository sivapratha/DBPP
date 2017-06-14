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
<SCRIPT language="javascript">
 
    
 function generateExcel(){
	 window.open('validateObjExcelReport.do','excel','width=500,height=400,menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes,maximum=yes');
	 }
 
 function sendEmail(){

	 var result = window.showModalDialog('loadMailAction.do', null, 
		"dialogWidth:600px;dialogHeight:400px;center:yes;scroll:no;status:no");
		if(result != undefined && result.to != ""){
			document.validateObjectForm.to.value = result.to;
			document.validateObjectForm.cc.value = result.cc;
			document.validateObjectForm.subject.value = result.subject;
			document.validateObjectForm.bodyContent.value = result.bodyContent;
		} 


		document.validateObjectForm.action = "validateObjsendMailAction.do";
		document.validateObjectForm.submit();
	 }

    </SCRIPT>
    
</head>
  
<body>
<html:form action="invalidObjAction.do" method="POST" enctype="multipart/form-data" >  
     <html:hidden property="to" styleId="to"  name="validateObjectForm"/>	
     <html:hidden property="cc" styleId="cc" name="validateObjectForm"/>
     <html:hidden property="subject" styleId="subject" name="validateObjectForm"/>
     <html:hidden property="bodyContent" styleId="bodyContent" name="validateObjectForm"/> 
   <div>
   <table class="plainTable">
   <tr><td>&nbsp;&nbsp;</td></tr>
   <tr>
   <td width="20px"></td><td rowspan="2">
	<html:button property="save" onclick="generateExcel()"  styleClass="button">Excel</html:button>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="sendEmail()"  styleClass="button">Send Email</html:button>
	</td>
   </tr></table>
   <br/>
<table><tr><td width="20px"></td>
   <td>
   <logic:equal name="validateObjectForm" property="requestType" value="InvalidObject">

	<div style="height:500px; overflow:auto;">
<table  class="gridtable">
	
	<tr>
	<th>Object Type</th>
	<th>Object Name</th>
	</tr>
	
	<logic:notEmpty name="validateObjectForm" property="invalidObjList">
		
	<logic:iterate id="dbObjDetails" name="validateObjectForm" property="invalidObjList" type="com.hp.dbpowerpack.Model.DBObjViewModel">
	<tr><td height="20" ><bean:write  name="dbObjDetails" property="objType"  /><logic:empty name="dbObjDetails" property="objType" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write  name="dbObjDetails" property="objName"  /><logic:empty name="dbObjDetails" property="objName" >&nbsp;</logic:empty></td>
	 </tr>
	 		</logic:iterate>
	   </logic:notEmpty>	
</table></div>
</logic:equal>
 
<logic:equal name="validateObjectForm" property="requestType" value="PoorVersionMngmnt">

	<div style="height:500px; overflow:auto;">
<table  class="gridtable">
	
	<tr>
	<th>Object Type</th>
	<th>Object Name</th>
	<th>Object Status</th>
	</tr>
	
	<logic:notEmpty name="validateObjectForm" property="poorVersionMngmntLst">
		
	<logic:iterate id="dbObjDetails" name="validateObjectForm" property="poorVersionMngmntLst" type="com.hp.dbpowerpack.Model.DBObjViewModel">
	<tr><td height="20" ><bean:write  name="dbObjDetails" property="objType"  /><logic:empty name="dbObjDetails" property="objType" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write  name="dbObjDetails" property="objName"  /><logic:empty name="dbObjDetails" property="objName" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write  name="dbObjDetails" property="status"  /><logic:empty name="dbObjDetails" property="status" >&nbsp;</logic:empty></td>
	 </tr>
	 		</logic:iterate>
	   </logic:notEmpty>	
</table></div>
</logic:equal>

<logic:equal name="validateObjectForm" property="requestType" value="PoorReadability">

	<div style="height:500px; overflow:auto;">
<table  class="gridtable">
	
	<tr>
	<th>Object Type</th>
	<th>Object Name</th>	
	</tr>
	
	<logic:notEmpty name="validateObjectForm" property="poorReadabilityList">
		
	<logic:iterate id="dbObjDetails" name="validateObjectForm" property="poorReadabilityList" type="com.hp.dbpowerpack.Model.DBObjViewModel">
	<tr><td height="20" ><bean:write  name="dbObjDetails" property="objType"  /><logic:empty name="dbObjDetails" property="objType" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write  name="dbObjDetails" property="objName"  /><logic:empty name="dbObjDetails" property="objName" >&nbsp;</logic:empty></td>
	 </tr>
	 		</logic:iterate>
	   </logic:notEmpty>	
</table></div>
</logic:equal>

<logic:equal name="validateObjectForm" property="requestType" value="RollbackCheck">

	<div style="height:500px; overflow:auto;">
<table  class="gridtable">
	
	<tr>
	<th>Object Type</th>
	<th>Object Name</th>
	</tr>
	
	<logic:notEmpty name="validateObjectForm" property="rollBackCheckList">
		
	<logic:iterate id="dbObjDetails" name="validateObjectForm" property="rollBackCheckList" type="com.hp.dbpowerpack.Model.DBObjViewModel">
	<tr><td height="20" ><bean:write  name="dbObjDetails" property="objType"  /><logic:empty name="dbObjDetails" property="objType" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write  name="dbObjDetails" property="objName"  /><logic:empty name="dbObjDetails" property="objName" >&nbsp;</logic:empty></td>
	 </tr>
	 		</logic:iterate>
	   </logic:notEmpty>	
</table></div>
</logic:equal>

   </tr>
   </table>
   </div>
 
 
</html:form>
</body>
</html>