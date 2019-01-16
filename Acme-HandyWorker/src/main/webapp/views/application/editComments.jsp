<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="application/administrator/editComments.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="offeredPrice" />
	<form:hidden path="applicationMoment" />
	<form:hidden path="status" />
	<form:hidden path="creditCard" />

	<div>
		<form:label path="comments">
			<spring:message code="application.comments" />
		</form:label>
		<form:input path="comments" />
		<form:errors path="comments"/>
	</div>

	<input type="submit" name="save"
		value="<spring:message code="application.save" />">

	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/list.do')">

</form:form>