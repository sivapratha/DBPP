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
    
    function addUserConfig() {

		var myArguments = new Object();
		var url = "addUserConfig.do";
		var result = window.showModalDialog(url, myArguments, 
								"dialogWidth:400px;dialogHeight:300px;center:yes;scroll:no;status:no");
		if(result != undefined && result.userId != ""){
			document.userForm.userId.value = result.userId;
			document.userForm.firstName.value = result.firstName;
			document.userForm.lastName.value = result.lastName;
			document.userForm.passWord.value = result.passWord;
			document.userForm.emailID.value = result.emailID;
			document.userForm.action = "addUserAction.do";
			document.userForm.submit();
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
    <html:form action="userConfig.do" method="POST" enctype="multipart/form-data" >
   <html:hidden property="userId" styleId="userId" name="userForm"/>
	<html:hidden property="firstName" styleId="firstName" name="userForm"/>	 
	<html:hidden property="lastName" styleId="lastName" name="userForm"/>
	<html:hidden property="passWord" styleId="passWord" name="userForm"/>
	<html:hidden property="emailID" styleId="emailID" name="userForm"/>
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
	<tr><td width="50px"></td><td class="fontstyle-green">Configured Users</td></tr>
	<tr><td width="50px">&nbsp;&nbsp;</td></tr>
	<tr><td width="50px"></td><td>
	<div style="height:250px; overflow:auto;">
	<table class="gridtable">
	<tr>
	<th width="133px" nowrap="nowrap">User Id</th>
	<th width="133px" nowrap="nowrap">First Name</th>
	<th width="133px" nowrap="nowrap">Last Name</th>
	<th width="133px">Email Id</th>
	</tr>
	<logic:notEmpty name="userForm" property="userConfigList">
	  <logic:iterate id="userObj" name="userForm" property="userConfigList" type="com.hp.dbpowerpack.entities.User">
	  <tr>
	  <td width="133px"><bean:write  name="userObj" property="userId"  /><logic:empty name="userObj" property="userId" >&nbsp;</logic:empty></td>  
	  <td width="133px"><bean:write  name="userObj" property="firstName"  /><logic:empty name="userObj" property="firstName" >&nbsp;</logic:empty></td>
	  <td width="133px"><bean:write  name="userObj" property="lastName"  /><logic:empty name="userObj" property="lastName" >&nbsp;</logic:empty></td>
	  <td width="133px"><bean:write  name="userObj" property="email"  /><logic:empty name="userObj" property="email" >&nbsp;</logic:empty></td>
	  </tr>
	  </logic:iterate>
	  </logic:notEmpty> 
	
	</table>
	</div>
	</td></tr></table>
	<br/>
	<div>
	
	<table class="plainTable">
	<tr><td width="50px"></td><td colspan="2"><html:button property="save" onclick="addUserConfig()" styleClass="button">Add User</html:button>     
	</tr>	
</table>

	</div>
	</html:form>
    </body>
</html>
