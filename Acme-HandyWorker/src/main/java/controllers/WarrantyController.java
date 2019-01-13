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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Warranty;
import services.AdministratorService;
import services.WarrantyService;

@Controller
@RequestMapping("/warranty")
public class WarrantyController extends AbstractController {

	@Autowired
	WarrantyService warrantyservice;
	
	@Autowired
	AdministratorService administratorService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("warranty/list");
		model.addObject("list", warrantyservice.findAll());

		return model;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Warranty warranty, BindingResult binding) {
		ModelAndView result;
		try {
			administratorService.deleteWarranty(warranty);
			result = new ModelAndView("redirect:/warranty/list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(administratorService.findOneWarranty(warranty.getId()), "warranty.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyservice.create();
		result = this.createEditModelAndView(warranty);
		return result;
	}

	protected ModelAndView createEditModelAndView(Warranty warranty) {
		ModelAndView result;
		result = createEditModelAndView(warranty, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Warranty warranty, String messageCode) {
		ModelAndView result;
		
		if (warranty.getId() > 0)
			result = new ModelAndView("warranty/administrator/edit");
		else
			result = new ModelAndView("warranty/administrator/create");
		
		result.addObject("warranty", warranty);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int warrantyId) {
		ModelAndView result;
		Warranty warranty;

		warranty = administratorService.findOneWarranty(warrantyId);
		Assert.notNull(warranty);
		result = createEditModelAndView(warranty);

		return result;
	}
	
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Warranty warranty, BindingResult binding) {
		ModelAndView result;
		
		if(binding.hasErrors()) {
			result = createEditModelAndView(warranty);
			for(ObjectError e : binding.getAllErrors()) {
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			}
		}else {
			try {
				administratorService.saveWarranty(warranty);
				result = new ModelAndView("redirect:/warranty/list.do");
			}catch (Throwable oops) {
				result = createEditModelAndView(warranty, "warranty.commit.error");
			}
		}
		return result;
	}
}
