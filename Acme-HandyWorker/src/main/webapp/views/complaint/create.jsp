<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
<head>
<title><spring:message code="complaint.titleCreate" /></title>
</head>
<body>
	<form:form modelAttribute="complaint" action="complaint/customer/edit.do">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="selfAsigned" />
		<!-- Ticker -->
		<form:hidden path="ticker" />
		<!-- Moment -->
		<form:hidden path="moment"/>
		<!-- Description -->
		<p>
		<form:label path="description">
			<spring:message code="complaint.description" />
		</form:label>
		<form:textarea path="description" />
		<form:errors path="description" />
		</p>
		<!-- Attachments -->
		<p>
		<form:label path="attachments">
			<spring:message code="complaint.attachments" />
		</form:label>
		<form:textarea path="attachments" />
		<form:errors path="attachments" cssClass="error" />
		</p>
		<!-- Report -->
		<form:hidden path="report" />
		<input type="submit" class="btn btn-danger" name="save"
			value="<spring:message code="complaint.save"/>"
			onclick="return confirm('<spring:message code="complaint.confirm.save" />')" >

		<input type="button" name="cancel"
			value="<spring:message code="complaint.cancel"/>"
			onclick="javascript: relativeRedir('complaint/customer/list.do');" />
	</form:form>

</body>
</html>