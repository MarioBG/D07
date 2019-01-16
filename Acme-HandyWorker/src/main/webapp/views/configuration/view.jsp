<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<spring:message code="configuration.bannerURL" var="bannerURL" />
<spring:message code="configuration.spamWords" var="spamWords" />
<spring:message code="configuration.goodWords" var="goodWords" />
<spring:message code="configuration.badWords" var="badWords" />
<spring:message code="configuration.VATTax" var="VATTax" />
<spring:message code="configuration.countryCode" var="countryCode" />
<spring:message code="configuration.finderCached" var="finderCached" />
<spring:message code="configuration.finderReturn" var="finderReturn" />
<spring:message code="configuration.systemName" var="systemName" />
<spring:message code="configuration.defaultCreditCards" var="defaultCreditCards" />
<spring:message code="configuration.welcomeMessage" var="welcomeMessage" />

<display:table name="configuration" requestURI="/configuration/view.do">
<display:column property="systemName" title="${systemName}" />
<display:column property="bannerURL" title="${bannerURL}" />
<display:column property="welcomeMessage.content" title="${welcomeMessage}" />
<display:column property="spamWords" title="${spamWords}" />
<display:column property="goodWords" title="${goodWords}" />
<display:column property="badWords" title="${badWords}" />
<display:column property="VATTax" title="${VATTax}" />
<display:column property="countryCode" title="${countryCode}" />
<display:column property="finderCached" title="${finderCached}" />
<display:column property="finderReturn" title="${finderReturn}" />
<display:column property="defaultCreditCards" title="${defaultCreditCards}" />
</display:table>


<input type="button" class="ui button" name="edit"
		value="<spring:message code="configuration.edit" />"
		onclick="javascript: relativeRedir('configuration/edit.do');">
</html>
