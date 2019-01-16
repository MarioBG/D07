<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="tutorial.create" /></title>
</head>
<body>
<form:form modelAttribute="tutorial" action="tutorial/handyworker/create.do">
		<!-- Title -->
			 <form:label path="title">
				<spring:message code="tutorial.title" />
			</form:label>
			<form:input path="title" />
			<form:errors path="title" />

		<!-- Update Time -->
			<form:label path="updateTime">
				<spring:message code="tutorial.updateTime" />
			</form:label>
			<form:input path="updateTime" />
			<form:errors path="updateTime" />

		<!-- Summary -->
			<form:label path="summary">
				<spring:message code="tutorial.summary" />
			</form:label>
			<form:input path="summary" />
			<form:errors path="summary" />

		<!-- Pictures -->
		<form:label path="pictures">
				<spring:message code="tutorial.pictures" />
			</form:label>
		<form:textarea path="pictures" />
			<form:errors path="pictures" /> 

	</form:form>
	
	<input type="button" name="save"
	value="<spring:message code="tutorial.save" />"
	onclick="javascript: relativeRedir('tutorial/viewTutorial.do');" />
	
	<input type="button" name="cancel"
	value="<spring:message code="tutorial.cancel" />"
	onclick="javascript: relativeRedir('tutorial/viewTutorial.do');" />

</body>
</html>