package controllers;

import java.util.Arrays;
import java.util.Collection;

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

import domain.Box;
import security.LoginService;
import security.UserAccount;
import services.BoxServices;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	@Autowired
	BoxServices boxService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("box/list");
		model.addObject("boxes", boxService.findBoxesByUserAccountId(LoginService.getPrincipal().getId()));

		return model;
	}
	
	@RequestMapping("/display")
	public ModelAndView display(@RequestParam int boxId) {
		ModelAndView result;

		result = new ModelAndView("box/display");
		result.addObject("box", boxService.findOne(boxId));

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
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int boxId) {
		ModelAndView result;
		Box box;

		box = boxService.findOne(boxId);
		Assert.notNull(box);
		result = createEditModelAndView(box);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Box box, BindingResult binding) {
		ModelAndView result;
		
		if(binding.hasErrors()) {
			result = createEditModelAndView(box);
			for(ObjectError e : binding.getAllErrors()) {
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			}
		}else {
			try {
				boxService.saveBox(box);
				result = new ModelAndView("redirect:/box/list.do");
			}catch (Throwable oops) {
				result = createEditModelAndView(box, "box.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Box box, BindingResult binding) {
		ModelAndView result;
		try {
			boxService.delete(box);
			result = new ModelAndView("redirect:/box/list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(boxService.findOne(box.getId()), "box.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int boxId) {
		ModelAndView result;
		Box box;

		box = this.boxService.findOne(boxId);
		Assert.notNull(box);

		result = this.moveModelAndView(box);

		return result;

	}
	
	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView move(@RequestParam(value="src") int src, @RequestParam(value="dst") int dst) {
		ModelAndView result;
			try {
				this.boxService.moveBox(dst, src);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.moveModelAndView(boxService.findOne(src), "box.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(Box box) {
		ModelAndView result;
		result = createEditModelAndView(box, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Box box, String messageCode) {
		ModelAndView result;
		
		if (box.getId() > 0)
			result = new ModelAndView("box/edit");
		else
			result = new ModelAndView("box/create");
		
		result.addObject("boxes", boxService.findBoxesByUserAccountId(LoginService.getPrincipal().getId()));
		result.addObject("box", box);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView moveModelAndView(final Box box) {
		ModelAndView result;

		result = this.moveModelAndView(box, null);

		return result;
	}
	protected ModelAndView moveModelAndView(final Box box, final String messageCode) {
		ModelAndView result;
		Collection<Box> boxes;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = new ModelAndView("box/move");
		boxes = this.boxService.findBoxesByUserAccountId(userAccount.getId());
		boxes.remove(box);

		result.addObject("boxes", boxes);
		result.addObject("box", box);
		result.addObject("message", messageCode);

		return result;
	}
}

