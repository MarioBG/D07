
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PersonalRecordService;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord")
public class PersonalRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private PersonalRecordService	personalRecordService;
	@Autowired
	private ActorService			actorService;


	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/ranger/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();
		result = this.createEditModelAndView(personalRecord);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId) {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.findOneToEdit(personalRecordId);

		result = this.createEditModelAndView(personalRecord);

		return result;
	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				this.personalRecordService.save(personalRecord);
				result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
			} catch (final Throwable oops) {
				String errorMessage = "endorserRecord.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(personalRecord, errorMessage);
			}

		return result;
	}

	// DELETE --------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(PersonalRecord personalRecord, BindingResult bindingResult) {
		ModelAndView result;

		try {
			this.personalRecordService.delete(personalRecord);
			result = new ModelAndView("redirect:/views/welcome/index");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(personalRecord, "endorserRecord.commit.error");
		}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(PersonalRecord personalRecord, String message) {
		ModelAndView result;

		result = new ModelAndView("personalRecord/edit");

		result.addObject("personalRecord", personalRecord);
		result.addObject("message", message);

		return result;
	}

}
