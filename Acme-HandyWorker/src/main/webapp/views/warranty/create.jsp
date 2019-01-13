<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="warranty/administrator/edit.do" modelAttribute="warranty">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<div> 
		<form:label path="title">
			<spring:message code="warranty.title" />
		</form:label>
		<form:input path="title" />
		<form:errors path="title"/>
	</div>
	
	<div> 
		<form:label path="terms">
			<spring:message code="warranty.terms" />
		</form:label>
		<form:input path="terms" />
		<form:errors path="terms"/>
	</div>

	<div> 
		<form:label path="applicableLaws">
			<spring:message code="warranty.applicableLaws" />
		</form:label>
		<form:input path="applicableLaws" />
		<form:errors path="applicableLaws"/>
	</div>
	
	<div> 
		<form:label path="finalMode">
			<spring:message code="warranty.finalMode" />
		</form:label>
		<form:radiobutton path="finalMode" value="true"/><spring:message code="warranty.finalModeTrue" />
		<form:radiobutton path="finalMode" value="false"/><spring:message code="warranty.finalModeFalse" />
	</div>
	
	<input type="submit" name="save" value="<spring:message code="warranty.save" />">
 	
	<input type="button" name="cancel" value="<spring:message code="warranty.cancel" />" onclick="javascript: relativeRedir('warranty/list.do')">
	
</form:form>