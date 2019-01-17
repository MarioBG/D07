
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialIdentityService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController {

	// Services ----------------------------------------------
	@Autowired
	private SocialIdentityService	socialIdentityService;
	@Autowired
	private ActorService			actorService;


	// Constructors ----------------------------------------------

	public SocialIdentityController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.create();
		result = this.createEditModelAndView(socialIdentity);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int socialIdentityId) {
		ModelAndView result;

		SocialIdentity socialIdentity = this.socialIdentityService.findOneToEdit(socialIdentityId);
		Assert.notNull(socialIdentity);

		result = this.createEditModelAndView(socialIdentity);
		Actor actor = this.actorService.findByPrincipal();
		result.addObject(actor);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer actorId) {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		Actor a;
		if (actorId != null) {
			Assert.notNull(this.actorService.findOne(actorId));
			a = this.actorService.findOne(actorId);
		} else {
			Assert.notNull(this.actorService.findByPrincipal());
			a = this.actorService.findByPrincipal();
		}
		socialIdentities = a.getSocialIdentity();
		result = new ModelAndView("socialIdentity/list");

		result.addObject("socialIdentities", socialIdentities);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SocialIdentity socialIdentity, BindingResult binding) {
		ModelAndView result;

		result = new ModelAndView("redirect:/socialIdentity/list.do");

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity);
		else
			try {
				this.socialIdentityService.save(socialIdentity);
			} catch (Throwable oops) {
				result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SocialIdentity socialIdentity, BindingResult binding) {
		ModelAndView result;

		result = new ModelAndView("redirect:/socialIdentity/list.do");

		try {
			this.socialIdentityService.delete(socialIdentity);
		} catch (Throwable oops) {
			result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private ModelAndView createEditModelAndView(SocialIdentity socialIdentity) {
		ModelAndView result;

		result = this.createEditModelAndView(socialIdentity, null);

		return result;
	}

	private ModelAndView createEditModelAndView(SocialIdentity socialIdentity, String message) {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);

		return result;
	}

}
