<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="view" tagdir="/WEB-INF/tags"%>

<jstl:set value="<%=LocaleContextHolder.getLocale()%>" var="locale"></jstl:set>

	<form:form action="fixuptask/edit.do" modelAttribute="fixUpTask">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="publicationMoment"/>
		<form:hidden path="ticker"/>
		<form:hidden path="applications"/>
		<form:hidden path="phases"/>
		<form:hidden path="complaints"/>
		
		<div> 
		<form:label path="description">
			<spring:message code="fixUpTask.description" />
		</form:label>
		<form:input path="description" />
		<form:errors path="description"/>
		</div>
		
		<div> 
		<form:label path="address">
			<spring:message code="fixUpTask.address" />
		</form:label>
		<form:input path="address" />
		<form:errors path="address"/>
		</div>
		
		<div> 
		<form:label path="maxPrice">
			<spring:message code="fixUpTask.maxPrice" />
		</form:label>
		<form:input path="maxPrice" />
		<form:errors path="maxPrice"/>
		</div>
		
		<div> 
		<form:label path="startDate">
			<spring:message code="fixUpTask.startDate" />
		</form:label>
		<form:input path="startDate" />
		<form:errors path="startDate"/>
		</div>
		
		<div> 
		<form:label path="endDate">
			<spring:message code="fixUpTask.endDate" />
		</form:label>
		<form:input path="endDate" />
		<form:errors path="endDate"/>
		</div>
		
		<form:label path="category.name">
			<spring:message code="fixUpTask.category" />
		</form:label>
		<form:select path="category">
			<jstl:forEach items="${categories}" var="e">
				<form:option value="${e.id}">
					<jstl:if test="${locale == 'en'}">${e.name}</jstl:if>
					<jstl:if test="${locale == 'es'}">${e.espName}</jstl:if>
				</form:option>
			</jstl:forEach>
		</form:select>
		<form:errors path="category.name"/>
		
		<form:label path="warranty">
			<spring:message code="fixUpTask.warranty" />
		</form:label>
		<form:select path="warranty">
			<jstl:forEach items="${warranties}" var="e">
				<form:option value="${e.id}">
					${e.title}
				</form:option>
			</jstl:forEach>
		</form:select>
		<form:errors path="warranty.title"/>
		
		<input type="submit" name="save" value="<spring:message code="fixUpTask.save" />">
 
 <input type="button" name="cancel"
 value="<spring:message code="fixUpTask.cancel" />"
 onclick="javascript: relativeRedir('fixuptask/list.do');" />
	</form:form>
</html>