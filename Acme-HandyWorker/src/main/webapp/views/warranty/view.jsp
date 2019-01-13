<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<spring:message code="warranty.title" var="title" />
<spring:message code="warranty.terms" var="terms" />
<spring:message code="warranty.applicableLaws" var="applicableLaws" />
<spring:message code="warranty.finalMode" var="finalMode" />

	<display:column property="title" title="${title}" />
	<display:column property="terms" title="${terms}" />
	<display:column property="applicableLaws" titleKey="${applicableLaws}" />
	<display:column property="finalMode" titleKey="${finalMode}" />

</html>

 
