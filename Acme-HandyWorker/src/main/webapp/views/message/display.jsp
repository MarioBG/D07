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

<h3>${priority}</h3> <p>${messageObject.priority}</p>
<h3>${subject}</h3> <p>${messageObject.subject}</p>
<h3>${body}</h3> <p>${messageObject.body}</p>
<h3>${sender}</h3> <p>${messageObject.sender.userAccount.username}</p>
<h3>${recipients}</h3> <p>
<jstl:forEach items="${messageObject.recipients}" var="e">
	<p>
		${e.userAccount.username}
	</p>
</jstl:forEach>
</p>
<input type="button" name="back" class="ui button"
	value="<spring:message code="message.back" />"
	onclick="javascript: relativeRedir('box/list.do');" />

</html>
