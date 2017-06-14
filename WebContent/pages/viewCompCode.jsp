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
 <SCRIPT language="javascript">
 function fnViewCode(tableName1,tableName2,ObjectType){
 	var url = "viewCodeComparison.do?tableName1="+tableName1+"&tableName2="+tableName2+"&ObjectType="+ObjectType;
		var result = window.showModalDialog(url, null, 
								"dialogWidth:1350px;dialogHeight:650px;center:yes;scroll:yes;status:no");
		
 }
    
 function generateExcel(){
	 window.open('compTableExcelReport.do','excel','width=500,height=400,menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes,maximum=yes');
	 }
 
 function goBack(){
	 document.compareDBForm.action = "loadCompareDB.do";
		document.compareDBForm.submit();
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

    </SCRIPT>
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to DB Power Pack</title>
    </head>
    <body>
    <html:form action="compareDB.do" method="POST" enctype="multipart/form-data" >
     <html:hidden property="selectedDb" styleId="selectedDb" name="compareDBForm"/>
     <html:hidden property="to" styleId="to"  name="compareDBForm"/>	
     <html:hidden property="cc" styleId="cc" name="compareDBForm"/>
     <html:hidden property="subject" styleId="subject" name="compareDBForm"/>
     <html:hidden property="bodyContent" styleId="bodyContent" name="compareDBForm"/>
<br/>
	<table class="plainTable">
	<tr><td>
	<table  class="gridtable">
	<tr><td class="yellow-td" ><font color="Red">Signifies missing objects</font></td></tr>
	<tr><td class="blue-td" ><font color="blue">Signifies Changed objects</font></td></tr>
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
	
	
	<table width="800px" border="0"  class="plainTable">
	<tr>
	<td>
	<table width="800px" class="gridtable" >
	<tr>
	<th width="100px"  style="word-wrap:break-word">Object Type</th>  
	<logic:notEmpty name="compareDBForm" property="dbNameList">
	  <logic:iterate id="dbName" name="compareDBForm" property="dbNameList" type="java.lang.String">
		<th width="300px" style="word-wrap:break-word"><bean:write name="dbName"  /></th>  
	 </logic:iterate>
	  </logic:notEmpty> 
	</tr>
	</table>
	<tr><td>
	<div style="height:300px; overflow:auto;">
	<table width="800px" class="gridtable">
	
	<logic:notEmpty name="compareDBForm" property="dbObjTblNameList">
	  <logic:iterate id="dbObjDetails" name="compareDBForm" property="dbObjTblNameList" type="com.hp.dbpowerpack.Model.DBTableNameModel">
		  	<tr>
		  <logic:equal name="dbObjDetails" property="colorFlag" value="Red">
		  <td width="100px" class="yellow-td" ><font color="Red"> <bean:write  name="dbObjDetails" property="objectType"  /></font><logic:empty name="dbObjDetails" property="objectType" >&nbsp;</logic:empty></td>
		 <td width="300px" class="yellow-td" ><a href="#" onclick="fnViewCode('${dbObjDetails.dbTableName1}','${dbObjDetails.dbTableName2}','${dbObjDetails.objectType}')">  <font color="Red"> <bean:write  name="dbObjDetails" property="dbTableName1"  /></font></a><logic:empty name="dbObjDetails" property="dbTableName1" >&nbsp;</logic:empty></td>
		  <td width="300px" class="yellow-td" ><a href="#" onclick="fnViewCode('${dbObjDetails.dbTableName1}','${dbObjDetails.dbTableName2}','${dbObjDetails.objectType}')"> <font color="Red"><bean:write  name="dbObjDetails" property="dbTableName2"  /></font></a><logic:empty name="dbObjDetails" property="dbTableName2" >&nbsp;</logic:empty></td>
		 
		  </logic:equal>
		  <logic:equal name="dbObjDetails" property="colorFlag" value="Green">
		  <td width="100px" class="blue-td" ><font color="blue"> <bean:write  name="dbObjDetails" property="objectType"  /></font><logic:empty name="dbObjDetails" property="objectType" >&nbsp;</logic:empty></td>
		 <td width="300px" class="blue-td" ><a href="#" onclick="fnViewCode('${dbObjDetails.dbTableName1}','${dbObjDetails.dbTableName2}','${dbObjDetails.objectType}')">  <font color="blue"> <bean:write  name="dbObjDetails" property="dbTableName1"  /></font></a><logic:empty name="dbObjDetails" property="dbTableName1" >&nbsp;</logic:empty></td>
		  <td width="300px" class="blue-td" ><a href="#" onclick="fnViewCode('${dbObjDetails.dbTableName1}','${dbObjDetails.dbTableName2}','${dbObjDetails.objectType}')"> <font color="blue"><bean:write  name="dbObjDetails" property="dbTableName2"  /></font></a><logic:empty name="dbObjDetails" property="dbTableName2" >&nbsp;</logic:empty></td>
		 
		  </logic:equal>
		  	
		   <logic:notEqual name="dbObjDetails" property="colorFlag" value="Red">
		    <logic:notEqual name="dbObjDetails" property="colorFlag" value="Green">
		   <td width="100px"><font color="black"><bean:write  name="dbObjDetails" property="objectType"  /></font><logic:empty name="dbObjDetails" property="objectType" >&nbsp;</logic:empty></td>
		   <td width="300px"><a href="#" onclick="fnViewCode('${dbObjDetails.dbTableName1}','${dbObjDetails.dbTableName2}','${dbObjDetails.objectType}')"> <font color="black"><bean:write  name="dbObjDetails" property="dbTableName1"  /></font></a><logic:empty name="dbObjDetails" property="dbTableName1" >&nbsp;</logic:empty></td>
		  <td width="300px"><a href="#" onclick="fnViewCode('${dbObjDetails.dbTableName1}','${dbObjDetails.dbTableName2}','${dbObjDetails.objectType}')"> <font color="black"><bean:write  name="dbObjDetails" property="dbTableName2"  /></font></a><logic:empty name="dbObjDetails" property="dbTableName2" >&nbsp;</logic:empty></td>
		  </logic:notEqual>	 
		  </logic:notEqual>  
		  </tr>
	  </logic:iterate>
	  </logic:notEmpty> 
	   </table> 
	  </div>
	  </td></tr></table>
	</html:form>
    </body>
</html>
