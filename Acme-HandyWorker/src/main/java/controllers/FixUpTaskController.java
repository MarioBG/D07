
package controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;
import dto.ApplicationAceptDTO;
import dto.ApplicationRejectDTO;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ApplicationService;
import services.CategoryService;
import services.ComplaintService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.PhaseService;
import services.WarrantyService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;
import dto.ApplicationAceptDTO;

@Controller
@RequestMapping("/fixuptask")
public class FixUpTaskController {

	@Autowired
	FixUpTaskService				fixuptaskservice;
	@Autowired
	CustomerService					customerservice;
	@Autowired
	CategoryService					categoryService;
	@Autowired
	WarrantyService					warrantyService;
	@Autowired
	ApplicationService				applicationservice;
	@Autowired
	PhaseService					phaseservice;
	@Autowired
	ComplaintService				complaintservice;
	@Autowired
	HandyWorkerService				handyworkerservice;
	@Autowired
	ActorService					actorService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ModelAndView filter(final Principal principal, final HttpServletRequest request, @RequestParam(value = "command", required = false) final String command, @RequestParam(value = "startDate", required = false) final String startDate,
		@RequestParam(value = "endDate", required = false) final String endDate, @RequestParam(value = "maxPrice", required = false, defaultValue = "-1") final double maxPrice,
		@RequestParam(value = "minPrice", required = false, defaultValue = "-1") final double minPrice) {

		final ModelAndView model = new ModelAndView("fixuptask/filter");
		try {
			model.addObject("data", this.handyworkerservice.filter(command, startDate, endDate, maxPrice, minPrice));
		} catch (final Exception e) {
			e.printStackTrace();
			model.addObject("data", Arrays.asList());
		}

		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
		for (final FixUpTask f : this.fixuptaskservice.findAll())
			customers.put(f.getId(), this.customerservice.findCustomerByFixUpTask(f));

		final ModelAndView model = new ModelAndView("fixuptask/list");
		model.addObject("list", this.fixuptaskservice.findAll());
		model.addObject("customers", customers);
		model.addObject("vatPercent", this.configurationService.findVat());
		return model;
	}

