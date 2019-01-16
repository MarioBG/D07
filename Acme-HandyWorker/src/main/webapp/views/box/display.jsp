<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<spring:message code="box.name" var="name" />
<spring:message code="box.messages" var="messages" />
<spring:message code="box.parentBox" var="parentBox" />
<spring:message code="message.priority" var="priority" />
<spring:message code="message.subject" var="subject" />
<spring:message code="message.moment" var="moment" />
<spring:message code="message.view" var="viewMessage" />
<spring:message code="message.delete" var="deleteMessage" />

<display:table name="box" id="row" requestURI="box/display.do" class="displaytag">
	<display:column property="name" title="${name}" />
	<jstl:if test="${not empty row.parentBox }">
	<display:column property="parentBox.name" title="${parentBox}" />
	</jstl:if>
	<display:column title="${priority}">
	<jstl:forEach var="e" items="${row.messages }">
		${e.priority }
	</jstl:forEach>
	</display:column>
	<display:column title="${moment}">
	<jstl:forEach var="e" items="${row.messages }">
		${e.moment }
	</jstl:forEach>
	</display:column>
	<display:column title="${subject}">
	<jstl:forEach var="e" items="${row.messages }">
		${e.subject }
	</jstl:forEach>
	</display:column>
	<display:column>
	<jstl:forEach var="e" items="${row.messages }">
		<a href="message/view.do?messageId=${e.id}">${viewMessage}</a>
	</jstl:forEach>
	</display:column>
	<display:column>
	<jstl:forEach var="e" items="${row.messages }">
		<a href="message/delete.do?messageId=${e.id}">${deleteMessage}</a>
	</jstl:forEach>
	</display:column>
</display:table>

<input type="button" name="edit" class="ui button"
 value="<spring:message code="box.edit" />"
 onclick="javascript: relativeRedir('box/edit.do?boxId=${row.id}');" />
 
 <input type="button" name="delete" class="ui button"
 value="<spring:message code="box.delete" />"
 onclick="javascript: relativeRedir('box/delete.do?boxId=${row.id}');" />
 
<input type="button" name="back" class="ui button"
 value="<spring:message code="customer.back" />"
 onclick="javascript: relativeRedir('box/list.do');" />
</html>