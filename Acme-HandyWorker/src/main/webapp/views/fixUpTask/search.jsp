<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<input type="text" onclic="search" name="search" placeholder="${search}">
<display:table name="fixUpTask" id="row"
	requestURI="fixUpTask/customer/list.do" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('HANDY WORKER')">
		<display:column>
			<a href="fixUpTask/customer/category.do"> <spring:message
					code="fixUpTask.category" />
			</a>
		</display:column>
		<display:column>
			<a href="fixUpTask/customer/warranty.do"> <spring:message
					code="fixUpTask.warranty" />
			</a>
		</display:column>
		<display:column>
			<a href="fixUpTask/customer/customer.do"> <spring:message
					code="fixUpTask.customer" />
			</a>
		</display:column>
	</security:authorize>
	
	<display:column property="ticker" title="${ticker}" />
	<display:column property="publicationMoment" title="${publicationMoment}" />
	<display:column property="description" titleKey="${description}" />
	<display:column property="address" titleKey="${address}" />
	<display:column property="maxPrice" titleKey="${maxPrice}" />
	<display:column property="startDate" titleKey="${startDate}" />
	<display:column property="endDate" titleKey="${endDate}" />

</display:table>