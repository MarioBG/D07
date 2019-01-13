<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>
<html>
<spring:message code="phase.title" var="title" />
<spring:message code="phase.description" var="description" />
<spring:message code="phase.startMoment" var="startMoment" />
<spring:message code="phase.endMoment" var="endMoment" />


<security:authorize access="hasRole('HANDYWORKER')">
	<form:form action="phase/edit.do" modelAttribute="phase">
		<form:hidden path="id" />
		<form:hidden path="version" />

		<form:input path="title" placeholder="${title }" />
		<form:errors cssClass="error" path="title"></form:errors>

		<form:input path="description" placeholder="${description }" />
		<form:errors cssClass="error" path="description"></form:errors>

		<form:input path="startMoment" placeholder="${startMoment }" />
		<form:errors cssClass="error" path="startMoment"></form:errors>

		<form:input path="endMoment" placeholder="${endMoment }" />
		<form:errors cssClass="error" path="endMoment"></form:errors>

	</form:form>

<input type="button" name="save"
	value="<spring:message code="phase.save" />"
	onclick="javascript: relativeRedir('phase/view.do');" />

<input type="button" name="cancel"
	value="<spring:message code="phase.cancel" />"
	onclick="javascript: relativeRedir('phase/view.do');" />
	
</security:authorize>

</html>
