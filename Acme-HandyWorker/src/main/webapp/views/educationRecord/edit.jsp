
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="educationRecord/ranger/edit.do" modelAttribute="educationRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="diplomaTitle">
		<spring:message code="educationRecord.diplomaTitle" />:
	</form:label>
	<form:input path="diplomaTitle" />
	<form:errors cssClass="error" path="diplomaTitle" />
	<br/>
	<br/>
	
	<form:label path="startDate">
		<spring:message code="educationRecord.startDate" />:
	</form:label>
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br/>
	<br/>
	
	<form:label path="endDate">
		<spring:message code="educationRecord.endDate" />:
	</form:label>
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br/>
	<br/>
	
	<form:label path="institutionName">
		<spring:message code="educationRecord.institutionName" />:
	</form:label>
	<form:input path="institutionName" />
	<form:errors cssClass="error" path="institutionName" />
	<br/>
	<br/>
	
	<form:label path="comment">
		<spring:message code="educationRecord.comment" />:
	</form:label>
	<form:textarea path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br/>
	<br/>
	
	<form:label path="attachmentURL">
		<spring:message code="educationRecord.attachmentURL" />:
	</form:label>
	<form:input path="attachmentURL" />
	<form:errors cssClass="error" path="attachmentURL" />
	<br/>

	<input type="submit" name="save"
		value="<spring:message code="educationRecord.save" />" />&nbsp; 
	
	<jstl:if test="${educationRecord.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="educationRecord.delete" />" /> 
	</jstl:if>

</form:form>
