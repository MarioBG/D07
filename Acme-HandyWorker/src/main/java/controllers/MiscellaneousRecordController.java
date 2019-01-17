package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord")
public class MiscellaneousRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
	// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/ranger/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			MiscellaneousRecord miscellaneousRecord;

			miscellaneousRecord = miscellaneousRecordService.create();
			result = createEditModelAndView(miscellaneousRecord);
			
			return result;
		}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = miscellaneousRecordService.findOneToEdit(miscellaneousRecordId);

		result = this.createEditModelAndView(miscellaneousRecord);

		return result;
	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
			} catch (final Throwable oops) {
				String errorMessage = "miscellaneousRecord.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(miscellaneousRecord,
						errorMessage);
			}

		return result;
	}

	// DELETE --------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MiscellaneousRecord miscellaneousRecord,
			BindingResult bindingResult) {
		ModelAndView result;

		try {
			miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(miscellaneousRecord,
					"miscellaneousRecord.commit.error");
		}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			MiscellaneousRecord miscellaneousRecord, String message) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousRecord/edit");

		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", message);

		return result;
	}

}