	@RequestMapping(value = "/listCustomer", method = RequestMethod.GET)
	public ModelAndView listCustomer(int customerId) {
		Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
		for (FixUpTask f : this.customerservice.findOne(customerId).getFixUpTasks())
			customers.put(f.getId(), this.customerservice.findCustomerByFixUpTask(f));

		ModelAndView model = new ModelAndView("fixuptask/listCustomer");
		model.addObject("list", this.customerservice.findOne(customerId).getFixUpTasks());
		model.addObject("customers", customers);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/async/phases", method = RequestMethod.GET)
	public String asyncPhases(@RequestParam(required = true, value = "q", defaultValue = "-1") final int id) {
		final Collection<Phase> phases = this.fixuptaskservice.getPhasesOf(id);

		final JsonArray array = new JsonArray();

		for (final Phase e : phases) {
			final JsonObject json = new JsonObject();
			json.addProperty("title", e.getTitle());
			json.addProperty("id", e.getId());

			array.add(json);
		}

		return array.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/async/complaints", method = RequestMethod.GET)
	public String asyncComplaints(@RequestParam(required = true, value = "q", defaultValue = "-1") final int id) {
		final Collection<Complaint> phases = this.fixuptaskservice.getComplaintsOf(id);

		final JsonArray array = new JsonArray();

		for (final Complaint e : phases) {
			final JsonObject json = new JsonObject();
			json.addProperty("title", e.getTicker());
			json.addProperty("id", e.getId());

			array.add(json);
		}

		return array.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/async/aplications", method = RequestMethod.GET)
	public String asyncAplications(@RequestParam(required = true, value = "q", defaultValue = "-1") final int id) {
		final Collection<Application> aplications = this.fixuptaskservice.getApplicationsOf(id);

		final JsonArray array = new JsonArray();

		for (final Application e : aplications) {
			final JsonObject json = new JsonObject();
			json.addProperty("title", e.getHandyWorker().getName() + " " + e.getHandyWorker().getSurname());
			json.addProperty("id", e.getId());

			array.add(json);
		}

		return array.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/customer/application-accept", method = RequestMethod.POST)
	public String acceptApplication(@RequestBody final String dto) {
		final Gson gson = new Gson();
		final JsonObject json = new JsonObject();
		final JsonArray erros = new JsonArray();

		final ApplicationAceptDTO parsed = gson.fromJson(dto, ApplicationAceptDTO.class);

		try {
			final Application application = this.applicationservice.accept(parsed);
			json.addProperty("application", application.getId());
		} catch (final Exception e) {
			erros.add(e.getMessage());
		}

		json.add("erros", erros);

		return json.toString();
	}

	// @RequestMapping(value = "/customer/application-accept", method =
	// RequestMethod.GET)
	// public ModelAndView acceptApplication(@RequestParam(value = "q") int
	// applicationId, @RequestParam(value = "f") int fixUpTaskId) {
	// Application application = applicationservice.findOne(applicationId);
	// application.setStatus("ACCEPTED");
	//
	// applicationservice.save(application);
	//
	// return edit(fixUpTaskId);
	// }

	@ResponseBody
	@RequestMapping(value = "/customer/application-reject", method = RequestMethod.POST)
	public String rejectApplication(@RequestBody final String dto) {
		final Gson gson = new Gson();
		final JsonObject json = new JsonObject();
		final JsonArray erros = new JsonArray();
		final ApplicationRejectDTO parsed = gson.fromJson(dto, ApplicationRejectDTO.class);

		try {
			final Application application = this.applicationservice.reject(parsed);
			json.addProperty("application", application.getId());
		} catch (final Exception e) {
			erros.add(e.getMessage());
		}

		json.add("erros", erros);

		return json.toString();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;
		try {
			this.fixuptaskservice.deleteFixUpTask(fixUpTask);
			result = new ModelAndView("redirect:/fixuptask/listCustomer.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fixUpTask, "fixuptask.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask fixUpTask;

		fixUpTask = this.fixuptaskservice.create();
		result = this.createEditModelAndView(fixUpTask);
		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask) {
		ModelAndView result;
		result = this.createEditModelAndView(fixUpTask, null);
		result.addObject("fixuptasks", this.fixuptaskservice.findAll());

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final String messageCode) {
		ModelAndView result;

		final UserAccount account = LoginService.getPrincipal();

		result = new ModelAndView(fixUpTask.getId() < 1 ? "fixuptask/create" : "fixuptasks/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("warranties", this.warrantyService.findAll());

		boolean isHandyWorker = false;
		boolean isCustomer = false;
		boolean canAddApplication = true;
		boolean canAddPhase = false;

		for (final Authority auth : account.getAuthorities()) {
			if (Authority.HANDYWORKER.equals(auth.getAuthority()))
				isHandyWorker = true;

			if (Authority.CUSTOMER.equals(auth.getAuthority()))
				isCustomer = true;
		}

		result.addObject("isHandyWorker", isHandyWorker);
		result.addObject("isCustomer", isCustomer);

		final Collection<Complaint> allComplaints = this.complaintservice.findAll();

		// Ya tiene una aplicacion, no puede a�adir m�s
		if (isHandyWorker) {
			final HandyWorker worker = this.handyworkerservice.findByPrincipal();
			for (final Application a : fixUpTask.getApplications())
				if (a.getHandyWorker() != null && a.getHandyWorker().equals(worker)) {
					canAddApplication = false;
					canAddPhase = "ACCEPTED".equals(a.getStatus());
					break;
				}

			result.addObject("acceptedApplication", this.applicationservice.findAcceptedHandyWorkerApplicationByFixUpTaskId(fixUpTask.getId(), worker.getId()));
			result.addObject("workerId", worker.getId());
		} else if (isCustomer)
			for (final Application a : fixUpTask.getApplications())
				if ("ACCEPTED".equals(a.getStatus())) {
					canAddPhase = true;
					break;
				}

		result.addObject("canAddPhase", canAddPhase);
		result.addObject("canAddApplication", canAddApplication);
		result.addObject("allComplaints", allComplaints);

		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;

		fixUpTask = this.fixuptaskservice.findOne(fixUpTaskId);
		Assert.notNull(fixUpTask);
		Boolean canBeDeleted = this.fixuptaskservice.canBeDeleted(fixUpTask);
		result = this.createEditModelAndView(fixUpTask);
		result.addObject("canBeDeleted", canBeDeleted);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result = new ModelAndView("redirect:/fixuptask/list.do");

		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			result = this.createEditModelAndView(fixUpTask);
		} else
			try {
				this.customerservice.saveCustomerFixUpTask(fixUpTask);
			} catch (final ObjectOptimisticLockingFailureException ex) {
				// This exception will can ignore
				return result;
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(fixUpTask, "fixuptask.commit.error");
			}
		return result;
	}

}
