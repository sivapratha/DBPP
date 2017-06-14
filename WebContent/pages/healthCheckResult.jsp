<%@ page language="java"%>
<%@page contentType="text/html" import="java.text.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
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
<html:form action="healthCheck.do" method="POST"
	enctype="multipart/form-data">
	
     <html:hidden property="to" styleId="to"  name="healthCheckForm"/>	
     <html:hidden property="cc" styleId="cc" name="healthCheckForm"/>
     <html:hidden property="subject" styleId="subject" name="healthCheckForm"/>
     <html:hidden property="bodyContent" styleId="bodyContent" name="healthCheckForm"/> 
	<div>
	<table>
	<tr><td>&nbsp;&nbsp;</td></tr>
	<tr>
   <td width="20px"></td><td rowspan="2">
	<html:button property="save" onclick="generateExcel()"  styleClass="button">Excel</html:button>
	</td>
	<td rowspan="2">
	<html:button property="save" onclick="sendEmail()"  styleClass="button">Send Email</html:button>
	</td>
   </tr></table>
   <table>
   <tr><td>&nbsp;&nbsp;</td></tr>
   <tr><td width="20px"></td>
   <td>
	<logic:equal name="healthCheckForm" property="displayFlag"
		value="PartitionedTable">
		<div style="height: 500px; overflow: auto;">
		<table class="gridtable">

			<tr>
				<th>Table Name</th>
			</tr>

			<logic:notEmpty name="healthCheckForm"
				property="partitionedTableList">

				<logic:iterate id="tableName" name="healthCheckForm"
					property="partitionedTableList">
					<tr>
						<td height="20" width="100px"><bean:write name="tableName" /><logic:empty
							name="tableName">&nbsp;</logic:empty></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</div>
	</logic:equal> 
	<logic:equal name="healthCheckForm" property="displayFlag"
		value="BlockContention">
		<div style="height: 500px; overflow: auto;">
		<table class="gridtable">

			<tr>
				<th>Class</th>
				<th>Total Waits</th>
				<th>Total Time</th>
			</tr>

			<logic:notEmpty name="healthCheckForm" property="blockContentionList">

				<logic:iterate id="dbObjDetails" name="healthCheckForm"
					property="blockContentionList"
					type="com.hp.dbpowerpack.Model.BlockContentionModel">
					<tr>
						<td height="20" ><bean:write name="dbObjDetails" property="waitClass" /><logic:empty
							name="dbObjDetails" property="class">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails"
							property="totalWaits" /><logic:empty name="dbObjDetails"
							property="totalWaits">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails"
							property="totalTime" /><logic:empty name="dbObjDetails"
							property="totalTime">&nbsp;</logic:empty></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</div>
	</logic:equal>
	
	<logic:equal name="healthCheckForm" property="displayFlag"
		value="AvailableJobs">
		<div style="height: 500px; overflow: auto;">
		<table class="gridtable">

			<tr>
				<th>Log user</th>
				<th>Priv user</th>
				<th>Last Date</th>
				<th>Last Second</th>
				<th>This Date</th>
				<th>This Second</th>
				<th>Is Broken</th>
				<th>What </th>
				
			</tr>

			<logic:notEmpty name="healthCheckForm" property="availableJobsList">

				<logic:iterate id="dbObjDetails" name="healthCheckForm"
					property="availableJobsList"
					type="com.hp.dbpowerpack.Model.DbJobsModel">
					<tr>
						<td height="20" ><bean:write name="dbObjDetails" property="logUser" /><logic:empty name="dbObjDetails" property="logUser">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="privUser" /><logic:empty name="dbObjDetails" property="privUser">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="MM/dd/yyyy" name="dbObjDetails" property="lastDate" /><logic:empty name="dbObjDetails" property="lastDate">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="lastSec" /><logic:empty name="dbObjDetails" property="lastSec">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="MM/dd/yyyy" name="dbObjDetails" property="thisDate" /><logic:empty name="dbObjDetails" property="thisDate">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="thisSec" /><logic:empty name="dbObjDetails" property="thisSec">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="broken" /><logic:empty name="dbObjDetails" property="broken">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="what" /><logic:empty name="dbObjDetails" property="what">&nbsp;</logic:empty></td>						
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</div>
	</logic:equal>
	
	<logic:equal name="healthCheckForm" property="displayFlag"
		value="JobsBroken">
		<div style="height: 500px; overflow: auto;">
		<table class="gridtable">

			<tr>
				<th>Log user</th>
				<th>Priv user</th>
				<th>Last Date</th>
				<th>Last Second</th>
				<th>This Date</th>
				<th>This Second</th>
				<th>What </th>
				
			</tr>

			<logic:notEmpty name="healthCheckForm" property="jobsBrokenList">

				<logic:iterate id="dbObjDetails" name="healthCheckForm"
					property="jobsBrokenList"
					type="com.hp.dbpowerpack.Model.DbJobsModel">
					<tr>
						<td height="20" ><bean:write name="dbObjDetails" property="logUser" /><logic:empty name="dbObjDetails" property="logUser">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="privUser" /><logic:empty name="dbObjDetails" property="privUser">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="MM/dd/yyyy"  name="dbObjDetails" property="lastDate" /><logic:empty name="dbObjDetails" property="lastDate">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="lastSec" /><logic:empty name="dbObjDetails" property="lastSec">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="MM/dd/yyyy" name="dbObjDetails" property="thisDate" /><logic:empty name="dbObjDetails" property="thisDate">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="thisSec" /><logic:empty name="dbObjDetails" property="thisSec">&nbsp;</logic:empty></td>						
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="what" /><logic:empty name="dbObjDetails" property="what">&nbsp;</logic:empty></td>						
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</div>
	</logic:equal>
	
	<logic:equal name="healthCheckForm" property="displayFlag"
		value="JobsRunning">
		<div style="height: 500px; overflow: auto;">
		<table class="gridtable">

			<tr>
				<th>Job</th>
				<th>Failures</th>
				<th>Last Date</th>
				<th>Last Second</th>
				<th>This Date</th>
				<th>This Second</th>				
			</tr>

			<logic:notEmpty name="healthCheckForm" property="jobsRunningList">

				<logic:iterate id="dbObjDetails" name="healthCheckForm"
					property="jobsRunningList"
					type="com.hp.dbpowerpack.Model.DbJobsModel">
					<tr>
						<td height="20" ><bean:write name="dbObjDetails" property="job" /><logic:empty name="dbObjDetails" property="job">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="failures" /><logic:empty name="dbObjDetails" property="failures">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="MM/dd/yyyy" name="dbObjDetails" property="lastDate" /><logic:empty name="dbObjDetails" property="lastDate">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="lastSec" /><logic:empty name="dbObjDetails" property="lastSec">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="MM/dd/yyyy" name="dbObjDetails" property="thisDate" /><logic:empty name="dbObjDetails" property="thisDate">&nbsp;</logic:empty></td>
						<td height="20" ><bean:write format="#" name="dbObjDetails" property="thisSec" /><logic:empty name="dbObjDetails" property="thisSec">&nbsp;</logic:empty></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</div>
	</logic:equal>
	
   </tr>
   </table>
	</div>
</html:form>
</body>
</html>