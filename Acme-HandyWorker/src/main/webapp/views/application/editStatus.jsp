<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="application/administrator/editStatus.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="offeredPrice" />
	<form:hidden path="applicationMoment" />
	<form:hidden path="comments" />
	<form:hidden path="creditCard" />

	<div>
		<form:label path="status">
			<spring:message code="application.status" />
		</form:label>
		<form:radiobutton path="status" value="ACCEPTED"/><spring:message code="application.statusAccepted" />
		<form:radiobutton path="status" value="REJECTED"/><spring:message code="application.statusRejected" />
	</div>
	
	<div>
		<a><spring:message code="application.statusAccepted.creditCard" /></a>
	</div>
	
	<div>
		<form:label path="creditCard.holderName">
			<spring:message code="application.creditCard.holderName" />
		</form:label>
		<form:input path="creditCard.holderName" />
		<form:errors path="creditCard.holderName" />
	</div>
	
	<<div>
		<form:label path="creditCard.brandName">
			<spring:message code="application.creditCard.brandName" />
		</form:label>
		<form:input path="creditCard.brandName" />
		<form:errors path="creditCard.brandName" />
	</div>
	
	<div>
		<form:label path="creditCard.number">
			<spring:message code="application.creditCard.number" />
		</form:label>
		<form:input path="creditCard.number" />
		<form:errors path="creditCard.number" />
	</div>
	
	<div>
		<form:label path="creditCard.expirationMonth">
			<spring:message code="application.creditCard.expirationMonth" />
		</form:label>
		<form:input path="creditCard.expirationMonth" />
		<form:errors path="creditCard.expirationMonth" />
	</div>
	
	<div>
		<form:label path="creditCard.expirationYear">
			<spring:message code="application.creditCard.expirationYear" />
		</form:label>
		<form:input path="creditCard.expirationYear" />
		<form:errors path="creditCard.expirationYear" />
	</div>
	
	<div>
		<form:label path="creditCard.cVV">
			<spring:message code="application.creditCard.cVV" />
		</form:label>
		<form:input path="creditCard.cVV" />
		<form:errors path="creditCard.cVV" />
	</div>
	
	<input type="submit" name="save"
		value="<spring:message code="application.save" />">

	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/list.do')">

</form:form>