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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    <SCRIPT language="javascript">
    
 function generateExcel(){
	 window.open('compTableExcelReport.do','excel','width=500,height=400,menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes,maximum=yes');
	 }
 
 function sendEmail(){

	 var result = window.showModalDialog('loadMailAction.do', null, 
		"dialogWidth:600px;dialogHeight:400px;center:yes;scroll:no;status:no");
		if(result != undefined && result.to != ""){
			document.compareDBForm.to.value = result.to;
			document.compareDBForm.cc.value = result.cc;
			document.compareDBForm.subject.value = result.subject;
			document.compareDBForm.bodyContent.value = result.bodyContent;
		} 


		document.compareDBForm.action = "sendMailAction.do";
		document.compareDBForm.submit();
	 }
 
 function goBack(){
	 document.compareDBForm.action = "loadCompareDB.do";
		document.compareDBForm.submit();
	 }
 
    </SCRIPT>
    </head>
    <body>
    <html:form action="compareDB.do" method="POST" enctype="multipart/form-data" >
    <html:hidden property="to" styleId="to"  name="compareDBForm"/>	
     <html:hidden property="cc" styleId="cc" name="compareDBForm"/>
     <html:hidden property="subject" styleId="subject" name="compareDBForm"/>
     <html:hidden property="bodyContent" styleId="bodyContent" name="compareDBForm"/>
<br/>
	<table class="plainTable">
	<tr><td>
	<table  class="gridtable">
	<tr><td class="yellow-td" ><font color="Red">Signifies object version discrepancy</font></td></tr>
	</table>
	</td><td rowspan="2">
	<html:button property="save" onclick="generateExcel()" styleClass="button">Excel</html:button>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="sendEmail()" styleClass="button">Send Email</html:button>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="goBack()" styleClass="button">Back</html:button>
	</td>
	</tr>
	</table>
	<table width="800px" border="2" cellpadding="1" class="plainTable">
	<tr>
	<td>
	<table class="gridtable">
	<tr>
	<th width="140px" > Object Type  </th>
	<th width="440px" >Object Name </th>
	<logic:notEmpty name="compareDBForm" property="dbNameList">
	  <logic:iterate id="dbName" name="compareDBForm" property="dbNameList" type="java.lang.String">
		<th width="60px" style="word-wrap:break-word"><bean:write name="dbName"  /></th>  
	 </logic:iterate>
	  </logic:notEmpty> 
	</tr>
	</table>
	<tr><td colspan="5">
	<div style="height:300px; overflow:auto;">
	<table width="800px" class="gridtable">
	
	<logic:notEmpty name="compareDBForm" property="dbObjViewList">
	  <logic:iterate id="dbObjDetails" name="compareDBForm" property="dbObjViewList" type="com.hp.dbpowerpack.Model.DBObjViewModel">
	  <logic:equal name="dbObjDetails" property="colorFlag"  value="Red">
	  <tr>	  
	  
	  <td width="100px" class="yellow-td"><font color="Red"><bean:write  name="dbObjDetails" property="objType"  /><logic:empty name="dbObjDetails" property="objType" >&nbsp;</logic:empty></font></td>
	  <td width="300px" class="yellow-td"><font color="Red"><bean:write  name="dbObjDetails" property="objName"  /><logic:empty name="dbObjDetails" property="objName" >&nbsp;</logic:empty></font></td>
	  
	  <logic:notEmpty name="dbObjDetails" property="versionMap">
		
		   <logic:iterate id="dbName" name="compareDBForm" property="dbNameList" type="java.lang.String">
		    <td width="50px" class="yellow-td"><font color="Red"><%= dbObjDetails.getVersionMap().get(dbName)%>&nbsp;</font></td>  
	 		</logic:iterate>
	   </logic:notEmpty>
	  </tr>
	  </logic:equal>
	  <logic:notEqual name="dbObjDetails" property="colorFlag"  value="Red">
	  <tr >	  
	  
	  <td width="100px"><bean:write  name="dbObjDetails" property="objType"  /><logic:empty name="dbObjDetails" property="objType" >&nbsp;</logic:empty></td>
	  <td width="300px"><bean:write  name="dbObjDetails" property="objName"  /><logic:empty name="dbObjDetails" property="objName" >&nbsp;</logic:empty></td>
	  
	  <logic:notEmpty name="dbObjDetails" property="versionMap">
		
		   <logic:iterate id="dbName" name="compareDBForm" property="dbNameList" type="java.lang.String">
		    <td width="50px" ><%= dbObjDetails.getVersionMap().get(dbName)%>&nbsp;</td>  
	 		</logic:iterate>
	   </logic:notEmpty>
	  </tr>
	  </logic:notEqual>
	  </logic:iterate>
	  </logic:notEmpty> 
	   </table> 
	  </div>
	  </td></tr></table>
	
	
	</html:form>
    </body>
</html>
