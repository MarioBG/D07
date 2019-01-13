<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<spring:message code="phase.title" var="title" />
<spring:message code="phase.description" var="description" />
<spring:message code="phase.startMoment" var="startMoment" />
<spring:message code="phase.endMoment" var="endMoment" />

<display:table name="phases" id="row"
	requestURI="phase/customer/list.do" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="phase/customer/edit.do?phaseId=${row.id}"> <spring:message
					code="phase.edit" />
			</a>
		</display:column>
		<%-- <display:column>
			<a href="phase/customer/list.do?phaseId=${row.id}"> <spring:message
					code="phase.delete" />
			</a>
		</display:column> --%>
	</security:authorize>
	
	<display:column property="title" title="${title}" />
	<display:column property="description" title="${description}" />
	<display:column property="startMoment" titleKey="${startMoment}" />
	<display:column property="endMoment" titleKey="${endMoment}" />

</display:table>

<input type="button" name="create"
 value="<spring:message code="phase.create" />"
 onclick="javascript: relativeRedir('phase/createPhase.do');" />
 
