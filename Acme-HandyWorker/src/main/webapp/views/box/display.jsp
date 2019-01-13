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
<spring:message code="message.send" var="sendMessage" />

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
	<jstl:if test="${not empty row.messages }" >
	<display:column>
	<jstl:forEach var="e" items="${row.messages }">
		<a href="message/display.do?messageId=${e.id}">${viewMessage}</a>
	</jstl:forEach>
	</display:column>
	 </jstl:if>
</display:table>

<jstl:if test="${box.predefined==false }" >
<input type="button" name="edit" class="ui button"
 value="<spring:message code="box.edit" />"
 onclick="javascript: relativeRedir('box/edit.do?boxId=${box.id}');" />
 
 <input type="button" name="move" class="ui button"
 value="<spring:message code="box.move" />"
 onclick="javascript: relativeRedir('box/move.do?boxId=${box.id}');" />

</jstl:if>
<input type="button" name="back" class="ui button"
 value="<spring:message code="customer.back" />"
 onclick="javascript: relativeRedir('box/list.do');" />

 <input type="button" name="send" class="ui button"
 value="<spring:message code="message.send" />"
 onclick="javascript: relativeRedir('message/send.do');" />