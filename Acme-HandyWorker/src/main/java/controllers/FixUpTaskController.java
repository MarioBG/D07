
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
import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ApplicationService;
import services.CategoryService;
import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.PhaseService;
import services.WarrantyService;

@Controller
@RequestMapping("/fixuptask")
public class FixUpTaskController {

	@Autowired
	FixUpTaskService	fixuptaskservice;
	@Autowired
	CustomerService		customerservice;
	@Autowired
	CategoryService		categoryService;
	@Autowired
	WarrantyService		warrantyService;
	@Autowired
	ApplicationService	applicationservice;
	@Autowired
	PhaseService		phaseservice;
	@Autowired
	ComplaintService	complaintservice;
	@Autowired
	HandyWorkerService	handyworkerservice;

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ModelAndView filter(
			Principal principal,
			HttpServletRequest request,
			@RequestParam(value = "command", required = false) String command,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "maxPrice", required = false, defaultValue = "-1") double maxPrice,
			@RequestParam(value = "minPrice", required = false, defaultValue = "-1") double minPrice) {
		
		ModelAndView model = new ModelAndView("fixuptask/filter");
		try {
			model.addObject("data", handyworkerservice.filter(command, startDate, endDate, maxPrice, minPrice));
		} catch (Exception e) {
			e.printStackTrace();
			model.addObject("data", Arrays.asList());
		}
		
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
		for (FixUpTask f : this.fixuptaskservice.findAll())
			customers.put(f.getId(), this.customerservice.findCustomerByFixUpTask(f));

		ModelAndView model = new ModelAndView("fixuptask/list");
		model.addObject("list", this.fixuptaskservice.findAll());
		model.addObject("customers", customers);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/async/phases", method = RequestMethod.GET)
	public String asyncPhases(@RequestParam(required = true, value = "q", defaultValue = "-1") int id) {
		Collection<Phase> phases = this.fixuptaskservice.getPhasesOf(id);

		JsonArray array = new JsonArray();

		for (Phase e : phases) {
			JsonObject json = new JsonObject();
			json.addProperty("title", e.getTitle());
			json.addProperty("id", e.getId());

			array.add(json);
		}

		return array.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/async/complaints", method = RequestMethod.GET)
	public String asyncComplaints(@RequestParam(required = true, value = "q", defaultValue = "-1") int id) {
		Collection<Complaint> phases = this.fixuptaskservice.getComplaintsOf(id);

		JsonArray array = new JsonArray();

		for (Complaint e : phases) {
			JsonObject json = new JsonObject();
			json.addProperty("title", e.getTicker());
			json.addProperty("id", e.getId());

			array.add(json);
		}

		return array.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/async/aplications", method = RequestMethod.GET)
	public String asyncAplications(@RequestParam(required = true, value = "q", defaultValue = "-1") int id) {
		Collection<Application> aplications = this.fixuptaskservice.getApplicationsOf(id);

		JsonArray array = new JsonArray();

		for (Application e : aplications) {
			JsonObject json = new JsonObject();
			json.addProperty("title", e.getHandyWorker().getName() + " " + e.getHandyWorker().getSurname());
			json.addProperty("id", e.getId());

			array.add(json);
		}

		return array.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/customer/application-accept", method = RequestMethod.POST)
	public String acceptApplication(@RequestBody String dto) {
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		JsonArray erros = new JsonArray();

		ApplicationAceptDTO parsed = gson.fromJson(dto, ApplicationAceptDTO.class);

		try {
			Application application = this.applicationservice.accept(parsed);
			json.addProperty("application", application.getId());
		} catch (Exception e) {
			erros.add(e.getMessage());
		}

		json.add("erros", erros);

		return json.toString();
	}

	//	@RequestMapping(value = "/customer/application-accept", method = RequestMethod.GET)
	//	public ModelAndView acceptApplication(@RequestParam(value = "q") int applicationId, @RequestParam(value = "f") int fixUpTaskId) {
	//		Application application = applicationservice.findOne(applicationId);
	//		application.setStatus("ACCEPTED");
	//		
	//		applicationservice.save(application);
	//		
	//		return edit(fixUpTaskId);
	//	}

	@RequestMapping(value = "/customer/application-reject", method = RequestMethod.GET)
	public ModelAndView rejectApplication(@RequestParam(value = "q") int applicationId, @RequestParam(value = "f") int fixUpTaskId) {
		Application application = this.applicationservice.findOne(applicationId);
		application.setStatus("REJECTED");

		this.applicationservice.save(application);

		return this.edit(fixUpTaskId);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(FixUpTask fixuptask, BindingResult binding) {
		ModelAndView result;
		try {
			this.fixuptaskservice.delete(fixuptask);
			result = new ModelAndView("redirect:/fixuptask/list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(fixuptask, "fixuptask.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask fixuptask;

		fixuptask = this.fixuptaskservice.create();
		result = this.createEditModelAndView(fixuptask);

		return result;
	}

	protected ModelAndView createEditModelAndView(FixUpTask fixuptask) {
		ModelAndView result;
		result = this.createEditModelAndView(fixuptask, null);
		result.addObject("fixuptasks", this.fixuptaskservice.findAll());

		return result;
	}

	protected ModelAndView createEditModelAndView(FixUpTask fixuptask, String messageCode) {
		ModelAndView result;

		UserAccount account = LoginService.getPrincipal();

		result = new ModelAndView(fixuptask.getId() < 1 ? "fixuptask/create" : "fixuptasks/edit");
		result.addObject("fixuptask", fixuptask);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("warranties", this.warrantyService.findAll());

		boolean isHandyWorker = false;
		boolean isCustomer = false;
		boolean canAddApplication = true;
		boolean canAddPhase = false;

		for (Authority auth : account.getAuthorities()) {
			if (Authority.HANDYWORKER.equals(auth.getAuthority()))
				isHandyWorker = true;

			if (Authority.CUSTOMER.equals(auth.getAuthority()))
				isCustomer = true;
		}

		result.addObject("isHandyWorker", isHandyWorker);
		result.addObject("isCustomer", isCustomer);

		Collection<Complaint> allComplaints = this.complaintservice.findAll();

		// Ya tiene una aplicacion, no puede a�adir m�s
		if (isHandyWorker) {
			HandyWorker worker = this.handyworkerservice.findByPrincipal();
			for (Application a : fixuptask.getApplications())
				if (a.getHandyWorker() != null && a.getHandyWorker().equals(worker)) {
					canAddApplication = false;
					canAddPhase = "ACCEPTED".equals(a.getStatus());
					break;
				}

			result.addObject("acceptedApplication", this.applicationservice.findAcceptedHandyWorkerApplicationByFixUpTaskId(fixuptask.getId(), worker.getId()));
			result.addObject("workerId", worker.getId());
		} else if (isCustomer)
			for (Application a : fixuptask.getApplications())
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
	public ModelAndView edit(@RequestParam int fixuptaskId) {
		ModelAndView result;
		FixUpTask fixuptask;

		fixuptask = this.fixuptaskservice.findOne(fixuptaskId);
		Assert.notNull(fixuptask);
		result = this.createEditModelAndView(fixuptask);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FixUpTask fixuptask, BindingResult binding) {
		ModelAndView result = new ModelAndView("redirect:/fixuptask/list.do");

		if (binding.hasErrors()) {
			for (ObjectError e : binding.getAllErrors())
			System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			result = this.createEditModelAndView(fixuptask);
		}else
			try {
				this.customerservice.saveCustomerFixUpTask(fixuptask);
			} catch (ObjectOptimisticLockingFailureException ex) {
				// This exception will can ignore
				return result;
			} catch (Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(fixuptask, "fixuptask.commit.error");
			}
		return result;
	}

}
