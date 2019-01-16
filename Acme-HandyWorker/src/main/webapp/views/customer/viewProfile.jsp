<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<table
	class="ui celled table <jstl:if test='${actor.suspicious}'>red</jstl:if>">
	<thead>
		<tr>
			<th colspan="2">
				<h4 class="ui image header">
					<img src="${actor.photo}" class="ui mini rounded image">
					<div class="content">
							<spring:message code="customer.profile.title"/> ${actor.name}
						</div>
				</h4>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><spring:message code="customer.name" />
			<td data-label="name">${actor.name}</td>
		</tr>
		<tr>
			<td><spring:message code="customer.middleName" />
			<td data-label="MiddleName">${actor.middleName}</td>
		</tr>
		<tr>
			<td><spring:message code="customer.surname" />
			<td data-label="surname">${actor.surname}</td>
		</tr>
		<tr>
			<td><spring:message code="customer.email" />
			<td data-label="email">${actor.email}</td>
		</tr>
		<tr>
			<td><spring:message code="customer.phoneNumber" />
			<td data-label="phoneNumber">${actor.phoneNumber}</td>
		</tr>
		<tr>
			<td><spring:message code="customer.address" />
			<td data-label="address">${actor.address}</td>
		</tr>
	</tbody>


<security:authorize access="hasRole('CUSTOMER')">
<input type="button" name="save" class="ui button"
 value="<spring:message code="customer.modify" />"
 onclick="javascript: relativeRedir('customer/edit.do');" />
 
 <a class="ui button" href="fixuptask/listCustomer.do?customerId=${actor.id}"><spring:message code="customer.fix"/></a>
 
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">

<a class="ui button" href="fixuptask/listCustomer.do?customerId=${actor.id}"><spring:message code="fixuptask.viewCustomer"/></a>

<input type="button" name="back" class="ui button"
 value="<spring:message code="customer.back" />"
 onclick="javascript: relativeRedir('welcome/index.do');" />
</security:authorize>

</table>
</body>
</html>