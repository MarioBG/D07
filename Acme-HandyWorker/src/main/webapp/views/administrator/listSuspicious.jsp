
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="actor.name" var="name"></spring:message>
<spring:message code="actor.surname" var="surname"></spring:message>
<spring:message code="actor.middleName" var="middleName"></spring:message>
<spring:message code="actor.email" var="email"></spring:message>
<spring:message code="actor.address" var="address"></spring:message>
<spring:message code="actor.phoneNumber" var="phoneNumber"></spring:message>
<spring:message code="actor.photo" var="photo"></spring:message>

<display:table name="actors" id="row"
	requestURI="administrator/listSuspicious.do" pagesize="5">
	<security:authorize access="hasRole('ADMINISTRATOR')">
	
	<display:column>
			<a href="administrator/ban.do?actorId=${row.id}"> <spring:message
					code="administrator.ban"></spring:message>
			</a>
		</display:column>
		<display:column property="name" titleKey="${name}"></display:column>
		<display:column property="surname" titleKey="${surname}"></display:column>
		<display:column property="middleName" titleKey="${middleName}"></display:column>
		<display:column property="email" titleKey="${email}"></display:column>
		<display:column property="address" titleKey="${address}"></display:column>
		<display:column property="phoneNumber" titleKey="${phoneNumber}"></display:column>
		<display:column property="photo" titleKey="${photo}"></display:column>
	</security:authorize>

</display:table>