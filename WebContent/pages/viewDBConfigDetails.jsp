<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" %> 
<%@page contentType="text/html" import="java.text.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %> 
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>

<html>
    <head>
    <link href="static/DBPP.css" rel="stylesheet" type="text/css">
    <meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<base target="_top" />
  <SCRIPT language="javascript">
    function configAction(configAct){
    	var actionName = "viewDBConfig.do";
    	if(configAct=='ADD'){
    		actionName = "addDBConfig.do";
    	}else if(configAct=='REMOVE'){
    		actionName = "removeDBConfig.do";
    	}
    	
    	var removeConfigObj = "";
    	
    	var checkbox = document.getElementsByName('isSelected');
    	var max = checkbox.length;
		  for (var i=0; i<max; i++) {
			  if(checkbox[i].checked == true){
				  if(removeConfigObj==""){
					  removeConfigObj = checkbox[i].value;
				  }else {
					  removeConfigObj = selectedObjects +","+ checkbox[i].value;
				  }
			  }
				  
		  }
    		
		  document.dbConfigForm.removeConfig.value = removeConfigObj;
 		
		document.dbConfigForm.action = actionName;
		document.dbConfigForm.submit();
    }
    
    function addDBConfig() {

		var myArguments = new Object();
		var url = "addConfig.do";
		var result = window.showModalDialog(url, myArguments, 
								"dialogWidth:400px;dialogHeight:300px;center:yes;scroll:no;status:no");
		if(result != undefined && result.dbName != ""){
			document.dbConfigForm.dbName.value = result.dbName;
			document.dbConfigForm.serverName.value = result.serverName;
			document.dbConfigForm.portNumber.value = result.portNumber;
			document.dbConfigForm.sid.value = result.sid;
			document.dbConfigForm.userName.value = result.userName;
			document.dbConfigForm.passWord.value = result.passWord;
			configAction('ADD');
		}
		
		
	}

    
    function checkAll(chkall) {
		var selectAllList = document.getElementsByName('isSelected');
		if (selectAllList != null) {
	for ( var i = 0; i < selectAllList.length; i++) {
				selectAllList[i].checked = chkall.checked;
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link href="static/DBPP.css" rel="stylesheet" type="text/css">
    
        <title>Welcome to DB Power Pack</title>
    </head>
    <% 
  String resultMsg = "";
  if (request.getAttribute("RESULT_MSG") != null)
	resultMsg = (String) request.getAttribute("RESULT_MSG");
%>
    <body>
    <html:form action="viewDBConfig.do" method="POST" enctype="multipart/form-data" >
    <html:hidden property="removeConfig" styleId="removeConfig" name="dbConfigForm"/>
   <html:hidden property="dbName" styleId="dbName" name="dbConfigForm"/>
	<html:hidden property="serverName" styleId="serverName" name="dbConfigForm"/>	 
	<html:hidden property="portNumber" styleId="portNumber" name="dbConfigForm"/>
	<html:hidden property="sid" styleId="sid" name="dbConfigForm"/>
	<html:hidden property="userName" styleId="userName" name="dbConfigForm"/>
	<html:hidden property="passWord" styleId="passWord" name="dbConfigForm"/>
<div>
<table width="100%" height="10" class="plainTable">
      <% if (resultMsg != null && resultMsg != "" && resultMsg.equals("Error")) { %> 	
      <tr>
        <td  class="fontstyle-red">
       Failed to add the Configuration Details
        </td>
      </tr>  
   	  <% } %> 
   	</table> 
</div>   	
	<table class="plainTable">
	<tr><td width="50px"></td><td class="fontstyle-green">Configured Databases</td></tr>
	<tr><td width="50px">&nbsp;&nbsp;</td></tr>
	<tr><td width="50px"></td><td>
	<div style="height:250px; overflow:auto;">
	<table class="gridtable">
	<tr>
	<th width="33px" nowrap="nowrap" ><html:checkbox name="dbConfigForm" property="selectAll" styleId="selectAll" onclick="javascript:checkAll(this)" /></th>
	<th width="133px" nowrap="nowrap">Database Name</th>
	<th width="133px" nowrap="nowrap">Server Name</th>
	<th width="133px" nowrap="nowrap">Port Number</th>
	<th width="133px">Service Name</th>
	<th width="133px" nowrap="nowrap">User Name</th>
	</tr>
	<logic:notEmpty name="dbConfigForm" property="dbConfigList">
	  <logic:iterate id="configObj" name="dbConfigForm" property="dbConfigList" type="com.hp.dbpowerpack.entities.DBConfigDetails">
	  <tr>
	  <td width="33px" align="center"><html:checkbox name="dbConfigForm" property="isSelected" onclick="checkSelectAll(this,'selectAll')" value="${configObj.dbName}"/></td>
	  <td width="133px"><bean:write  name="configObj" property="dbName"  /><logic:empty name="configObj" property="dbName" >&nbsp;</logic:empty></td>  
	  <td width="133px"><bean:write  name="configObj" property="serverName"  /><logic:empty name="configObj" property="serverName" >&nbsp;</logic:empty></td>
	  <td width="133px"><bean:write  name="configObj" property="portNumber"  /><logic:empty name="configObj" property="portNumber" >&nbsp;</logic:empty></td>
	  <td width="133px"><bean:write  name="configObj" property="sid"  /><logic:empty name="configObj" property="sid" >&nbsp;</logic:empty></td>
	  <td width="133px"><bean:write  name="configObj" property="userName"  /><logic:empty name="configObj" property="userName" >&nbsp;</logic:empty></td>
	  </tr>
	  </logic:iterate>
	  </logic:notEmpty> 
	
	</table>
	</div>
	</td></tr></table>
	<br/>
	<div>
	
	<table class="plainTable">
	<tr><td width="50px"></td><td colspan="2"><html:button property="save" onclick="addDBConfig()" styleClass="button">Add Config</html:button>     
	<td colspan="2"><html:button property="save" onclick="configAction('REMOVE')" styleClass="button">Remove Config</html:button></td>
	</tr>	
</table>

	</div>
	</html:form>
    </body>
</html>
