<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png" alt="Acme-HandyWorker Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/viewProfile.do"><spring:message code="master.page.administrator.view" /></a></li>	
					<li><a href="warranty/list.do"><spring:message code="master.page.warraty.list" /></a></li>
					<li><a href="category/list.do"><spring:message code="master.page.category.list" /></a></li>
					<li><a href="configuration/view.do"><spring:message code="master.page.configuration.display" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/registerAdministrator.do"><spring:message code="master.page.administrator.register" /></a></li>
					<li><a href="administrator/registerReferee.do"><spring:message code="master.page.referee.register" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/viewProfile.do"><spring:message code="master.page.customer.view" /></a></li>
					<li><a href="complaint/customer/list.do"><spring:message code="master.page.complaint.list" /></a></li>
					<li><a href="application/list.do"><spring:message code="master.page.applications" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.handyWorker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="handyWorker/viewProfile.do"><spring:message code="master.page.handyWorker.view" /></a></li>
					<li><a href="fixuptask/list.do"><spring:message code="fixuptask.list" /></a></li>
					<li><a href="fixuptask/filter.do"><spring:message code="master.page.handyWorker.filter" /></a></li>
					<li><a href="application/list.do"><spring:message code="master.page.applications" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.referee" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="referee/viewProfile.do"><spring:message code="master.page.referee.view" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsor/viewProfile.do"><spring:message code="master.page.sponsor.view" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/register.do"><spring:message code="master.page.customer.register" /></a></li>
					<li><a href="handyWorker/register.do"><spring:message code="master.page.handyWorker.register" /></a></li>
					<li><a href="sponsor/register.do"><spring:message code="master.page.sponsor.register" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="socialIdentity/list.do"><spring:message code="socialIdentity.label"/></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.box" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/list.do?d-16544-p=1"><spring:message code="master.page.boxes" /></a></li>
					<li><a href="message/send.do"><spring:message code="master.page.messages.send" /></a></li>
				</ul>
			</li>
			
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

