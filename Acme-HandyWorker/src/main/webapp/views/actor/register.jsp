<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="actor/save.do" modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="suspicious" />
	
	<form:hidden path="userAccount.authorities" />
	
	<div class="form-group"> 
		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username" />
		</form:label>
		<form:input class="form-control" path="userAccount.username" />
		<form:errors class="error" path="userAccount.username" />
	</div>
	<div class="form-group"> 
		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password" />
		</form:label>
		<form:password class="form-control" path="userAccount.password" />
		<form:errors class="error" path="userAccount.password" />
	</div>

	<hr />
	
	<div class="form-group"> 
		<form:label path="name">
			<spring:message code="actor.name" />
		</form:label>
		<form:input class="form-control" path="name" />
		<form:errors class="error" path="name" />
	</div>
	<div class="form-group"> 
		<form:label path="middleName">
			<spring:message code="actor.middleName" />
		</form:label>
		<form:input class="form-control" path="middleName" />
		<form:errors class="error" path="middleName" />
	</div>
</form:form>