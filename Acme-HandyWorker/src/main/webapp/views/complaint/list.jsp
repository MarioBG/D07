<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<head>
	<title>
		<spring:message code="complaint.titleList"/>
	</title>
</head>

<spring:message code="complaint.ticker" var="complaintTicker" />
<spring:message code="complaint.moment" var="complaintMoment" />
<spring:message code="complaint.description"
	var="complaintDescription" />
<spring:message code="complaint.attachments" var="complaintAttachments" />
<spring:message code="complaint.selfAsigned" var="complaintSelfAsigned" />
<spring:message code="complaint.fixUpTask" var="fixUpTask" />
<spring:message code="complaint.delete" var="complaintDelete" />
<spring:message code="complaint.edit" var="complaintEdit" />

<display:table pagesize="3" class="displaytag" keepStatus="true"
	name="list" requestURI="/complaint/customer/list.do" id="row">
	<display:column value="asdasd" title="${complaintTicker}"></display:column>
	<display:column value="${row.moment}" title="${complaintMoment}"></display:column>
	<display:column value="${row.description}" title="${complaintDescription}"/>
	<display:column value="${row.attachments}" title="${complaintAttachments}"></display:column>
	<display:column value="${row.selfAsigned}" title="${complaintSelfAsigned}"></display:column>
	<display:column title="${fixUpTask}"><a href="fixuptask/edit.do?fixUpTaskId=${complaintToTask[row.id].id}">${ complaintToTask[row.id].ticker }</a></display:column>

</display:table>

<input type="button" name="create"
	value="<spring:message code="complaint.create" />"
	onclick="javascript: relativeRedir('complaint/customer/create.do');" />