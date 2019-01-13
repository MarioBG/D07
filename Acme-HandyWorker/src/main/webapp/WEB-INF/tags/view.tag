<%@tag import="java.lang.reflect.Field"%>
<%@tag import="domain.Actor"%>
<%@tag import="java.util.Enumeration"%>
<%@tag import="domain.DomainEntity"%>
<%@ tag language="java" body-content="empty" %>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%@ attribute name="entity" required="true" rtexprvalue="true" %>
<%@ attribute name="photoField" required="false" rtexprvalue="true" %>

<!-- definition of parameters -->
<%
	DomainEntity entity = (DomainEntity) request.getAttribute("entity");
	System.out.println(photoField);
	
	Class<?> clazz = entity.getClass();
	
	StringBuilder main = new StringBuilder();
	StringBuilder mainListContent = new StringBuilder();
	StringBuilder photoFieldValue = new StringBuilder();
%>
<!-- if is an actor show pretty table with data else show all attributes -->
<%
	if(entity instanceof Actor) {
		do{
			for(Field e : clazz.getDeclaredFields()) {
				e.setAccessible(true);
				
				if(e.getName().equalsIgnoreCase(photoField)) {
					photoFieldValue.append(String.format("<img class='ui small circular image' src='%s'>", e.get(entity)));
					continue;
				}
				
				if("id".equalsIgnoreCase(e.getName()) || "version".equalsIgnoreCase(e.getName())) {
					mainListContent.append(String.format("<input type='hidden' value='%s'>", e.get(entity)));
				} else if(String.class.isAssignableFrom(e.getType())) {
					mainListContent.append(String.format("<div class='item'><div class='header'>%s</div>%s</div>", e.getName(), e.get(entity)));
				} else if(Boolean.TYPE.isAssignableFrom(e.getType())) {
					//todo
				}
			}
		} while((clazz = clazz.getSuperclass()) != null);
		
		main.append(photoFieldValue);
		
		main.append("<div class='ui list'>");
		main.append(mainListContent);
		main.append("</div>");
%>

<% } else { %>

<%
	}
%>


<!-- print result -->
<%
	out.print(main);
%>