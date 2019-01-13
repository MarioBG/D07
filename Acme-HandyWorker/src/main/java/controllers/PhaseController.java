
package controllers;

import java.util.ArrayList;
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

import services.FixUpTaskService;
import services.PhaseService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.FixUpTask;
import domain.Phase;

@Controller
@RequestMapping("/phase")
public class PhaseController {

	@Autowired
	PhaseService				phaseService;
	@Autowired
	private FixUpTaskService	fixuptaskservice;


	@ResponseBody
	@RequestMapping(value = "/handyworker/save-async", method = RequestMethod.POST)
	public String saveAsync(@Valid Phase phase, BindingResult binding, @RequestParam(value = "q") int fixupTaskId) {
		JsonObject result = new JsonObject();
		JsonArray errors = new JsonArray();

		if (binding.hasErrors())
			for (ObjectError e : binding.getAllErrors())
				errors.add(e.getDefaultMessage());
		else
			try {
				Phase saved = this.phaseService.saveAndFlush(phase);
				FixUpTask task = this.fixuptaskservice.findOne(fixupTaskId);
				task.getPhases().add(saved);
				this.fixuptaskservice.saveAndFlush(task);

				result.addProperty("phase", saved.getId());
			} catch (Throwable oops) {
				errors.add("general.error");
			}

		result.add("errors", errors);

		return result.toString();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int fixUpTaskId) {
		ModelAndView result;
		Collection<Phase> phases = new ArrayList<Phase>();
		FixUpTask f = this.fixuptaskservice.findOne(fixUpTaskId);
		if (f != null)
			phases = f.getPhases();
		result = new ModelAndView("phase/list");

		result.addObject("phases", phases);

		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int phaseId) {
		ModelAndView result;
		Phase phase = this.phaseService.findOne(phaseId);
		result = new ModelAndView("phase/view");

		result.addObject("phase", phase);

		return result;
	}

}
