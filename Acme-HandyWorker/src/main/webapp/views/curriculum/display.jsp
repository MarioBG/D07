<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${empty curriculum}">
<a href="curriculum/ranger/create.do"><spring:message code="curriculum.create" /></a>
</jstl:if>

<jstl:if test="${not empty curriculum}">

<div id="curriculum" >


	<ul style="list-style-type:disc">
	
	
	<li>
	<b><spring:message code="curriculum.ticker"></spring:message>:</b>
	<jstl:out value="${curriculum.getTicker()}"/>
	</li>
	
	<br/>
	
	
	<%-- MOSTRAMOS LOS DATOS DEL PERSONAL RECORD --%>
	<u><b><spring:message code="curriculum.personalRecord"></spring:message></b></u>
	
	<br/>
	
	<li>
	<b><spring:message code="curriculum.personalRecord.fullName"></spring:message>:</b>
	<jstl:out value="${curriculum.getPersonalRecord().getFullName()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.personalRecord.photoUrl"></spring:message>:</b>
	<jstl:out value="${curriculum.getPersonalRecord().getPhotoUrl()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.personalRecord.email"></spring:message>:</b>
	<jstl:out value="${curriculum.getPersonalRecord().getEmail()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.personalRecord.phoneNumber"></spring:message>:</b>
	<jstl:out value="${curriculum.getPersonalRecord().getPhoneNumber()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.personalRecord.linkedInProfileUrl"></spring:message>:</b>
	<jstl:out value="${curriculum.getPersonalRecord().getLinkedInProfileUrl()}"/>
	</li>
	
	<br/>
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="edit"
		value="<spring:message code="personalRecord.edit" />"
		onclick="javascript: relativeRedir('personalRecord/ranger/edit.do?personalRecordId=${curriculum.getPersonalRecord().getId()}')" />
	
	<br/>
		<%-- MOSTRAMOS LOS DATOS DEL Educational RECORD --%>
	<jstl:if test="${not empty curriculum.getEducationRecords()}">
	<u><b><spring:message code="curriculum.educationRecord"></spring:message></b></u>
	<br/>
	<jstl:forEach var="educationRecord" items="${curriculum.getEducationRecords()}">
	
	<li>
	<b><spring:message code="curriculum.educationRecord.diplomeTitle"></spring:message>:</b>
	<jstl:out value="${educationRecord.getDiplomaTitle()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.educationRecord.startDate"></spring:message>:</b>
	<jstl:out value="${educationRecord.getStartDate()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.educationRecord.endDate"></spring:message>:</b>
	<jstl:out value="${educationRecord.getEndDate()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.educationRecord.institutionName"></spring:message>:</b>
	<jstl:out value="${educationRecord.getInstitutionName()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.educationRecord.comment"></spring:message>:</b>
	<jstl:out value="${educationRecord.getComment()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.educationRecord.attachmentURL"></spring:message>:</b>
	<jstl:out value="${educationRecord.getAttachmentURL()}"/>
	</li>

	<br/>
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="edit"
		value="<spring:message code="educationRecord.edit" />"
		onclick="javascript: relativeRedir('educationRecord/ranger/edit.do?educationRecordId=${educationRecord.getId()}')" />
	<br/>
	</jstl:if>
	</security:authorize>
	</jstl:forEach>
	</jstl:if>
	
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="create"
		value="<spring:message code="educationRecord.create" />"
		onclick="javascript: relativeRedir('educationRecord/ranger/create.do')" />
	</jstl:if>
	</security:authorize>
	<br/>
	<br/>
	<%-- MOSTRAMOS LOS DATOS DEL PROFESSIONAL RECORD --%>
	<jstl:if test="${not empty curriculum.getProfessionalRecords()}">
	<u><b><spring:message code="curriculum.professionalRecord"></spring:message></b></u>
	<br/>
	<jstl:forEach var="professionalRecord" items="${curriculum.getProfessionalRecords()}">
	
	<li>
	<b><spring:message code="curriculum.professionalRecord.companyName"></spring:message>:</b>
	<jstl:out value="${professionalRecord.getCompanyName()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.professionalRecord.startDate"></spring:message>:</b>
	<jstl:out value="${professionalRecord.getStartDate()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.professionalRecord.endDate"></spring:message>:</b>
	<jstl:out value="${professionalRecord.getEndDate()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.professionalRecord.playedRole"></spring:message>:</b>
	<jstl:out value="${professionalRecord.getPlayedRole()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.professionalRecord.comment"></spring:message>:</b>
	<jstl:out value="${professionalRecord.getComment()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.professionalRecord.attachmentURL"></spring:message>:</b>
	<jstl:out value="${professionalRecord.getAttachmentURL()}"/>
	</li>

	<br/>
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="edit"
		value="<spring:message code="professionalRecord.edit" />"
		onclick="javascript: relativeRedir('professionalRecord/ranger/edit.do?professionalRecordId=${professionalRecord.getId()}')" />
	<br/>
	</jstl:if>
	</security:authorize>
	</jstl:forEach>
	</jstl:if>
	
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="create"
		value="<spring:message code="professionalRecord.create" />"
		onclick="javascript: relativeRedir('professionalRecord/ranger/create.do')" />
	</jstl:if>
	</security:authorize>
	<br/>
	<br/>
	
	<%-- MOSTRAMOS LOS DATOS DEL ENDORSER RECORD --%>
	<jstl:if test="${not empty curriculum.getEndorserRecords()}">
	<u><b><spring:message code="curriculum.endorserRecord"></spring:message></b></u>
	<br/>
	<jstl:forEach var="endorserRecord" items="${curriculum.getEndorserRecords()}">
	
	<li>
	<b><spring:message code="curriculum.endorserRecord.fullName"></spring:message>:</b>
	<jstl:out value="${endorserRecord.getFullName()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.endorserRecord.email"></spring:message>:</b>
	<jstl:out value="${endorserRecord.getEmail()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.endorserRecord.phoneNumber"></spring:message>:</b>
	<jstl:out value="${endorserRecord.getPhoneNumber()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.endorserRecord.linkedInProfileUrl"></spring:message>:</b>
	<jstl:out value="${endorserRecord.getLinkedInProfileUrl()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.endorserRecord.comment"></spring:message>:</b>
	<jstl:out value="${endorserRecord.getComment()}"/>
	</li>
	
	<br/>
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="edit"
		value="<spring:message code="endorserRecord.edit" />"
		onclick="javascript: relativeRedir('endorserRecord/ranger/edit.do?endorserRecordId=${endorserRecord.getId()}')" />
	</jstl:if>
	</security:authorize>
	<br/>
	</jstl:forEach>
	</jstl:if>
	
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="edit"
		value="<spring:message code="endorserRecord.create" />"
		onclick="javascript: relativeRedir('endorserRecord/ranger/create.do')" />
	</jstl:if>
	</security:authorize>
	<br/>
	<br/>
	
	<%-- MOSTRAMOS LOS MISCELLANEOUS RECORDS --%>
	<jstl:if test="${not empty curriculum.getMiscellaneousRecords()}">
	<u><b><spring:message code="curriculum.miscellaneousRecord"></spring:message></b></u>
	<br/>
	<jstl:forEach var="miscellaneousRecord" items="${curriculum.getMiscellaneousRecords()}">
	
	<li>
	<b><spring:message code="curriculum.miscellaneousRecord.title"></spring:message>:</b>
	<jstl:out value="${miscellaneousRecord.getTitle()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.miscellaneousRecord.comment"></spring:message>:</b>
	<jstl:out value="${miscellaneousRecord.getComment()}"/>
	</li>
	
	<li>
	<b><spring:message code="curriculum.miscellaneousRecord.attachmentUrl"></spring:message>:</b>
	<jstl:out value="${miscellaneousRecord.getAttachmentURL()}"/>
	</li>
	
	<br/>
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="edit"
		value="<spring:message code="miscellaneousRecord.edit" />"
		onclick="javascript: relativeRedir('miscellaneousRecord/ranger/edit.do?miscellaneousRecordId=${miscellaneousRecord.getId()}')" />
	</jstl:if>
	</security:authorize>
	<br/>
	</jstl:forEach>
	</jstl:if>
	
	<security:authorize access="hasRole('RANGER')">
	<jstl:if test="${rangerCurriculum.id eq curriculum.id}">
	<input type="button" name="edit"
		value="<spring:message code="miscellaneousRecord.create" />"
		onclick="javascript: relativeRedir('miscellaneousRecord/ranger/create.do')" />
	</jstl:if>
	</security:authorize>
	<br/>
	<br/>
	
</div>

<%-- BOTONES, SOLO LOS RANGERS PUEDEN EDITAR Y ELIMINAR SUS CURRICULUMS --%>

<form:form action="curriculum/ranger/edit.do" modelAttribute="curriculum">
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<input type="submit" name="delete" value="<spring:message code="curriculum.delete" />" />
	</form:form>
	<br/>
</jstl:if>
</security:authorize>

</jstl:if>


<input type="button" name="cancel"
		value="<spring:message code="ranger.back" />"
		onclick="javascript: relativeRedir('ranger/list.do');" />
	<br />