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
<jstl:choose>
	<jstl:when test="${not empty handyWorker}">
		<table
			class="ui celled table <jstl:if test='${handyWorker.suspicious}'>red</jstl:if>">
			<thead>
				<tr>
					<th colspan="2">
						<h4 class="ui image header">
							<img src="${handyWorker.photo}" class="ui mini rounded image">
							<div class="content">
									<spring:message code="handyWorker.profile.title"/> ${handyWorker.name}
								</div>
						</h4>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><spring:message code="handyWorker.name" />
					<td data-label="name">${handyWorker.name}</td>
				</tr>
				<tr>
					<td><spring:message code="handyWorker.middleName" />
					<td data-label="MiddleName">${handyWorker.middleName}</td>
				</tr>
				<tr>
					<td><spring:message code="handyWorker.surname" />
					<td data-label="surname">${handyWorker.surname}</td>
				</tr>
				<tr>
					<td><spring:message code="handyWorker.email" />
					<td data-label="email">${handyWorker.email}</td>
				</tr>
				<tr>
					<td><spring:message code="handyWorker.phoneNumber" />
					<td data-label="phoneNumber">${handyWorker.phoneNumber}</td>
				</tr>
				<tr>
					<td><spring:message code="handyWorker.address" />
					<td data-label="address">${handyWorker.address}</td>
				</tr>
				<tr>
					<td><spring:message code="handyWorker.make" />
					<td data-label="make">${handyWorker.make}</td>
				</tr>
				<jstl:if test="${handyWorker.curriculum.id!=0}">
				<tr>
					<td><spring:message code="handyWorker.currilum.ticker" />
					<td>
						<a href="handyWorker/viewCurriculum.do">
							<div style="height:100%;width:100%">
						 		${handyWorker.curriculum.ticker}
						 	</div>
						 </a>
					</td>
				</tr>
				</jstl:if>
			</tbody>
		</table>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<input type="button" name="save" class="ui button"
			 value="<spring:message code="handyWorker.modify" />"
			 onclick="javascript: relativeRedir('handyWorker/edit.do');" />
		</security:authorize>
	 </jstl:when>
	 <jstl:otherwise>
	 	<h3><spring:message code="handyWorker.notfound"/></h3>
	 	<h4><a href="javascript:history.back()"><spring:message code="handyWorker.backToList"/></a></h4>
	 </jstl:otherwise>
 </jstl:choose>

</body>
</html>
