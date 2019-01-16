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
<spring:message code="message.recipients" var="recipients" />
<spring:message code="message.sender" var="sender" />

<display:column property="priority" title="${priority}" />
<display:column property="subject" title="${subject}" />
<display:column property="moment" titleKey="${moment}" />
<display:column property="body" titleKey="${body}" />
<display:column property="sender" titleKey="${sender}" />
<display:column titleKey="${recipients }">
		<jstl:forEach items="${recipients}" var="e">
			${e.name}, ${e.middleName}, ${e.surname}
		</jstl:forEach>
	</display:column>

</html>
