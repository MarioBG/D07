<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<head>
	<spring:message code="actor.confirmPhone" var="phoneConfirm"/>
	<script>
		$(document).ready(function() {
		 $("#formID").submit(function(){
			var m = document.getElementById("phone").value;
			var expreg = /^(\+\d{1,3})?\s?(\(\d{3}\)\s)?\s?\d{4,100}$/;
			
			if(!expreg.test(m)){
				
				return confirm($('#phoneConfirm').val());
			}
		});
		});
	</script>
</head>

<input id="phoneConfirm" type="hidden" value="${phoneConfirm}"/>

<form:form action="handyWorker/edit.do" id="formID" modelAttribute="handyWorker">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="suspicious" />
	<form:hidden path="userAccount" />
	<form:hidden path="socialIdentity" />
	<form:hidden path="boxes" />
	<form:hidden path="endorsements" />
	<form:hidden path="applications" />
	<form:hidden path="tutorials" />
	<form:hidden path="curriculum" />
	<form:hidden path="finder" />
	<form:hidden path="make" />
	
	<form:hidden path="userAccount.authorities" />
	
	<div class="ui equal width form">
	<div class="form-group"> 
		<form:label path="userAccount.username">
			<spring:message code="handyWorker.userAccount.username" />
		</form:label>
		<form:input class="form-control" path="userAccount.username" />
		<form:errors class="error" path="userAccount.username" />
	</div>
	<div class="form-group"> 
		<form:label path="userAccount.password">
			<spring:message code="handyWorker.userAccount.password" />
		</form:label>
		<form:password class="form-control" path="userAccount.password" />
		<form:errors class="error" path="userAccount.password" />
	</div>

	
		<div class="fields">
			<!-- Name -->
			<div class="field">
				<form:label path="name">
					<spring:message code="handyWorker.name" />
				</form:label>
				<form:input class="form-control" path="name" />
				<form:errors class="error" path="name" />
			</div>
			<!-- MiddleName -->
			<div class="field">
				<form:label path="middleName">
					<spring:message code="handyWorker.middleName" />
				</form:label>
				<form:input class="form-control" path="middleName" />
				<form:errors class="error" path="middleName" />
			</div>
			<!-- Surname -->
			<div class="field">
				<form:label path="surname">
					<spring:message code="handyWorker.surname" />
				</form:label>
				<form:input class="form-control" path="surname" />
				<form:errors class="error" path="surname" />
			</div>
		</div>
		
		
		<div class="fields">
			<!-- Email -->
			<div class="field">
				<form:label path="email">
					<spring:message code="handyWorker.email" />
				</form:label>
				<form:input placeholder="${actor.email}" path="email" />
				<form:errors class="error" path="email" />
			</div>
			<!-- Phone Number -->
			<div class="field">
				<form:label path="phoneNumber">
					<spring:message code="handyWorker.phoneNumber" />
				</form:label>
				<form:input placeholder="${actor.phoneNumber}" id="phone" path="phoneNumber" />
				<form:errors class="error" path="phoneNumber" />
			</div>
		</div>
		<div class="fields">
			<!-- Address -->
			<div class="field">
				<form:label path="address">
					<spring:message code="handyWorker.address" />
				</form:label>
				<form:input class="form-control" path="address" />
				<form:errors class="error" path="address" />
			</div>
			<!-- Photo -->
			<div class="field">
				<form:label path="photo">
					<spring:message code="handyWorker.photo" />
				</form:label>
				<form:input class="form-control" path="photo" />
				<form:errors class="error" path="photo" />
			</div>
		</div>

	<input type="submit" class="ui primary button" name="save"
			value="<spring:message code="handyWorker.save" />">

	<input type="button" class="ui button" name="cancel"
		value="<spring:message code="handyWorker.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');">
</div>
</form:form>