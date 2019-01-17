<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="socialIdentity.nick" var="messageMoment" />
<spring:message code="socialIdentity.socialNetworkName" var="messageSubject" />
<spring:message code="socialIdentity.profileLink" var="messageBody" />
<%-- <spring:message code="message.priority" var="messagePriority" />
<spring:message code="message.recipients" var="messageRecipients" />
<spring:message code="message.sender" var="messageSender" />
<spring:message code="message.view" var="messageView" /> --%>

<display:table pagesize="3" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="/message/list.do" id="row">

	<display:column value="${row.nick}" title="${messagePriority}"></display:column>
	<display:column value="${row.socialNetworkName}" title="${messageMoment}"></display:column>
	<display:column value="${row.profileLink}" title="${messageSubject}"></display:column>
<%-- 	<display:column value="${row.body}" title="${messageBody}"></display:column>
	<display:column value="${row.sender}" title="${messageSender}"></display:column>
	<display:column title="${messageRecipients}">
		<jstl:forEach items="${row.recipients}" var="e">
			${e.name}, ${e.middleName}, ${e.surname}
		</jstl:forEach>
	</display:column> --%>
	<display:column>
			<a href="message/display.do?messageId=${row.id}">${messageView}</a>
	</display:column>
</display:table>

<input type="submit" name="create" class="ui button"
			value="<spring:message code="socialIdentity.create" />"
			onclick="javascript: relativeRedir('socialIdentity/create.do');" />&nbsp;