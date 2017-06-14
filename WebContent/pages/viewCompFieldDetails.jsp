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
 function generateExcel(){
	 window.open('compTableExcelReport.do','excel','width=500,height=400,menubar=yes,status=yes,location=yes,toolbar=yes,scrollbars=yes,maximum=yes');
	 }
 
 function sendEmail(){

	 var result = window.showModalDialog('loadMailAction.do', null, 
		"dialogWidth:600px;dialogHeight:400px;center:yes;scroll:no;status:no");
		if(result != undefined && result.to != ""){
			var to = result.to;
			var cc = result.cc;
			var subject = result.subject;
			var bodyContent = result.bodyContent;


		  var xmlHttpReq = false;
			var self = this;
			// Mozilla/Safari
			if (window.XMLHttpRequest) {
				self.xmlHttpReq = new XMLHttpRequest();
			}
			// IE
			else if (window.ActiveXObject) {
				self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			}
			var url = "sendMailAction.do?";
			var parameters="to=" + to+"&cc="+cc+"&subject="+subject+"&bodyContent="+bodyContent;

			self.xmlHttpReq.open('POST', url, false);
			self.xmlHttpReq.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded');
			self.xmlHttpReq.onreadystatechange = function() {
				if (self.xmlHttpReq.readyState == 4) {
					retValue = self.xmlHttpReq.responseText;
					
				}
			}
			self.xmlHttpReq.send(parameters);

		} 
	 }

 function goBack(){
	 document.compareDBForm.action = "loadCompareDB.do";
		document.compareDBForm.submit();
	 }
 
    </SCRIPT>
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to DB Power Pack</title>
    </head>
    <body>
    <html:form action="viewDBConfig.do" method="POST" enctype="multipart/form-data" >
      <html:hidden property="to" styleId="to"  name="compareDBForm"/>	
     <html:hidden property="cc" styleId="cc" name="compareDBForm"/>
     <html:hidden property="subject" styleId="subject" name="compareDBForm"/>
     <html:hidden property="bodyContent" styleId="bodyContent" name="compareDBForm"/>
