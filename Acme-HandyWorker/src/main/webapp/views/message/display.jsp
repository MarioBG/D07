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
<spring:message code="message.priority" var="priority" />
<spring:message code="message.body" var="body" />
<spring:message code="message.sender" var="sender" />

<display:table name="data" id="row" requestURI="message/display.do" class="displaytag">
	<display:column property="priority" title="${priority}" />
	<display:column property="subject" title="${subject}" />
	<display:column property="moment" title="${moment}" />
	<display:column property="body" title="${body}" />
	<display:column property="sender.name" title="${sender}" />
</display:table>
