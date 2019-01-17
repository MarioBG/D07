package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EndorserRecordService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord")
public class EndorserRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private EndorserRecordService endorserRecordService;
	
	// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/ranger/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			EndorserRecord endorserRecord;

			endorserRecord = endorserRecordService.create();
			result = createEditModelAndView(endorserRecord);
			
			return result;
		}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = endorserRecordService.findOneToEdit(endorserRecordId);

		result = this.createEditModelAndView(endorserRecord);

		return result;
	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
			} catch (final Throwable oops) {
				String errorMessage = "endorserRecord.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(endorserRecord, errorMessage);
			}

		return result;
	}

	// DELETE --------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(EndorserRecord endorserRecord,
			BindingResult bindingResult) {
		ModelAndView result;

		try {
			endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(endorserRecord,
					"endorserRecord.commit.error");
		}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(EndorserRecord endorserRecord) {
		ModelAndView result;

		result = createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(EndorserRecord endorserRecord,
			String message) {
		ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");

		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", message);

		return result;
	}

}
