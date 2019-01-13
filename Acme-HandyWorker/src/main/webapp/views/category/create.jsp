<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set value="<%=LocaleContextHolder.getLocale() %>" var="locale"></jstl:set>

<form:form action="category/administrator/edit.do" modelAttribute="category">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<div> 
		<form:label path="name">
			<spring:message code="category.name" />
		</form:label>
		<form:input path="name" />
		<form:errors path="name"/>
	</div>
	
	<div> 
		<form:label path="espName">
			<spring:message code="category.espName" />
		</form:label>
		<form:input path="espName" />
		<form:errors path="espName"/>
	</div>

	<div> 
		<form:label path="parentCategory">
			<spring:message code="category.parentCategory" />
		</form:label>
		<form:select path="parentCategory">
			<jstl:forEach items="${categories}" var="e">
				<form:option value="${e.id}">
					<jstl:if test="${locale == 'en'}">${e.name}</jstl:if>
					<jstl:if test="${locale == 'es'}">${e.espName}</jstl:if>
				</form:option>
			</jstl:forEach>
		</form:select>
		<form:errors path="parentCategory"/>
	</div>
	
	<input type="submit" name="save" value="<spring:message code="category.save" />">
 	
	<input type="button" name="cancel" value="<spring:message code="category.cancel" />" onclick="javascript: relativeRedir('category/list.do')">
	
</form:form>