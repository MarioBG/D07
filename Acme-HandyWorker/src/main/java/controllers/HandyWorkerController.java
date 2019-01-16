
package controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import domain.HandyWorker;

@Controller
@RequestMapping("/handyWorker")
public class HandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;


	public HandyWorkerController() {
		super();
	}

	@RequestMapping("/viewProfile")
	public ModelAndView view(@RequestParam(required = false) final Integer handyWorkerId) {
		ModelAndView result;

		result = new ModelAndView("handyWorker/viewProfile");
		if (handyWorkerId == null)
			result.addObject("handyWorker", this.handyWorkerService.findByPrincipal());
		else {
			final HandyWorker target = this.handyWorkerService.findOne(handyWorkerId);
			result.addObject("handyWorker", target);
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.create();
		result = this.createEditModelAndView(handyWorker);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();
		result = this.createEditModelAndView(handyWorker);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(handyWorker);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(handyWorker, "handyWorker.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker) {
		ModelAndView result;

		result = this.createEditModelAndView(handyWorker, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker, final String messageCode) {
		ModelAndView result;

		if (handyWorker.getId() > 0)
			result = new ModelAndView("handyWorker/edit");
		else
			result = new ModelAndView("handyWorker/register");

		result.addObject("handyWorker", handyWorker);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping("/register")
	public ModelAndView register() {

		ModelAndView result;
		final HandyWorker actor = this.handyWorkerService.create();

		result = new ModelAndView("handyWorker/register");
		result.addObject("handyWorker", actor);

		return result;
	}

	//	@RequestMapping(value = "/finder", method = RequestMethod.GET)
	//	public ModelAndView finder(
	//			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	//			@RequestParam(value = "startDate", required = false) String startDate,
	//			@RequestParam(value = "endDate", required = false) String endDate,
	//			@RequestParam(value = "maxPrice", required = false, defaultValue = "-1") double maxPrice) {
	//
	//		ModelAndView result;
	//
	//		result = new ModelAndView("handyWorker/finder");
	//		try {
	//			result.addObject("result", this.handyWorkerService.filter(keyword, startDate, endDate, maxPrice));
	//		} catch (ParseException e) {
	//			result.addObject("result", Arrays.asList());
	//		}
	//
	//		return result;
	//	}

}
