
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

import security.LoginService;
import services.BoxServices;
import domain.Box;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	@Autowired
	BoxServices	boxService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("box/list");
		model.addObject("boxes", this.boxService.findBoxesByUserAccountId(LoginService.getPrincipal().getId()));

		return model;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam int boxId) {
		ModelAndView result;

		result = new ModelAndView("box/display");
		result.addObject("box", this.boxService.findOne(boxId));

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Box box;

		box = this.boxService.create();
		result = this.createEditModelAndView(box);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int boxId) {
		ModelAndView result;
		Box box;

		box = this.boxService.findOne(boxId);
		Assert.notNull(box);
		this.boxService.delete(box);
		result = new ModelAndView("redirect:/box/list.do?d-16544-p=1");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int boxId) {
		ModelAndView result;
		Box box;

		box = this.boxService.findOne(boxId);
		Assert.notNull(box);
		result = this.createEditModelAndView(box);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Box box, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(box);
			for (ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.boxService.saveBox(box);
				result = new ModelAndView("redirect:/box/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(box, "box.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(Box box) {
		ModelAndView result;
		result = this.createEditModelAndView(box, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Box box, String messageCode) {
		ModelAndView result;

		if (box.getId() > 0)
			result = new ModelAndView("box/edit");
		else
			result = new ModelAndView("box/create");

		result.addObject("boxes", this.boxService.findBoxesByUserAccountId(LoginService.getPrincipal().getId()));
		result.addObject("box", box);
		result.addObject("message", messageCode);

		return result;
	}
}
