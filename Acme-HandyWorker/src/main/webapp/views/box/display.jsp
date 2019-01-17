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
<p><spring:message code="box.current"/>: ${box.name }</p>
<p><jstl:if test="${not empty box.parentBox}"><spring:message code="box.parentBox"/>: ${box.parentBox.name}</jstl:if></p>
<h3><spring:message code="message.messages"/></h3>
<display:table name="box.messages" id="row" requestURI="box/display.do" class="displaytag">
	<display:column title="${priority}">
		${row.priority }
	</display:column>
	<display:column title="${moment}">
		${row.moment }
	</display:column>
	<display:column title="${subject}">
		${row.subject }
	</display:column>
	<display:column>
		<a href="message/display.do?messageId=${row.id}">${viewMessage}</a>
	</display:column>
	<display:column>
		<a href="message/delete.do?messageId=${row.id}">${deleteMessage}</a>
	</display:column>
</display:table>
<jstl:if test="${!box.predefined}">
	<input type="button" name="edit" class="ui button"
	 value="<spring:message code="box.edit" />"
	 onclick="javascript: relativeRedir('box/edit.do?boxId=${box.id}');" />
	 
	 <input type="button" name="delete" class="ui button"
	 value="<spring:message code="box.delete" />"
	 onclick="javascript: relativeRedir('box/delete.do?boxId=${box.id}');" />
</jstl:if>
<input type="button" name="back" class="ui button"
 value="<spring:message code="customer.back" />"
 onclick="javascript: relativeRedir('box/list.do');" />
</html>