

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="miscellaneousRecord/ranger/edit.do" modelAttribute="miscellaneousRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="title">
		<spring:message code="miscellaneousRecord.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br/>
	<br/>
	
	<form:label path="comment">
		<spring:message code="miscellaneousRecord.comment" />:
	</form:label>
	<form:textarea path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br/>
	<br/>
	
	<form:label path="attachmentURL">
		<spring:message code="miscellaneousRecord.attachmentURL" />:
	</form:label>
	<form:input path="attachmentURL" />
	<form:errors cssClass="error" path="attachmentURL" />
	<br/>
	<br/>


	<input type="submit" name="save"
		value="<spring:message code="miscellaneousRecord.save" />" />&nbsp;
	
	<jstl:if test="${miscellaneousRecord.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="miscellaneousRecord.delete" />" /> 
	</jstl:if>

</form:form>