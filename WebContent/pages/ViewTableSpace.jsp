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
	 window.open('healthChkExcelReport.do','excel','width=500,height=400,menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes,maximum=yes');
	 }
 
 function sendEmail(){

	 var result = window.showModalDialog('loadMailAction.do', null, 
		"dialogWidth:600px;dialogHeight:400px;center:yes;scroll:no;status:no");
		if(result != undefined && result.to != ""){
			document.healthCheckForm.to.value = result.to;
			document.healthCheckForm.cc.value = result.cc;
			document.healthCheckForm.subject.value = result.subject;
			document.healthCheckForm.bodyContent.value = result.bodyContent;
		} 


		document.healthCheckForm.action = "healthChksendMailAction.do";
		document.healthCheckForm.submit();
	 }

    </SCRIPT>
</head>
  
<body>
<html:form action="checkTableSpace.do" method="POST" enctype="multipart/form-data" >  

     <html:hidden property="to" styleId="to"  name="healthCheckForm"/>	
     <html:hidden property="cc" styleId="cc" name="healthCheckForm"/>
     <html:hidden property="subject" styleId="subject" name="healthCheckForm"/>
     <html:hidden property="bodyContent" styleId="bodyContent" name="healthCheckForm"/>  
   <div>
   
   <table class="plainTable"><tr><td>&nbsp;&nbsp;</td></tr>
   <tr><td width="20px"></td>
   <td rowspan="2">
	<html:button property="save" onclick="generateExcel()" styleClass="button">Excel</html:button>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="sendEmail()" styleClass="button">Send Email</html:button>
	</td>
   </tr></table>
   <br/>

<table><tr><td width="20px"></td><td>
	<div style="height:500px; overflow:auto;">
<table class="gridtable" >
	
	<tr>
	<th>Table Space Name</th>
	<th>Size (MB)</th>
	<th>Free Size (MB)</th>
	<th>Free Size (%)</th>
	<th>Used Size (%)</th>
	</tr>
	
	<logic:notEmpty name="healthCheckForm" property="tableSpaceDtlList">
		
	<logic:iterate id="dbObjDetails" name="healthCheckForm" property="tableSpaceDtlList" type="com.hp.dbpowerpack.Model.TableSpaceModel">
	<tr><td height="20" ><bean:write  name="dbObjDetails" property="tableSpaceName"  /><logic:empty name="dbObjDetails" property="tableSpaceName" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write  name="dbObjDetails" property="sizeMb"  /><logic:empty name="dbObjDetails" property="sizeMb" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write format="" name="dbObjDetails" property="freeMb"  /><logic:empty name="dbObjDetails" property="freeMb" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write format="#" name="dbObjDetails" property="freePerc"  /><logic:empty name="dbObjDetails" property="freePerc" >&nbsp;</logic:empty></td>
	  	<td height="20" ><bean:write format="#" name="dbObjDetails" property="usedPerc"  /><logic:empty name="dbObjDetails" property="usedPerc" >&nbsp;</logic:empty></td>
	  	
	 </tr>
	 		</logic:iterate>
	   </logic:notEmpty>	
</table>
</div>
</td></tr></table>
   </div>
 
 
</html:form>
</body>
</html>