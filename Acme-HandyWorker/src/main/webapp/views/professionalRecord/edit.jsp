
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="professionalRecord/ranger/edit.do" modelAttribute="professionalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="companyName">
		<spring:message code="professionalRecord.companyName" />:
	</form:label>
	<form:input path="companyName" />
	<form:errors cssClass="error" path="companyName" />
	<br/>
	<br/>
	
	<form:label path="startDate">
		<spring:message code="professionalRecord.startDate" />:
	</form:label>
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br/>
	<br/>
	
	<form:label path="endDate">
		<spring:message code="professionalRecord.endDate" />:
	</form:label>
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br/>
	<br/>
	
	<form:label path="playedRole">
		<spring:message code="professionalRecord.playedRole" />:
	</form:label>
	<form:input path="playedRole" />
	<form:errors cssClass="error" path="playedRole" />
	<br/>
	<br/>
	
	<form:label path="comment">
		<spring:message code="professionalRecord.comment" />:
	</form:label>
	<form:textarea path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br/>
	<br/>
	
	<form:label path="attachmentURL">
		<spring:message code="professionalRecord.attachmentURL" />:
	</form:label>
	<form:input path="attachmentURL" />
	<form:errors cssClass="error" path="attachmentURL" />
	<br/>

	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save" />" />&nbsp; 
	
	<jstl:if test="${professionalRecord.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="professionalRecord.delete" />" /> 
	</jstl:if>

</form:form>
