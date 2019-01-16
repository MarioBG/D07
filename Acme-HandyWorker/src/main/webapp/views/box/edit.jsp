<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form class="ui form" action="box/edit.do" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="predefined" />
	<form:hidden path="messages" />
	
	<div class="two fields">
	<div class="field"> 
		<form:label path="name">
			<spring:message code="box.name" />
		</form:label>
		<form:input path="name" />
		<form:errors path="name"/>
	</div>
	
	<div class="field"> 
		<form:label path="parentBox">
			<spring:message code="box.parentBoxName" />
		</form:label>
		<form:select class="ui fluid dropdown" path="parentBox" itemValue="parentBox.id">
			<jstl:forEach items="${boxes}" var="e">
				<form:option value="${e.id}">
					${e.name}
				</form:option>
			</jstl:forEach>
		</form:select>
		<form:errors path="parentBox"/>
	</div>
	</div>
	
	<input type="submit" class="ui button" name="save" value="<spring:message code="box.save" />">

	<jstl:if test="${box.getId() != 0}">
		<input type="submit" class="ui button" name="delete" value="<spring:message code="box.delete" />">
 	</jstl:if>
 	
	<input type="button" class="ui button" name="cancel" value="<spring:message code="box.cancel" />" onclick="javascript: relativeRedir('box/list.do')">
	
</form:form>