<br/>

	<table class="plainTable">
	<tr>
	<td>
	<table  class="gridtable">
	<tr><td class="yellow-td" ><font color="Red">Signifies missing objects</font></td></tr>
	<tr><td class="blue-td" ><font color="blue">Signifies Changed objects</font></td></tr>
	</table>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="generateExcel()" styleClass="button">Excel</html:button>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="sendEmail()" styleClass="button">Send Email</html:button>
	</td>
	</tr>
	</table>
	<table width="800px" class="plainTable">
	<tr>
	<td  width="10%"></td>
	<td>
	<table width="800px" class="plainTable">
	<tr><td colspan="2" align="center" class="fontstyle-green"><bean:write  name="compareDBForm" property="tableName"  /></td></tr>
	
	</table>
	</td>
	</tr>
	<tr>
	<td  width="10%"></td>
	<td colspan="2">
	<div style="height:500px; overflow:auto;">
	<table width="100%" class="gridtable">	
	<tr>	
	<logic:notEmpty name="compareDBForm" property="dbNameList">
	  <logic:iterate id="dbName" name="compareDBForm" property="dbNameList" type="java.lang.String">
		<td colspan="3" style="word-wrap:break-word" align="center" class="fontstyle-green"><bean:write name="dbName"  /></td>  
	 </logic:iterate>
	  </logic:notEmpty> 
	</tr>
	<tr>
	<th>Field Name</th>
	<th>Data Type</th>
	<th>Data Length</th>
	<th>Field Name</th>
	<th>Data Type</th>
	<th>Data Length</th>
	</tr>
	<logic:notEmpty name="compareDBForm" property="dbFieldsList">
	  <logic:iterate id="dbObjDetails" name="compareDBForm" property="dbFieldsList" type="com.hp.dbpowerpack.Model.DBTableNameModel">
	  <tr>
	  <logic:equal name="dbObjDetails" property="colorFlag" value="Green">
	 <td class="blue-td" ><font color="blue"> <bean:write  name="dbObjDetails" property="dbFieldName1"  /><logic:empty name="dbObjDetails" property="dbFieldName1" >&nbsp;</logic:empty></font></td>
	  <td class="blue-td"><font color="blue"><bean:write  name="dbObjDetails" property="dbDataType1"  /><logic:empty name="dbObjDetails" property="dbDataType1" >&nbsp;</logic:empty></font></td>
	  <td class="blue-td"><font color="blue"><bean:write format=""    name="dbObjDetails" property="dbDataLength1"  /><logic:empty name="dbObjDetails" property="dbDataLength1" >&nbsp;</logic:empty></font></td>
	  <td class="blue-td"><font color="blue"> <bean:write  name="dbObjDetails" property="dbFieldName2"  /><logic:empty name="dbObjDetails" property="dbFieldName2" >&nbsp;</logic:empty></font></td>
	  <td class="blue-td"><font color="blue"><bean:write  name="dbObjDetails" property="dbDataType2"  /><logic:empty name="dbObjDetails" property="dbDataType2" >&nbsp;</logic:empty></font></td>
	  <td class="blue-td"><font color="blue"><bean:write format=""    name="dbObjDetails" property="dbDataLength2"  /><logic:empty name="dbObjDetails" property="dbDataLength2" >&nbsp;</logic:empty></font></td>
	
	  </logic:equal>	
	  
	    <logic:equal name="dbObjDetails" property="colorFlag" value="Red">
	 <td class="yellow-td" ><font color="Red"> <bean:write  name="dbObjDetails" property="dbFieldName1"  /><logic:empty name="dbObjDetails" property="dbFieldName1" >&nbsp;</logic:empty></font></td>
	  <td class="yellow-td"><font color="Red"><bean:write  name="dbObjDetails" property="dbDataType1"  /><logic:empty name="dbObjDetails" property="dbDataType1" >&nbsp;</logic:empty></font></td>
	  <td class="yellow-td"><font color="Red"><bean:write format=""    name="dbObjDetails" property="dbDataLength1"  /><logic:empty name="dbObjDetails" property="dbDataLength1" >&nbsp;</logic:empty></font></td>
	  <td class="yellow-td"><font color="Red"> <bean:write  name="dbObjDetails" property="dbFieldName2"  /><logic:empty name="dbObjDetails" property="dbFieldName2" >&nbsp;</logic:empty></font></td>
	  <td class="yellow-td"><font color="Red"><bean:write  name="dbObjDetails" property="dbDataType2"  /><logic:empty name="dbObjDetails" property="dbDataType2" >&nbsp;</logic:empty></font></td>
	  <td class="yellow-td"><font color="Red"><bean:write format=""    name="dbObjDetails" property="dbDataLength2"  /><logic:empty name="dbObjDetails" property="dbDataLength2" >&nbsp;</logic:empty></font></td>
	
	  </logic:equal>	
	  
	  <logic:notEqual name="dbObjDetails" property="colorFlag" value="Red">
	  <logic:notEqual name="dbObjDetails" property="colorFlag" value="Green">
	  <td><bean:write  name="dbObjDetails" property="dbFieldName1"  /><logic:empty name="dbObjDetails" property="dbFieldName1" >&nbsp;</logic:empty></td>
	  <td><bean:write  name="dbObjDetails" property="dbDataType1"  /><logic:empty name="dbObjDetails" property="dbDataType1" >&nbsp;</logic:empty></td>
	  <td><bean:write format="#"  name="dbObjDetails" property="dbDataLength1"  /><logic:empty name="dbObjDetails" property="dbDataLength1" >&nbsp;</logic:empty></td>
	  <td><bean:write  name="dbObjDetails" property="dbFieldName2"  /><logic:empty name="dbObjDetails" property="dbFieldName2" >&nbsp;</logic:empty></td>
	  <td><bean:write  name="dbObjDetails" property="dbDataType2"  /><logic:empty name="dbObjDetails" property="dbDataType2" >&nbsp;</logic:empty></td>
	  <td><bean:write format="#"  name="dbObjDetails" property="dbDataLength2"  /><logic:empty name="dbObjDetails" property="dbDataLength2" >&nbsp;</logic:empty></td>
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
