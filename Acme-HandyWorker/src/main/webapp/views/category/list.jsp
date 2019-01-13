<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set value="<%=LocaleContextHolder.getLocale() %>" var="locale"></jstl:set>

<spring:message code="category.name" var="categoryName" />
<spring:message code="category.espName" var="categoryEspName" />
<spring:message code="category.parentCategory" var="categoryParentCategory" />
<spring:message code="category.delete" var="categoryDelete" />
<spring:message code="category.edit" var="categoryEdit" />


<display:table pagesize="3" class="displaytag" keepStatus="true"
	name="list" requestURI="/category/list.do" id="row">
	
	<display:column value="${row.name}" title="${categoryName}"></display:column>
	<display:column value="${row.espName}" title="${categoryEspName}"></display:column>
	<display:column title="${categoryParentCategory}">
		<jstl:if test="${locale == 'en'}">${row.parentCategory.name}</jstl:if>
		<jstl:if test="${locale == 'es'}">${row.parentCategory.espName}</jstl:if>
	</display:column>
	
	<display:column>
		<a href="category/administrator/edit.do?categoryId=${row.id}">${categoryEdit}</a>
	</display:column>
</display:table>

<input type="button" class="ui button" name="create"
		value="<spring:message code="category.create" />"
		onclick="javascript: relativeRedir('category/administrator/create.do');">