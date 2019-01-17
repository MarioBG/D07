<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<jstl:set value="<%=LocaleContextHolder.getLocale()%>" var="locale"></jstl:set>

<spring:message code="fixUpTask.publishedBy" var="publishedBy" />
<spring:message code="fixUpTask.ticker" var="ticker" />
<spring:message code="fixUpTask.publicationMoment" var="publicationMoment" />
<spring:message code="fixUpTask.publicationMoment" var="publicationMoment" />
<spring:message code="fixUpTask.description" var="description" />
<spring:message code="fixUpTask.address" var="address" />
<spring:message code="fixUpTask.maxPrice" var="maxPrice" />
<spring:message code="fixUpTask.startDate" var="startDate" />
<spring:message code="fixUpTask.endDate" var="endDate" />
<spring:message code="fixUpTask.category" var="category" />
<spring:message code="fixUpTask.warranty" var="warranty" />
<spring:message code="fixUpTask.phases" var="phases" />
<spring:message code="fixUpTask.complaints" var="complaints" />
<spring:message code="fixUpTask.view" var="view" />
<spring:message code="fixUpTask.application" var="application" />

<display:table name="list" id="row" requestURI="fixuptask/list.do" pagesize="3" class="displaytag">

	<security:authorize access="hasRole('HANDYWORKER')">
	<display:column title="${publishedBy}">
		<a href="customer/viewProfile.do?customerId=${customers[row.id].id}">${customers[row.id].userAccount.username}</a>
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column title="${application}">
			<a href="javascript:showDialog('view-applications', sucessApplications, 'q=${row.id}', 'fixuptask/async/aplications.do')">${view}</a>
		</display:column>
		<display:column property="category.name" title="${category}">
			<jstl:if test="${locale == 'en'}">${row.category.name}</jstl:if>
			<jstl:if test="${locale == 'es'}">${row.category.espName}</jstl:if>
		</display:column>
		<display:column property="warranty.title" title="${warranty}">
		</display:column>
		<display:column title="${phases}">
			<a href="javascript:showDialog('view-phases', sucessPhases, 'q=${row.id}', 'fixuptask/async/phases.do')">${view}</a>
		</display:column>
		<%-- <display:column title="${complaints}">
			<a href="javascript:showDialog('view-complaints', sucessComplaints, 'q=${row.id}', 'fixuptask/async/complaints.do')">${view}</a>
		</display:column> --%>
	</security:authorize>

	<display:column property="ticker" title="${ticker}" />
	<display:column property="publicationMoment" title="${publicationMoment}" />
	<display:column property="description" title="${description}" />
	<display:column property="address" title="${address}" />
	<display:column value="${row.maxPrice + row.maxPrice * vatPercent / 100}&euro; (${vatPercent}%)" title="${maxPrice}" />
	<display:column property="startDate" title="${startDate}" />
	<display:column property="endDate" title="${endDate}" />
	
	<display:column>
		<a href="fixuptask/edit.do?fixUpTaskId=${row.id}"> <spring:message code="fixUpTask.edit" />
		</a>
	</display:column>
	
</display:table>

<!-- <button onclick="window.location.href = '/createFixUpTask.do'">Crear </button> -->
<security:authorize access="hasRole('CUSTOMER')">
<input type="button" name="createFixUpTask" value="<spring:message code="fixUpTask.create" />" onclick="javascript: relativeRedir('fixuptask/create.do');" />
</security:authorize>

<div class="ui modal" id="view-phases" style="display: none">
	<div class="header">${phases}</div>
	<div class="content">
		<div id="view-phases-content" class="ui middle aligned selection list">
			
		</div>
	</div>
</div>

<div class="ui modal" id="view-complaints" style="display: none">
	<div class="header">${complaints}</div>
	<div class="content">
		<div id="view-complaints-content" class="ui middle aligned selection list">
			
		</div>
	</div>
</div>

<div class="ui modal" id="view-applications" style="display: none">
	<div class="header">${application}</div>
	<div class="content">
		<div id="view-applications-content" class="ui middle aligned selection list">
			
		</div>
	</div>
</div>

<script>
	function sucessPhases(data) {
		let container = $('#view-phases-content');
		container.html('');
		
		for(let e in data) {
			let icon = $(document.createElement('i')).addClass('angle double right icon');
			
			let header = $(document.createElement('div')).addClass('header').html(data[e].title);
			let content = $(document.createElement('div')).addClass('content').append(header);
			
			let item = $(document.createElement('div')).addClass('item').append(icon).append(content).on('click', function() {
				location.href = 'phase/view.do?phaseId=' + data[e].id;
			});
			
			container.append(item);
		}
	}
	
	function sucessComplaints(data) {
		let container = $('#view-complaints-content');
		container.html('');
		
		for(let e in data) {
			let icon = $(document.createElement('i')).addClass('angle double right icon');
			
			let header = $(document.createElement('div')).addClass('header').html(data[e].title);
			let content = $(document.createElement('div')).addClass('content').append(header);
			
			let item = $(document.createElement('div')).addClass('item').append(icon).append(content).on('click', function() {
				location.href = 'complaint/actor/view.do?id=' + data[e].id;
			});
			
			container.append(item);
		}
	}
	
	function sucessApplications(data) {
		let container = $('#view-applications-content');
		container.html('');
		
		for(let e in data) {
			let icon = $(document.createElement('i')).addClass('angle double right icon');
			
			let header = $(document.createElement('div')).addClass('header').html(data[e].title);
			let content = $(document.createElement('div')).addClass('content').append(header);
			
			let item = $(document.createElement('div')).addClass('item').append(icon).append(content).on('click', function() {
				location.href = 'application/view.do?applicationId=' + data[e].id;
			});
			
			container.append(item);
		}
	}
</script>

