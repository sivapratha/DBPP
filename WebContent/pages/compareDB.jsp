<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 

<html>
    <head>
         <link href="static/DBPP.css" rel="stylesheet" type="text/css">
    <SCRIPT language="javascript">
    window.onload = function processOnLoad(){
    	
    	 var dbName = document.getElementById('dbName');
	 		var i;
	 		  var selectedDb = document.getElementById('selectedDb').value;
	 		for (i=0; i<dbName.options.length; i++) {
	 		  if (selectedDb.indexOf(dbName.options[i].value) != -1) {
	 			 dbName.options[i].selected = true;
	 		  }
			}
	 		
	 		var structCompType = document.getElementById('structCompType').value;
	 		viewObjectList(structCompType);
		  var slctdDbObj = document.getElementById('slctdDbObj').value;
		  var checkbox = document.getElementsByName('isSelected');
	 		var objectCnt = 0;
	 		var max = checkbox.length;
			  for (var i=0; i<max; i++) {
				  if(slctdDbObj != '' && checkbox[i].value.indexOf(slctdDbObj) != -1){
					  	checkbox[i].disabled = false;
						checkbox[i].checked = true;
				  }
				  objectCnt++;
			  }
    	
			 
    }
    function compare(){
 		var compareTypeOpt = document.getElementsByName('compareType');
 		var max = compareTypeOpt.length;
 		
		  for (var i=0; i<max; i++) {
			  if(compareTypeOpt[i].checked == true){
				 var compareType = compareTypeOpt[i].value;	
			  }				  
		  }
		  
		  var structCompType = document.getElementsByName('structCompType');
 		var actionName = "compareDB.do";
 		if("version"==compareType){
 			actionName = "versionCompare.do";
 		}else if("structure"==compareType){
 			actionName = "tableCompare.do";
 		}else if("ObjectExistent"==compareType){
 			actionName = "ObjectCompare.do";
 		}else if("ObjectContent"==compareType){
 			actionName = "ObjectContentCompare.do";
 			
 		}
 		 document.getElementById('structCompType').value = compareType;
   		var dbName = document.getElementById('dbName');
 		var i;
 		var selectedDb = '';
 		var selectedDBCnt = 0;
 		for (i=0; i<dbName.options.length; i++) {
 		  if (dbName.options[i].selected) {
 			 selectedDb = selectedDb + dbName.options[i].value +',';
 			selectedDBCnt++;
 		  }
		}
 		if(selectedDBCnt != 2 && (actionName == "tableCompare.do" || actionName == "ObjectContentCompare.do" )){
 			alert('Select only two Databases to Compare');
 			return;
 		}
 		
 		document.getElementById('selectedDb').value = selectedDb;
 		
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
		  
		  if(objectCnt!=1 && actionName == "tableCompare.do"){
			  alert('Choose any one of Object type to compare');
			  return;
		  }

	 		
 		
		  document.getElementById('slctdDbObj').value = selectedObjects;
		document.compareDBForm.action = actionName;
		document.compareDBForm.submit();
    }
    
    
    function viewObjectList(option){
    	var checkbox = document.getElementsByName('isSelected');
    	
    	if("version"==option){    		
    		var max = checkbox.length;
	   		  for (var i=0; i<max; i++) {
	   			  if(checkbox[i].value == 'PROCEDURE' || checkbox[i].value == 'FUNCTION' || checkbox[i].value == 'PACKAGE'){
	   				checkbox[i].disabled = false;
	   				checkbox[i].checked = false;
	   			  }else{
	   				checkbox[i].disabled = true;
	   				checkbox[i].checked = false;
	   			  }
	   		    
	   		  }
 		}else if("structure"==option){
 			var max = checkbox.length;
	   		  for (var i=0; i<max; i++) {
	   			  if(checkbox[i].value == 'TABLE' || checkbox[i].value == 'VIEW' || checkbox[i].value == 'MATERIALIZED VIEW'){
	   				checkbox[i].disabled = false;
	   				checkbox[i].checked = false;
	   			  }else{
	   				checkbox[i].disabled = true;
	   				checkbox[i].checked = false;
	   			  }
	   		    
	   		  }
 		}else if("ObjectExistent"==option){
 			var max = checkbox.length;
	   		  for (var i=0; i<max; i++) {	   			  
	   				checkbox[i].disabled = false;
	   				checkbox[i].checked = false;
	   		  }
 		}else if("ObjectContent"==option){
 			var max = checkbox.length;
	   		  for (var i=0; i<max; i++) {	 
	   			 if(checkbox[i].value == 'TABLE' || checkbox[i].value == 'VIEW'  || checkbox[i].value == 'SEQUENCE' || checkbox[i].value == 'MATERIALIZED VIEW'){
		   				checkbox[i].disabled = false;
		   				checkbox[i].checked = false;
		   			  }else{ 
		   				checkbox[i].disabled = false;
		   				checkbox[i].checked = false;
		   			  }
	   		  }
		}
    	
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
  
	function disableCheckbox(checkbox, disable) {
		  var max = checkbox.length;
		  for (var i=0; i<max; i++) {
		    checkbox[i].disabled = disable;		    
		  }
		 
		}
    </SCRIPT>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to DB Power Pack</title>
    </head>
     <% 
  String resultMsg = "";
  if (request.getAttribute("RESULT_MSG") != null)
	resultMsg = (String) request.getAttribute("RESULT_MSG");
%>
    <body >
    <html:form action="compareDB.do" method="POST" enctype="multipart/form-data" >
    <html:hidden property="selectedDb" styleId="selectedDb" name="compareDBForm"/>
    <html:hidden property="slctdDbObj" styleId="slctdDbObj" name="compareDBForm"/>
    <html:hidden property="structCompType" styleId="structCompType" name="compareDBForm"/>
     <div>
     <table width="100%" height="10" class="plainTable">
      <% if (resultMsg != null && resultMsg != "" && resultMsg.equals("Error")) { %> 	
      <tr>
        <td class="fontstyle-red">
       Failed to add the Configuration Details
        </td>
      </tr>  
   	  <% } %> 
   	</table>   
	<table class="plainTable">
	<tr><td width="50px">&nbsp;&nbsp;</td></tr>
	<tr><td width="50px"></td><td class="fontstyle-green" colspan="4">Select the database to be compared and comparison type</td></tr>
	<tr><td width="50px">&nbsp;&nbsp;</td></tr>
	<tr><td width="50px">&nbsp;&nbsp;</td></tr>
	<tr><td width="50px"></td>
	<td>
	<div>
	<table  class="plainTable" >
	<tr>
	<td class="fontstyle-green" valign="top">Configured database</td> 
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
	<td width="300px" valign="top">
	<html:select property="dbName" styleId="dbName" name="compareDBForm"  multiple="multiple" size="10" style="width:200px" >
	<font class="fontstyle1">
		<html:optionsCollection property="dbNameList" name="compareDBForm" label="dbName" value="dbName"/>
	</font>	
	</html:select>
	</td>
	</tr>
	
	</table>
		</div>
		</td>
	<td width="250px" valign="top">
	<div > 	 
	 <p class="fontstyle-green">Compare Type</p>	
	<html:radio property="compareType" styleId="compareType"  name="compareDBForm" value="version" onclick="viewObjectList(this.value);" >Version</html:radio> 
	 <br/>
	<html:radio property="compareType" styleId="compareType" name="compareDBForm" value="ObjectExistent" onclick="viewObjectList(this.value);" styleClass="fontstyle1" >Object Existence</html:radio>
	<br/>
	 <html:radio property="compareType" styleId="compareType" name="compareDBForm" value="structure" onclick="viewObjectList(this.value);" styleClass="fontstyle1" >Structure</html:radio> 
	 <br/>
	<html:radio property="compareType" styleId="compareType" name="compareDBForm" value="ObjectContent" onclick="viewObjectList(this.value);" styleClass="fontstyle1" >Code</html:radio> 
	</div>
	</td>
	<td valign="top">
	<div id="objectList" >
	<table  class="plainTable">
	<tr><td class="fontstyle-green" align="center" colspan="2">Database objects <html:checkbox name="compareDBForm" property="selectAll" 
							styleId="selectAll" onclick="javascript:checkAll(this)"  /></td></tr>	
	<tr><td colspan="2">&nbsp;</td></tr>	
	<tr><td width="100px">
	 <html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="PROCEDURE" disabled="true">Procedure</html:checkbox>
	</td><td width="100px">&nbsp;&nbsp;
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="FUNCTION" disabled="true">Function</html:checkbox>
	</td></tr>
	<tr><td>
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="PACKAGE" disabled="true">Package</html:checkbox>
	</td><td>&nbsp;&nbsp;
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="PACKAGE BODY" disabled="true">Package Body</html:checkbox>
	</td></tr>
	<tr><td>
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="TABLE" disabled="true">Table</html:checkbox>
	</td><td>&nbsp;&nbsp;
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="VIEW" disabled="true">View</html:checkbox>
	</td></tr>
	<tr><td>
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="SEQUENCE" disabled="true">Sequence</html:checkbox>
	</td><td>&nbsp;&nbsp;
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="TRIGGER" disabled="true">Trigger</html:checkbox>
	</td></tr>
	<tr><td>
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="TYPE" disabled="true">Type</html:checkbox>
	</td><td>&nbsp;&nbsp;
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="MATERIALIZED VIEW" disabled="true">Materialized View</html:checkbox>
	</td></tr>
	<tr><td>
	<html:checkbox name="compareDBForm" property="isSelected" styleId="objChkBox"
										onclick="checkSelectAll(this,'selectAll')" value="DB LINK" disabled="true">DB Link</html:checkbox>
	</td><td>&nbsp;&nbsp;
	
	</td></tr>
	</table>
	
	</div>
	</td>
	</tr>
	</table>
	<table align="center">
	<tr><td width="50px"></td><td align="center"><html:button property="save" onclick="compare()" styleClass="button">Compare</html:button></td></tr>
	</table>
	</div>
	</html:form>
    </body>
</html>
