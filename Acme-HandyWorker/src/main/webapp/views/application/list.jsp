<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:message code="application.status" var="applicationMoment" />
<spring:message code="application.status" var="applicationStatus" />
<spring:message code="application.offeredPrice" var="applicationOfferedPrice" />
<spring:message code="application.comments" var="applicationComments" />
<spring:message code="application.creditCard" var="applicationCreditCard" />
<spring:message code="application.fixUpTask" var="applicationFixUpTask" />
<spring:message code="application.handyWorker" var="applicationHandyWorker" />
<spring:message code="application.view" var="applicationView" />

<style type="text/css">

.pending{background-color: white;}
.pendingLessThanAMonth{background-color: grey;}
.accepted{background-color: green; color: white}
.accepted a{color: white;}
.rejected{background-color: orange;}
.due{background-color: yellow;}
.cancelled{background-color: cyan;}

</style>

<jsp:useBean id="now" class="java.util.Date"/>
	<fmt:parseNumber var="dateDifference"
    value="${(now.time - application.fixUpTask.endDate.time) / (1000*60*60*24) }"
    integerOnly="true" />

<display:table pagesize="3" class="displaytag" keepStatus="true"
	name="applications" requestURI="/application/list.do" id="row">
	
	<jstl:choose>
	
		<jstl:when test="${row.status eq 'PENDING' and dateDifference < 0}">
			<jstl:set value="pendingLessThanAMonth" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'ACCEPTED'}">
			<jstl:set value="accepted" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'DUE'}">
			<jstl:set value="due" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'REJECTED'}">
			<jstl:set value="rejected" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'CANCELLED'}">
			<jstl:set value="cancelled" var="style"/>
		</jstl:when>
		<jstl:otherwise><jstl:set value="" var="style"/></jstl:otherwise>
		
	</jstl:choose>
	
	<display:column value="${row.applicationMoment}" title="${applicationApplicationMoment}"></display:column>
	<display:column class="${style}" value="${row.status}" title="${applicationStatus}"></display:column>
	<display:column value="${row.offeredPrice + row.offeredPrice * vatPercent / 100}&euro; (${vatPercent}%)" title="${applicationOfferedPrice}"></display:column>
	<display:column value="${row.creditCard.number}" title="${applicationCreditCard}"></display:column>
	<display:column value="${row.fixUpTask.description}" title="${applicationFixUpTask}"></display:column>
	<display:column title="${applicationHandyWorker}"><a href="handyWorker/viewProfile.do?handyWorkerId=${row.handyWorker.id}">${row.handyWorker.userAccount.username}</a></display:column>
	
	<display:column>
			<a href="application/view.do?applicationId=${row.id}">${applicationView}</a>
	</display:column>
	
</display:table>