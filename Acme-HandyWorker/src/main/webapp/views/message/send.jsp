<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<spring:message code="message.moment" var="moment" />
<spring:message code="message.subject" var="subject" />
<spring:message code="message.body" var="body" />
<spring:message code="message.priority" var="priority" />
<spring:message code="message.messageSave" var="messageSave" />
<spring:message code="message.recipients" var="destinations" />

<form:form id="submit-form" action="message/edit.do" modelAttribute="data">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="sender"/>
	<form:hidden path="recipients"/>
	
	<input type="text" name="destinations" placeholder="${destinations}">
	
	<form:input path="subject" placeholder="${subject }"/>
	<form:errors cssClass="error" path="subject"></form:errors>
	
	<form:textarea path="body"/>
	<form:errors cssClass="error" path="body"></form:errors>
	
	<form:select path="priority">
		<form:option value="NEUTRAL"></form:option>
		<form:option value="HIGH"></form:option>
		<form:option value="LOW"></form:option>
	</form:select>
	<form:errors cssClass="error" path="priority"></form:errors>
	
	<input type="submit" class="button" name="save" value="<spring:message code="message.messageSave" />">
</form:form>

<script>
	$('#submit-form').submit(function() {
		let username = $('[name="destinations"]').val();
		let action = $('#submit-form').attr('action');
		$('#submit-form').attr('action', action + '?usernames=' + username);
	    return true;
	});
</script>