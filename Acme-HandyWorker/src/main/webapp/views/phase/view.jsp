<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<body>

<spring:message code="phase.title" var="title" />
<spring:message code="phase.description" var="description" />
<spring:message code="phase.startMoment" var="startMoment" />
<spring:message code="phase.endMoment" var="endMoment" />
		
		<h3><jstl:out value="${phase.title}"/></h3>
		
		<b>${description}:&nbsp;</b>
		<jstl:out value="${phase.description}"/>
		<br />
		
		<b>${startMoment}:&nbsp;</b>
		<jstl:out value="${phase.startMoment}"/>
		<br />
		
		<b>${endMoment}:&nbsp;</b>
		<jstl:out value="${phase.endMoment}"/>
		<br />
	
</body>
</html>