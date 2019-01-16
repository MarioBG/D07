<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>
<html>
<spring:message code="configuration.bannerURL" var="bannerURL" />
<spring:message code="configuration.spamWords" var="spamWords" />
<spring:message code="configuration.goodWords" var="goodWords" />
<spring:message code="configuration.badWords" var="badWords" />
<spring:message code="configuration.VATTax" var="VATTax" />
<spring:message code="configuration.countryCode" var="countryCode" />
<spring:message code="configuration.finderCached" var="finderCached" />
<spring:message code="configuration.finderReturn" var="finderCached" />
<spring:message code="configuration.systemName" var="systemName" />
<spring:message code="configuration.defaultCreditCards" var="defaultCreditCards" />
<spring:message code="configuration.welcomeMessage" var="welcomeMessage" />

	<form:form action="configuration/edit.do" modelAttribute="configuration">
		<form:hidden path="id" />
		<form:hidden path="version" />

		<div class="ui equal width form">

		<form:input path="systemName" placeholder="${systemName }" />
		<form:errors cssClass="error" path="systemName"></form:errors>
		
		<form:input path="welcomeMessage" placeholder="${welcomeMessage }" />
		<form:errors cssClass="error" path="welcomeMessage.content"></form:errors>

		<form:input path="bannerURL" placeholder="${bannerURL }" />
		<form:errors cssClass="error" path="bannerURL"></form:errors>

		<form:input path="spamWords" placeholder="${spamWords }" />
		<form:errors cssClass="error" path="spamWords"></form:errors>

		<form:input path="goodWords" placeholder="${goodWords }" />
		<form:errors cssClass="error" path="goodWords"></form:errors>

		<form:input path="badWords" placeholder="${badWords }" />
		<form:errors cssClass="error" path="badWords"></form:errors>
		
		<form:input path="VATTax" placeholder="${VATTax }" />
		<form:errors cssClass="error" path="VATTax"></form:errors>
		
		<form:input path="countryCode" placeholder="${countryCode }" />
		<form:errors cssClass="error" path="countryCode"></form:errors>
		
			<form:input path="finderCached" placeholder="${finderCached }" />
		<form:errors cssClass="error" path="finderCached"></form:errors>
		
		<form:input path="finderReturn" placeholder="${finderReturn }" />
		<form:errors cssClass="error" path="finderReturn"></form:errors>
		
		<form:input path="defaultCreditCards" placeholder="${defaultCreditCards }" />
		<form:errors cssClass="error" path="defaultCreditCards"></form:errors>

		</div>
	

<input type="submit" name="save" class="ui primary button"
	value="<spring:message code="configuration.save" />"
	onclick="javascript: relativeRedir('configuration/view.do');" />

<input type="button" name="cancel" class="ui button"
	value="<spring:message code="configuration.cancel" />"
	onclick="javascript: relativeRedir('configuration/view.do');" />
	
</form:form>

	
</html>