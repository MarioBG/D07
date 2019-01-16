package controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Configuration;
import services.AdministratorService;
import services.ConfigurationService;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController extends AbstractController{
	
	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	AdministratorService administratorService;

	
	// Constructor
	public ConfigurationController() {
		super();
	}
			
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();
		Assert.notNull(configuration);
		result = new ModelAndView("configuration/view");

		result.addObject(configuration);

		return result;

	}
	
	
	protected ModelAndView createEditModelAndView(Configuration configuration) {
		ModelAndView result;
		result = createEditModelAndView(configuration, null);
		result.addObject("configuration", configurationService.findConfiguration());
		
		return result;
	}

	protected ModelAndView createEditModelAndView(Configuration configuration, String messageCode) {
		ModelAndView result;
		
		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();
		Assert.notNull(configuration);
		result = createEditModelAndView(configuration);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Configuration configuration, BindingResult binding) {
		ModelAndView result;
		
		if(binding.hasErrors()) {
			result = createEditModelAndView(configuration);
			for(ObjectError e : binding.getAllErrors()) {
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			}
		}else {
			try {
				administratorService.saveConfiguration(configuration);
				result = new ModelAndView("redirect:/configuration/view.do");
			}catch (Throwable oops) {
				result = createEditModelAndView(configuration, "configuration.commit.error");
			}
		}
		return result;
	}
}


