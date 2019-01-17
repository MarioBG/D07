<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script type="text/javascript">

$(document).ready(function() {
	 $("#formID").submit(function(){
	var m = document.getElementById("phone").value;
	if(m != ""){
	var expreg = /^(\+\d{1,3})?\s(\(\d{3}\))?\s?\d{4,100}$/;
	
	if(!expreg.test(m)){
		
		return confirm("Are you sure you want to save this phone?");
	}
	}
});
});

</script>

<form:form id="formID" action="curriculum/ranger/edit.do" modelAttribute="curriculum" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	
	<%-- MODIFICANDO LOS VALORES DEL PERSONAL RECORD, SOLO EXISTE UN PERSONAL RECORD --%>

	<b><spring:message code="curriculum.personalRecord"></spring:message></b>
	
	<br/>
	<br/>
	
	<form:label path="personalRecord.fullName">
		<spring:message code="curriculum.personalRecord.fullName" />:
	</form:label>
	<form:input path="personalRecord.fullName" />
	<form:errors cssClass="error" path="personalRecord.fullName" />
	<br/>
	<br/>
	
	<form:label path="personalRecord.photoUrl">
		<spring:message code="curriculum.personalRecord.photoUrl" />:
	</form:label>
	<form:input path="personalRecord.photoUrl" />
	<form:errors cssClass="error" path="personalRecord.photoUrl" />
	<br/>
	<br/>
	
	<form:label path="personalRecord.email">
		<spring:message code="curriculum.personalRecord.email" />:
	</form:label>
	<form:input path="personalRecord.email" />
	<form:errors cssClass="error" path="personalRecord.email" />
	<br/>
	<br/>
	
	<form:label path="personalRecord.phoneNumber">
		<spring:message code="curriculum.personalRecord.phoneNumber" />:
	</form:label>
	<form:input id="phone" path="personalRecord.phoneNumber" />
	<form:errors cssClass="error" path="personalRecord.phoneNumber" />
	<br/>
	<br/>
	
	<form:label path="personalRecord.linkedInProfileUrl">
		<spring:message code="curriculum.personalRecord.linkedInProfileUrl" />:
	</form:label>
	<form:input path="personalRecord.linkedInProfileUrl" />
	<form:errors cssClass="error" path="personalRecord.linkedInProfileUrl" />
	<br/>
	<br/>
	

	<input type="submit" name="save"
		value="<spring:message code="curriculum.save" />" />&nbsp;

	<jstl:if test="${curriculum.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="curriculum.delete" />"
		onclick="return confirm('<spring:message code="curriculum.confirm.delete" />')" />
	</jstl:if>

</form:form>
