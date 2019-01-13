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
							<spring:message code="sponsor.profile.title"/> ${actor.name}
						</div>
				</h4>
			</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><spring:message code="sponsor.name"/>
			<td data-label="name">${actor.name}</td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.middleName"/>
			<td data-label="MiddleName">${actor.middleName}</td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.surname"/>
			<td data-label="surname">${actor.surname}</td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.email"/>
			<td data-label="email">${actor.email}</td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.phoneNumber"/>
			<td data-label="phoneNumber">${actor.phoneNumber}</td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.address"/>
			<td data-label="address">${actor.address}</td>
		</tr>
	</tbody>
</table>

<input type="button" name="save" class="ui button"
 value="<spring:message code="sponsor.modify" />"
 onclick="javascript: relativeRedir('sponsor/edit.do');" />

</body>
</html>