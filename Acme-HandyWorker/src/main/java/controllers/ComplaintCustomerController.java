
package controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import domain.Complaint;
import domain.FixUpTask;
import domain.Report;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//createEditModelAndView
	protected ModelAndView createEditModelAndView(final Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		ModelAndView result;
		Report report;
		report = complaint.getReport();

		result = new ModelAndView("complaint/create");
		result.addObject("complaint", complaint);
		result.addObject("report", report);
		result.addObject("message", messageCode);

		return result;
	}
	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("complaint/customer/list");
		result.addObject("list", this.customerService.findByPrincipal().getComplaints());
		result.addObject("requestURI", "complaint/customer/list.do");
		Map<Integer, FixUpTask> complaintToTask = new HashMap<Integer, FixUpTask>();
		for (Complaint c : this.complaintService.findAll())
			complaintToTask.put(c.getId(), this.fixUpTaskService.findForComplaint(c));
		result.addObject("complaintToTask", complaintToTask);

		return result;
	}
	//Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.create();
		result = this.createEditModelAndView(complaint);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(complaint);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.customerService.saveComplaint(complaint, this.customerService.findByPrincipal());
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		return result;
	}

}
