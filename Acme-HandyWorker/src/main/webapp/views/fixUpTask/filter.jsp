<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

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
<spring:message code="fixUpTask.pattern" var="pattern" />
<spring:message code="fixUpTask.filterStartDate" var="filterStartDate" />
<spring:message code="fixUpTask.filterEndDate" var="filterEndDate" />
<spring:message code="fixUpTask.minPrice" var="minPrice" />

<table style="border: none">
	<tr>
	<td><input name="command" type="text" placeholder="${pattern}"/></td>
		<td><input name="startDate"  type="text" placeholder="${filterStartDate}"/></td>
		<td><input name="endDate"  type="text" placeholder="${filterEndDate}"/></td>
		<td><input name="maxPrice"  type="number" placeholder="${maxPrice}"/></td>
		<td><input name="minPrice"  type="number" placeholder="${minPrice}"/></td>
		<td><button id="search">Buscar</button></td>
	</tr>
	<tr>
		<td colspan="5">
			<display:table name="data" id="row" requestURI="fixuptask/filter.do" pagesize="3" class="displaytag">
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
				<display:column title="${complaints}">
					<a href="javascript:showDialog('view-complaints', sucessComplaints, 'q=${row.id}', 'fixuptask/async/complaints.do')">${view}</a>
				</display:column>
			</security:authorize>
		
			<display:column property="ticker" title="${ticker}" />
			<display:column property="publicationMoment" title="${publicationMoment}" />
			<display:column property="description" title="${description}" />
			<display:column property="address" title="${address}" />
			<display:column property="maxPrice" title="${maxPrice}" />
			<display:column property="startDate" title="${startDate}" />
			<display:column property="endDate" title="${endDate}" />
			
			<display:column>
				<a href="fixuptask/edit.do?fixuptaskId=${row.id}"> <spring:message code="fixUpTask.edit" />
				</a>
			</display:column>
			
		</display:table>
		</td>
	</tr>
</table>

<script>
	(function() {
		let search = $('#search').on(
				'click',
				function() {
					let s_command = $('[name="command"]').val().trim();
					let s_startDate = $('[name="startDate"]').val().trim();
					let s_endDate = $('[name="endDate"]').val().trim();
					let s_maxPrice = $('[name="maxPrice"]').val().trim();
					let s_minPrice = $('[name="minPrice"]').val().trim();

					let params = '?';
					
					if(s_command.length > 0) {
						params += 'command=' + s_command + '&'
					}
					
					if(s_startDate.length > 0) {
						params += 'startDate=' + s_startDate + '&'
					}
					
					if(s_endDate.length > 0) {
						params += 'endDate=' + s_endDate + '&'
					}
					
					if(s_maxPrice.length > 0) {
						params += 'maxPrice=' + s_maxPrice + '&'
					}
					
					if(s_minPrice.length > 0) {
						params += 'minPrice=' + s_minPrice + '&'
					}
					
					params = params.substring(0, params.length - 1);

					location.href = 'fixuptask/filter.do' + params;
				});
		})();
</script>