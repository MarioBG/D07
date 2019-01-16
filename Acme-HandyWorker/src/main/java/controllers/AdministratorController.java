/*
 * AdministratorController.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.NoteService;
import services.RefereeService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------
	@Autowired
	private AdministratorService administratorservice;

	@Autowired
	private ActorService actorService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private RefereeService refereeService;

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------

	@RequestMapping("/viewProfile")
	public ModelAndView view() {
		ModelAndView result;

		result = new ModelAndView("administrator/viewProfile");
		result.addObject("actor", this.administratorservice.findByPrincipal());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorservice.create();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorservice.findByPrincipal();

		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(administrator);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(
						e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.administratorservice.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "administrator.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/editReferee", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Referee referee, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(referee);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(
						e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.administratorservice.saveReferee(referee);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(referee, "referee.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String messageCode) {
		ModelAndView result;

		if (administrator.getId() > 0)
			result = new ModelAndView("administrator/edit");
		else
			result = new ModelAndView("administrator/registerAdministrator");

		result.addObject("administrator", administrator);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Referee referee) {
		ModelAndView result;

		result = this.createEditModelAndView(referee, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Referee referee, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/registerReferee");

		result.addObject("actor", referee);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping("/registerAdministrator")
	public ModelAndView registerAdmin() {

		ModelAndView result;
		final Administrator actor = this.administratorservice.create();

		result = new ModelAndView("administrator/registerAdministrator");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping("/registerReferee")
	public ModelAndView registerReferee() {

		ModelAndView result;
		final Referee actor = this.refereeService.create();

		result = new ModelAndView("administrator/registerReferee");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");

		Double[] avgMinMaxStdDvtFixUpTasksPerUser;
		Double[] avgMinMaxStrDvtApplicationPerFixUpTask;
		Double[] avgMinMaxStrDvtPerFixUpTask;
		Double[] avgMinMaxStrDvtPerApplication;
		Double ratioOfPendingApplications;
		Double ratioOfAcceptedApplications;
		Double ratioOfRejectedApplications;
		Double ratioOfRejectedApplicationsCantChange;
		Collection<Customer> customersWith10PercentMoreAvgFixUpTask;
		Collection<HandyWorker> handyWorkersWith10PercentMoreAvgApplicatios;
		Double[] avgMinMaxStdvComplaintsPerFixUpTask;
		Double[] avgMinMaxStdvNotesPerReferee;
		Double percentageFixUpTasksWithComplaint;
		Collection<Customer> customersByComplaints;
		Collection<HandyWorker> handyWorkersByComplaints;

		avgMinMaxStdDvtFixUpTasksPerUser = this.fixUpTaskService.findAvgMinMaxStdDvtFixUpTasksPerUser();
		avgMinMaxStrDvtApplicationPerFixUpTask = this.applicationService.findAvgMinMaxStrDvtApplicationPerFixUpTask();
		avgMinMaxStrDvtPerFixUpTask = this.fixUpTaskService.findAvgMinMaxStrDvtPerFixUpTask();
		avgMinMaxStrDvtPerApplication = this.applicationService.findAvgMinMaxStrDvtPerApplication();
		ratioOfPendingApplications = this.applicationService.ratioOfPendingApplications();
		ratioOfAcceptedApplications = this.applicationService.ratioOfAcceptedApplications();
		ratioOfRejectedApplications = this.applicationService.ratioOfRejectedApplications();
		ratioOfRejectedApplicationsCantChange = this.applicationService.ratioOfRejectedApplicationsCantChange();
		customersWith10PercentMoreAvgFixUpTask = this.customerService.customersWith10PercentMoreAvgFixUpTask();
		handyWorkersWith10PercentMoreAvgApplicatios = this.handyWorkerService
				.handyWorkersWith10PercentMoreAvgApplicatios();

		avgMinMaxStdvComplaintsPerFixUpTask = this.administratorservice.findAvgMinMaxStrDvtPerFixUpTask();
		avgMinMaxStdvNotesPerReferee = this.noteService.computeAvgMinmaxStdvNotesPerReport();
		percentageFixUpTasksWithComplaint = this.fixUpTaskService.ratioFixUpTasksWithComplaints();
		customersByComplaints = this.customerService.topThreeCustomersInTermsOfComplaints();
		handyWorkersByComplaints = this.handyWorkerService.topThreeHandyWorkersInTermsOfComplaints();

		result.addObject("avgMinMaxStdDvtFixUpTasksPerUser", avgMinMaxStdDvtFixUpTasksPerUser);
		result.addObject("avgMinMaxStrDvtApplicationPerFixUpTask", avgMinMaxStrDvtApplicationPerFixUpTask);
		result.addObject("avgMinMaxStrDvtPerFixUpTask", avgMinMaxStrDvtPerFixUpTask);
		result.addObject("avgMinMaxStrDvtPerApplication", avgMinMaxStrDvtPerApplication);
		result.addObject("ratioOfPendingApplications", ratioOfPendingApplications);
		result.addObject("ratioOfAcceptedApplications", ratioOfAcceptedApplications);
		result.addObject("ratioOfRejectedApplications", ratioOfRejectedApplications);
		result.addObject("ratioOfRejectedApplicationsCantChange", ratioOfRejectedApplicationsCantChange);
		result.addObject("customersWith10PercentMoreAvgFixUpTask", customersWith10PercentMoreAvgFixUpTask);
		result.addObject("handyWorkersWith10PercentMoreAvgApplicatios", handyWorkersWith10PercentMoreAvgApplicatios);
		result.addObject("avgMinMaxStdvComplaintsPerFixUpTask", avgMinMaxStdvComplaintsPerFixUpTask);
		result.addObject("avgMinMaxStdvNotesPerReferee", avgMinMaxStdvNotesPerReferee);
		result.addObject("percentageFixUpTasksWithComplaint", percentageFixUpTasksWithComplaint);
		result.addObject("customersByComplaints", customersByComplaints);
		result.addObject("handyWorkersByComplaints", handyWorkersByComplaints);

		return result;
	}

	// List suspicious actors
	@RequestMapping(value = "/listSuspicious", method = RequestMethod.GET)
	public ModelAndView listSuspicious() {
		ModelAndView res;
		Collection<Actor> actors;

		actors = this.administratorservice.findSuspiciousActor();

		res = new ModelAndView("administrator/listSuspicious");
		res.addObject("actors", actors);
		res.addObject("requestURI", "administrator/listSuspicious.do");

		return res;
	}

	// List banned actors
//	@RequestMapping(value = "/listBanned", method = RequestMethod.GET)
//	public ModelAndView listBanned() {
//		ModelAndView res;
//		Collection<Actor> actors;
//
//		actors = this.actorService.findAllBannedActors();
//
//		res = new ModelAndView("administrator/listBanned");
//		res.addObject("bannedactors", actors);
//		res.addObject("requestURI", "administrator/listBanned.do");
//
//		return res;
//	}

	// Ban an actor
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView res;
		Actor a;

		a = this.actorService.ban(actorId);

		res = new ModelAndView("administrator/listBanned");
		res.addObject("bannedactors", a);
		res.addObject("requestURI", "administrator/listBanned");

		return res;

	}

	// Unban actor
	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId) {
		ModelAndView res;
		Actor a;

		a = this.actorService.unban(actorId);

		res = new ModelAndView("administrator/listSuspicious");
		res.addObject("actors", a);
		res.addObject("requestURI", "administrator/listSuspicious");

		return res;

	}
}
