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
<title>DBPP Mail</title>
<script language="JavaScript"  type="text/javascript">
 function fnSubmit(){

	 var procedureStartsWith = document.validateObjectForm.procedureStartsWith.value;
	 var functionStartsWith = document.validateObjectForm.functionStartsWith.value;
	 var packageStartsWith = document.validateObjectForm.packageStartsWith.value;
	 var tableStartsWith = document.validateObjectForm.tableStartsWith.value;
	 var viewStartsWith = document.validateObjectForm.viewStartsWith.value;
	 var sequenceStartsWith = document.validateObjectForm.sequenceStartsWith.value;
	 var triggerStartsWith = document.validateObjectForm.triggerStartsWith.value;
	 var typeStartsWith = document.validateObjectForm.typeStartsWith.value;
	 var mviewStartsWith = document.validateObjectForm.mviewStartsWith.value;
	 var dbLinkStartsWith = document.validateObjectForm.dbLinkStartsWith.value;
	 var isExist = document.validateObjectForm.isExist.value;
	 
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
		var url = "saveValidateObjConfig.do?";
		var parameters="procedureStartsWith=" + procedureStartsWith+"&functionStartsWith="+functionStartsWith
						+"&packageStartsWith="+packageStartsWith+"&tableStartsWith=" + tableStartsWith
						+"&viewStartsWith="+viewStartsWith+"&sequenceStartsWith=" + sequenceStartsWith
						+"&triggerStartsWith="+triggerStartsWith+"&typeStartsWith=" + typeStartsWith
						+"&mviewStartsWith="+mviewStartsWith+"&dbLinkStartsWith=" + dbLinkStartsWith
						+"&isExist="+isExist;

		self.xmlHttpReq.open('POST', url, false);
		self.xmlHttpReq.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		self.xmlHttpReq.onreadystatechange = function() {
			if (self.xmlHttpReq.readyState == 4) {
				retValue = self.xmlHttpReq.responseText;
				alert("Updated Successfully");
				window.close();
				
			}
		}
		self.xmlHttpReq.send(parameters);
		
	}
 
 </script>
</head>
  
<body background="images/mailBg.jpg" >
<html:form action="validateObjConfig.do" method="POST" enctype="multipart/form-data" >   
<html:hidden property="isExist" styleId="isExist" name="validateObjectForm"/>
 
<table class="plainTable">
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
<tr><td>&nbsp;&nbsp;</td><td> <p class="fontstyle-green">Configure the prefix for the below objects </p></td></tr>
<tr><td colspan="2" >&nbsp;&nbsp;</td></tr>
<tr>
<td>&nbsp;&nbsp;</td>
<td align="center">

<table class="plainTable">
	<tr>
		<td class="fontstyle-green">Procedure  </td>
		<td><html:text property="procedureStartsWith" styleId="procedureStartsWith"  name="validateObjectForm"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">Function  </td>
		<td><html:text property="functionStartsWith" styleId="functionStartsWith"  name="validateObjectForm"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">Package  </td>
		<td><html:text property="packageStartsWith" styleId="packageStartsWith" name="validateObjectForm"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">Table  </td>
		<td><html:text property="tableStartsWith" styleId="tableStartsWith" name="validateObjectForm"/></td>
	</tr>
		<tr>
		<td class="fontstyle-green">View  </td>
		<td><html:text property="viewStartsWith" styleId="viewStartsWith"  name="validateObjectForm"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">Sequence  </td>
		<td><html:text property="sequenceStartsWith" styleId="sequenceStartsWith"  name="validateObjectForm"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">Trigger  </td>
		<td><html:text property="triggerStartsWith" styleId="triggerStartsWith" name="validateObjectForm"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">Type  </td>
		<td><html:text property="typeStartsWith" styleId="typeStartsWith" name="validateObjectForm"/></td>
	</tr>
		<tr>
		<td class="fontstyle-green">Materialized View  </td>
		<td><html:text property="mviewStartsWith" styleId="mviewStartsWith" name="validateObjectForm"/></td>
	</tr>
	<tr>
		<td class="fontstyle-green">DB Link  </td>
		<td><html:text property="dbLinkStartsWith" styleId="dbLinkStartsWith" name="validateObjectForm"/></td>
	</tr>
	
	<tr><td colspan="2" align="center"><html:button property="save" onclick="fnSubmit()"  styleClass="button">Save</html:button></td>
	</tr>

</table>
</td>
</tr>
<tr><td colspan="2">&nbsp;&nbsp;</td></tr>
</table>
</html:form>
</body>
</html>