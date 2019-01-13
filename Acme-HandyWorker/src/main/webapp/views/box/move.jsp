<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form id="submit-form" class="ui form" action="box/move.do" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="predefined" />
	<form:hidden path="messages" />
	
	<div class="two fields">
	<div class="field"> 
		<form:label path="parentBox">
			<spring:message code="box.parentBoxName" />
		</form:label>
		<form:select id="selected-box" class="ui fluid dropdown" path="parentBox" itemValue="parentBox.id">
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
 	
	<input type="button" class="ui button" name="cancel" value="<spring:message code="box.cancel" />" onclick="javascript: relativeRedir('box/list.do')">
	
</form:form>
<script>
	let q = $('#selected-box').val();
	let action = $('#submit-form').attr('action');
	let srcId = ${box.id};
	
	$('#submit-form').submit(function() {
		let action = $('#submit-form').attr('action');
		$('#submit-form').attr('action', action + '&src=' + srcId);
		
	    return true;
	});

	$('#submit-form').attr('action', action + '?dst=' + q);
	
	$('#selected-box').on('change', function() {
		let action = $('#submit-form').attr('action').split('\?')[0];
		let autherq = $(this).val();
		
		$('#submit-form').attr('action', action + '?dst=' + autherq);
	});
</script>