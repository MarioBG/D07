<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<h4>
		<spring:message code="administrator.avgMinMaxStdDvtFixUpTasksPerUser"
			var="avgMinMaxStdDvtFixUpTasksPerUserHead" />
		<jstl:out value="${avgMinMaxStdDvtFixUpTasksPerUserHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdDvtFixUpTasksPerUser">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdDvtFixUpTasksPerUser}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.avgMinMaxStrDvtApplicationPerFixUpTask"
			var="avgMinMaxStrDvtApplicationPerFixUpTaskHead" />
		<jstl:out value="${avgMinMaxStrDvtApplicationPerFixUpTaskHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStrDvtApplicationPerFixUpTask">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStrDvtApplicationPerFixUpTask}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.avgMinMaxStrDvtPerFixUpTask"
			var="avgMinMaxStrDvtPerFixUpTaskHead" />
		<jstl:out value="${avgMinMaxStrDvtPerFixUpTaskHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStrDvtPerFixUpTask">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStrDvtPerFixUpTask}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.avgMinMaxStrDvtPerApplication"
			var="avgMinMaxStrDvtPerApplicationHead" />
		<jstl:out value="${avgMinMaxStrDvtPerApplicationHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStrDvtPerApplication">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStrDvtPerApplication}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.ratioOfPendingApplications"
			var="ratioOfPendingApplicationsHead" />
		<jstl:out value="${ratioOfPendingApplicationsHead}" />
		=
		<fmt:formatNumber value="${ratioOfPendingApplications*100}"
			maxFractionDigits="2" />
		%
	</h4>
	
	<h4>
		<spring:message code="administrator.ratioOfAcceptedApplications"
			var="ratioOfAcceptedApplicationsHead" />
		<jstl:out value="${ratioOfAcceptedApplicationsHead}" />
		=
		<fmt:formatNumber value="${ratioOfAcceptedApplications*100}"
			maxFractionDigits="2" />
		%
	</h4>
	
	<h4>
		<spring:message code="administrator.ratioOfRejectedApplications"
			var="ratioOfRejectedApplicationsHead" />
		<jstl:out value="${ratioOfRejectedApplicationsHead}" />
		=
		<fmt:formatNumber value="${ratioOfRejectedApplications*100}"
			maxFractionDigits="2" />
		%
	</h4>
	
	<h4>
		<spring:message code="administrator.ratioOfPendingApplicationsCantChange"
			var="ratioOfRejectedApplicationsCantChangeHead" />
		<jstl:out value="${ratioOfRejectedApplicationsCantChangeHead}" />
		=
		<fmt:formatNumber value="${ratioOfRejectedApplicationsCantChange*100}"
			maxFractionDigits="2" />
		%
	</h4>
	
	<table class="displaytag" name="customersWith10PercentMoreAvgFixUpTask">
		<tr>
			<th><spring:message
					code="administrator.customersWith10PercentMoreAvgFixUpTask"
					var="bestHeader" /> <jstl:out value="${bestHeader}"></jstl:out></th>
		</tr>
			<jstl:forEach var="datos"
				items="${customersWith10PercentMoreAvgFixUpTask}">
			<tr>
				<td><jstl:out value="${datos.userAccount.username}" /></td>
			</tr>
			</jstl:forEach>

	</table>
	
	<table class="displaytag" name="handyWorkersWith10PercentMoreAvgApplicatios">
		<tr>
			<th><spring:message
					code="administrator.handyWorkersWith10PercentMoreAvgApplicatios"
					var="bestHeader1" /> <jstl:out value="${bestHeader1}"></jstl:out></th>
		</tr>
		
			<jstl:forEach var="datos"
				items="${handyWorkersWith10PercentMoreAvgApplicatios}">
				<tr>
				<td><jstl:out value="${datos.userAccount.username}" /></td>
				</tr>
			</jstl:forEach>
		

	</table>
	
	<%-- B-LEVEL INDICATORS --%>
	

	<h4>
		<spring:message code="administrator.avgMinMaxStdvComplaintsPerFixUpTask"
			var="avgMinMaxStdvComplaintsPerFixUpTaskHead" />
		<jstl:out value="${avgMinMaxStdvComplaintsPerFixUpTaskHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdvComplaintsPerFixUpTask">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdvComplaintsPerFixUpTask}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.avgMinMaxStdvNotesPerReferee"
			var="avgMinMaxStdvNotesPerRefereeHead" />
		<jstl:out value="${avgMinMaxStdvNotesPerRefereeHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdvNotesPerReferee">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdvNotesPerReferee}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.percentageFixUpTasksWithComplaint"
			var="percentageFixUpTasksWithComplaintHead" />
		<jstl:out value="${percentageFixUpTasksWithComplaintHead}" />
		=
		<fmt:formatNumber value="${percentageFixUpTasksWithComplaint*100}"
			maxFractionDigits="2" />
		%
	</h4>
	
	<table class="displaytag" name="customersByComplaints">
		<tr>
			<th><spring:message code="administrator.customersByComplaints"
					var="bestHeader2" /> <jstl:out value="${bestHeader2}"></jstl:out></th>
		</tr>
		
			<jstl:forEach var="datos" items="${customersByComplaints}">
			<tr>
				<td><jstl:out
							value="${datos.userAccount.username}" /></td>
			</tr>
			</jstl:forEach>
		

	</table>
	
	<table class="displaytag" name="handyWorkersByComplaints">
		<tr>
			<th><spring:message code="administrator.handyWorkersByComplaints"
					var="bestHeader3" /> <jstl:out value="${bestHeader3}"></jstl:out></th>
		</tr>
		
			<jstl:forEach var="datos" items="${handyWorkersByComplaints}">
			<tr>
				<td><jstl:out
							value="${datos.userAccount.username}" /></td>
			</tr>
			</jstl:forEach>

	</table>

<input type="button" name="cancel" value="<spring:message code="administrator.back" />" onclick="javascript: history.back()" />
