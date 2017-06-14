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
	<table class="plainTable">
	<tr><td width="20">&nbsp;</td><td ></td></tr>
	<tr><td width="20">&nbsp;</td>
	<td>
	<table  class="gridtable">
	<tr><td class="yellow-td" ><font color="Red">Signifies missing code</font></td></tr>
	<tr><td class="blue-td" ><font color="blue">Signifies Changed code</font></td></tr>
	</table>
	</td><td >
	<html:button property="save" onclick="generateExcel()" styleClass="button">Excel</html:button>
	</td>
	<td >
	<html:button property="save" onclick="sendEmail()" styleClass="button">Send Email</html:button>
	</td>
	</tr>
	</table>
	<table width="1000px" class="plainTable">
	<tr>
	<td colspan="3" align="center" >
	<table width="1000px" class="plainTable">
	<tr><td align="center" class="fontstyle-green"><bean:write  name="compareDBForm" property="tableName"  /></td></tr>	
	</table>
	</td>
	</tr>
	<tr>
	<td  width="20">&nbsp;</td>
	<td colspan="2">
	<div style="height:500px; overflow:auto;">
	<table width="100%" class="gridtable">	
	<tr>	
	<logic:notEmpty name="compareDBForm" property="dbNameList">
	  <logic:iterate id="dbName" name="compareDBForm" property="dbNameList" type="java.lang.String">
		<th style="word-wrap:break-word"><bean:write name="dbName"  /></th>  
	 </logic:iterate>
	  </logic:notEmpty> 
	</tr>
	<logic:notEmpty name="compareDBForm" property="combinedLst">
	  <logic:iterate id="dbObjDetails" name="compareDBForm" property="combinedLst" type="com.hp.dbpowerpack.common.model.ViewCompareDiffModel">
	  <tr>
	  <logic:equal name="dbObjDetails" property="colorFlag" value="RED">
	 <td height="20" class="yellow-td" ><font color="red"> <bean:write  name="dbObjDetails" property="oldLine"  /><logic:empty name="dbObjDetails" property="oldLine" >&nbsp;</logic:empty></font></td>
	  <td height="20"  class="yellow-td"><font color="red"> <bean:write  name="dbObjDetails" property="newLine"  /><logic:empty name="dbObjDetails" property="newLine" >&nbsp;</logic:empty></font></td>
	 
	  </logic:equal>	
	  
	   <logic:equal name="dbObjDetails" property="colorFlag" value="BLUE">
	 <td height="20" class="yellow-td" ><font color="red"> <bean:write  name="dbObjDetails" property="oldLine"  /><logic:empty name="dbObjDetails" property="oldLine" >&nbsp;</logic:empty></font></td>
	  <td height="20" class="yellow-td"><font color="red"> <bean:write  name="dbObjDetails" property="newLine"  /><logic:empty name="dbObjDetails" property="newLine" >&nbsp;</logic:empty></font></td>
	 
	  </logic:equal>	
	  
	   <logic:equal name="dbObjDetails" property="colorFlag" value="GREEN">
	 <td height="20" class="blue-td" ><font color="blue"> <bean:write  name="dbObjDetails" property="oldLine"  /><logic:empty name="dbObjDetails" property="oldLine" >&nbsp;</logic:empty></font></td>
	  <td height="20" class="blue-td"><font color="blue"> <bean:write  name="dbObjDetails" property="newLine"  /><logic:empty name="dbObjDetails" property="newLine" >&nbsp;</logic:empty></font></td>
	 
	  </logic:equal>	
	  
	  
	  <logic:equal name="dbObjDetails" property="colorFlag" value="NONE">
	  <td height="20" class="none-td" ><font color="black"> <bean:write  name="dbObjDetails" property="oldLine"  /><logic:empty name="dbObjDetails" property="oldLine" >&nbsp;</logic:empty></font></td>
	  <td height="20" class="none-td"><font color="black"> <bean:write  name="dbObjDetails" property="newLine"  /><logic:empty name="dbObjDetails" property="newLine" >&nbsp;</logic:empty></font></td>
	
	  </logic:equal>	   
	  
	 
	  </tr>
	  </logic:iterate>
	  </logic:notEmpty> 
	   </table> 
	  </div>
	  </td></tr></table>
	
	
	</html:form>
    </body>
</html>
