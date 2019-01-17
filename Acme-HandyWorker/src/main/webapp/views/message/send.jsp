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
<spring:message code="message.saveStr" var="saveStr" />
<spring:message code="message.destinations" var="destinations" />

<form:form action="message/edit.do" modelAttribute="messageForm">
	<form:hidden path="id"/>
	
	<h3>${destinations}</h3>
	<div><form:select path="recipientIds" multiple="true">
	  <jstl:forEach items="${allActors}" var="actor">
	  	<form:option value="${actor.userAccount.id}">${actor.userAccount.username}</form:option>
	  </jstl:forEach>
	</form:select></div>
	<form:errors cssClass="error" path="recipientIds"></form:errors>
	<h3>${subject}</h3>
	<div>
		<form:input path="subject" placeholder="${subject}"/>
		<form:errors cssClass="error" path="subject"></form:errors>
	</div>
	<h3>${body}</h3>
	<div>
		<form:textarea path="body"/>
		<form:errors cssClass="error" path="body"></form:errors>
	</div>
	<h3>${priority}</h3>
	<div>
		<form:select path="priority">
			<form:option value="NEUTRAL"></form:option>
			<form:option value="HIGH"></form:option>
			<form:option value="LOW"></form:option>
		</form:select>
		<form:errors cssClass="error" path="priority"></form:errors>
	</div>
	
	<input type="submit" name="save" value="${saveStr}">
</form:form>