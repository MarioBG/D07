package controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Referee;
import services.RefereeService;

@Controller
@RequestMapping("/referee")
public class RefereeController extends AbstractController {

	@Autowired
	private RefereeService refereeService;

	public RefereeController() {
		super();
	}

	@RequestMapping("/viewProfile")
	public ModelAndView view() {
		ModelAndView result;

		result = new ModelAndView("referee/viewProfile");
		result.addObject("actor", refereeService.findByPrincipal());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Referee referee;

		referee = this.refereeService.create();
		result = this.createEditModelAndView(referee);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Referee referee;

		referee = refereeService.findByPrincipal();
		result = createEditModelAndView(referee);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Referee referee, BindingResult binding) {
		ModelAndView result;
		
		if(binding.hasErrors()) {
			result = createEditModelAndView(referee);
			for(ObjectError e : binding.getAllErrors()) {
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			}
		}else {
			try {
				refereeService.save(referee);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable oops) {
				result = createEditModelAndView(referee, "referee.commit.error");
			}
		}
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Referee referee) {
		ModelAndView result;

		result = createEditModelAndView(referee, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Referee referee, String messageCode) {
		ModelAndView result;

		if (referee.getId() > 0)
			result = new ModelAndView("referee/edit");
		else
			result = new ModelAndView("referee/create");

		result.addObject("actor", referee);
		result.addObject("message", messageCode);

		return result;
	}

}
