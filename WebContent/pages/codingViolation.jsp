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

 function validateCodingViolation(){
	 
	 var div = document.getElementById('violationStandardTable');
		div.style.display="block";
 
 var selectedObjects = "";
	var checkbox = document.getElementsByName('isSelected');
	var objectCnt = 0;
	var max = checkbox.length;
	  for (var i=0; i<max; i++) {
		  if(checkbox[i].checked == true){
			  if(selectedObjects==""){
				  selectedObjects = checkbox[i].value;
			  }else {
				  selectedObjects = selectedObjects +"','"+ checkbox[i].value;
			  }
			  objectCnt++;
		  }
			  
	  }
	  
	  
	  var dbName = document.getElementById('dbName').value;
	  var length = document.getElementById('length').value;
	  
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
		var url = "poorReadabilityResults.do?";
		var parameters="slctdDbObj=" + selectedObjects+"&dbName="+dbName+"&length="+length;

		self.xmlHttpReq.open('POST', url, false);
		self.xmlHttpReq.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		self.xmlHttpReq.onreadystatechange = function() {
			if (self.xmlHttpReq.readyState == 4) {
				retValue = self.xmlHttpReq.responseText;
				var div1 = document.getElementById('violationStandardTable');
				div1.innerHTML = retValue;
				div1.style.display="block";
				
			}
		}
		self.xmlHttpReq.send(parameters);
	  
 }
 
 function checkAll(chkall) {
		var selectAllList = document.getElementsByName('isSelected');
		if (selectAllList != null) {
	for ( var i = 0; i < selectAllList.length; i++) {
				if(!selectAllList[i].disabled){
					selectAllList[i].checked = chkall.checked;	
				}
				
			}
		}
	}
//Function to uncheck the select all checkbox if any 
	//checkbox is left unchecked and to check if all check boxes are checked.
	function checkSelectAll(chkbx, selectAllName) {
		var theForm = chkbx.form;
		var elmntName = chkbx.name;
		var length = theForm.elements[chkbx.name].length;
		var selectAllChkBxName = theForm.elements[selectAllName];

		if (!chkbx.checked) {
			if (!chkbx.disabled) {
				selectAllChkBxName.checked = false;
				return false;
			}
		} else {
			if (length > 1) {
				for (i = 0; i < length; i++) {
					if (!theForm.elements[elmntName][i].checked) {
						if (!theForm.elements[elmntName][i].disabled) {
							return false;
						}
					}
				}
				selectAllChkBxName.checked = true;
			} else {
				if (theForm.elements[elmntName].checked) {
					selectAllChkBxName.checked = true;
				}
			}
			return true;
		}
	}

    </SCRIPT>
    
</head>
  
<body>
<html:form action="invalidObjAction.do" method="POST" enctype="multipart/form-data" >  
     <html:hidden property="to" styleId="to"  name="validateObjectForm"/>	
     <html:hidden property="cc" styleId="cc" name="validateObjectForm"/>
     <html:hidden property="subject" styleId="subject" name="validateObjectForm"/>
     <html:hidden property="bodyContent" styleId="bodyContent" name="validateObjectForm"/> 
      <html:hidden property="dbName" styleId="dbName" name="validateObjectForm"/>
   <div>
   <table class="plainTable">
   <tr><td>&nbsp;&nbsp;</td></tr>
   <tr><td width="20px"></td>
   <td rowspan="2">
	<html:button property="save" onclick="generateExcel()"  styleClass="button">Excel</html:button>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="sendEmail()"  styleClass="button">Send Email</html:button>
	</td>
   </tr></table>
   <br/>
 
<table><tr><td width="20px"></td>
   <td>
    <div id="objectList" >
	<table  class="plainTable">
	<tr><td>
	 <html:checkbox name="validateObjectForm" property="selectAll" 
							styleId="selectAll" onclick="javascript:checkAll(this)"  >Select All</html:checkbox>
	</td></tr>
<tr><td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="PROCEDURE" >Procedure</html:checkbox>
	</td>
<td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="FUNCTION" >Function</html:checkbox>
	</td>
<td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="PACKAGE" >Package</html:checkbox>
	</td>
<td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="TABLE" >Table</html:checkbox>
	</td><td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="VIEW" >View</html:checkbox>
	</td></tr>
	<tr><td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="SEQUENCE" >Sequence</html:checkbox>
	</td>
	<td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="TRIGGER" >Trigger</html:checkbox>
	</td>
	<td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="TYPE" >Type</html:checkbox>
	</td>
	<td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="MATERIALIZED VIEW" >Materialized View</html:checkbox>
	</td>
<td>
	<html:checkbox name="validateObjectForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="DB LINK" >DB Link</html:checkbox>
	</td></tr>
	</table>
	<table  class="plainTable">
	<tr><td>Character length</td><td><html:text property="length"/></td></tr>
	</table>
	
	</div>
	</td></tr>
	<tr><td width="20px"></td>
   <td><html:button property="save" onclick="validateCodingViolation()"  styleClass="button">Validate coding violation</html:button></td></tr>
 <tr><td width="20px"></td>
   <td>

	<div style="height:400px; overflow:auto;" style="display: none;" id="violationStandardTable">
	<table  class="gridtable">
</table></div>
</td>

   </tr>
   </table>
   </div>
 
 
</html:form>
</body>
</html>