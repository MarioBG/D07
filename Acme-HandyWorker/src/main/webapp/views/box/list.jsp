<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="box.name" var="boxName" />
<spring:message code="box.view" var="boxView" />
<spring:message code="box.delete" var="boxDelete" />
<spring:message code="box.edit" var="boxEdit" />

<display:table pagesize="4" class="displaytag" keepStatus="true"
	name="boxes" requestURI="/box/list.do" id="row">

		<display:column value="${row.name}" title="${boxName}"></display:column>

	<display:column>
			<a href="box/display.do?boxId=${row.id}">${boxView}</a>
	</display:column>

	
	
</display:table>

<input type="button" class="ui button" name="create"
		value="<spring:message code="box.create" />"
		onclick="javascript: relativeRedir('box/create.do');">