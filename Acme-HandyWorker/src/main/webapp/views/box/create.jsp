<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="box/edit.do" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="messages" />
	<form:hidden path="predefined" />
	
	<div> 
		<form:label path="name">
			<spring:message code="box.name" />
		</form:label>
		<form:input path="name" />
		<form:errors path="name"/>
	</div>
	
	<div> 
		<form:label path="parentBox">
			<spring:message code="box.parentBox" />
		</form:label>
		<form:select path="parentBox">
			<jstl:forEach items="${boxes}" var="e">
				<form:option value="${e.id}">
					${e.name}
				</form:option>
			</jstl:forEach>
		</form:select>
		<form:errors path="parentBox"/>
	</div>
	
	<input type="submit" name="save" value="<spring:message code="box.save" />">
 	
	<input type="button" name="cancel" value="<spring:message code="box.cancel" />" onclick="javascript: relativeRedir('box/list.do')">
	
</form:form>