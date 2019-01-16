
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Application;
import domain.FixUpTask;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private FixUpTaskService		fixuptaskservice;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	public ApplicationController() {
		super();
	}

	@ResponseBody
	@RequestMapping(value = "/handyworker/save-async", method = RequestMethod.POST)
	public String saveAsync(@Valid Application application, BindingResult binding, @RequestParam(value = "q") int fixupTaskId) {
		JsonObject result = new JsonObject();
		JsonArray errors = new JsonArray();

		if (binding.hasErrors())
			for (ObjectError e : binding.getAllErrors())
				errors.add(e.getDefaultMessage());
		else
			try {
				Application saved = this.applicationService.save(application);
				FixUpTask task = this.fixuptaskservice.findOne(fixupTaskId);
				task.getApplications().add(saved);
				this.fixuptaskservice.saveAndFlush(task);

				result.addProperty("application", saved.getId());
			} catch (Throwable oops) {
				errors.add("general.error");
			}

		result.add("errors", errors);

		return result.toString();
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int applicationId) {
		ModelAndView result = new ModelAndView();
		Application target = this.applicationService.findOne(applicationId);
		result.addObject("application", target);
		result.addObject("vatPercent", this.configurationService.findVat());
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer handyWorkerId) {
		ModelAndView result;
		Collection<Application> applications;
		if (this.customerService.findByPrincipal() != null)
			applications = this.applicationService.findApplicationsByCustomer(this.customerService.findByPrincipal());
		else
			applications = this.applicationService.findApplicationsByHandyWorker(this.handyWorkerService.findByPrincipal());

		result = new ModelAndView("application/list");

		result.addObject("applications", applications);
		result.addObject("vatPercent", this.configurationService.findVat());

		return result;
	}

	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		Application application;
	//		
	//		application = this.applicationService.create();
	//		result = this.createEditModelAndView(application);
	//		
	//		return result;
	//	}

	//	protected ModelAndView createEditModelAndView(Application application) {
	//		ModelAndView result;
	//		
	//		result = createEditModelAndView(application, null);
	//		return result;
	//	}
	//	
	//	protected ModelAndView createEditModelAndView(Application application, String messageCode) {
	//		ModelAndView result;
	//		
	//		
	//		if (application.getId() > 0)
	//			result = new ModelAndView("application/edit");
	//		else
	//			result = new ModelAndView("application/create");
	//	
	//		result.addObject("application", application);
	//		result.addObject("message", messageCode);
	//		
	//		return result;
	//	}

}
