package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.ProfessionalRecord;

import services.ProfessionalRecordService;

@Controller
@RequestMapping("/professionalRecord")
public class ProfessionalRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private ProfessionalRecordService professionalRecordService;
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/ranger/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = professionalRecordService.create();
		result = createEditModelAndView(professionalRecord);
		
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = professionalRecordService.findOneToEdit(professionalRecordId);

		result = this.createEditModelAndView(professionalRecord);

		return result;
	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
			} catch (final Throwable oops) {
				String errorMessage = "professionalRecord.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(professionalRecord, errorMessage);
			}

		return result;
	}

	// DELETE --------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ProfessionalRecord professionalRecord,
			BindingResult bindingResult) {
		ModelAndView result;

		try {
			professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(professionalRecord,
					"professionalRecord.commit.error");
		}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ProfessionalRecord professionalRecord,
			String message) {
		ModelAndView result;

		result = new ModelAndView("professionalRecord/edit");

		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", message);

		return result;
	}

}
