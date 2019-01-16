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

<spring:message code="complaint.ticker" var="ticker"/>
<spring:message code="complaint.moment" var="moment"/>
<spring:message code="complaint.description" var="description"/>

<display:table name="complaints" id="row"
	requestURI="complaint/customer/list.do" pagesize="5" class="displaytag">
	
<security:authorize access="hasRole('CUSTOMER')">
	<display:column>
		<a href="complaint/customer/edit.do?complaintId=${row.id}">
			<spring:message code="complaint.edit"/>
		</a>
	</display:column>
	<display:column>
		<a href="complaint/customer/list.do?complaintId=${row.id}">
			<spring:message code="complaint.delete"/>
		</a>
	</display:column>
</security:authorize>
	<display:column property="ticker" titleKey="${ticker}"/>
	<display:column property="moment" titleKey="${moment}"/>
	<display:column property="description" titleKey="${description}"/>
	<display:column>
		<input type="button" name="attachments"
			value="<spring:message code="complaint.attachments"/>"
			onclick="javascript: relativeRedir('complaint/complaintAttachments.do');"/>
	</display:column>
	<display:column property="report" titleKey="complaint.report"/>
	
</display:table>

<a href="complaint/customer/create.do">
	<spring:message code="complaint.create"/>
</a>

