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

import domain.EducationRecord;

import services.EducationRecordService;

@Controller
@RequestMapping("/educationRecord")
public class EducationRecordController extends AbstractController {

	// SERVICES ------------------------------------
	@Autowired
	private EducationRecordService educationRecordService;
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/ranger/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = educationRecordService.create();
		result = createEditModelAndView(educationRecord);
		
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = educationRecordService.findOneToEdit(educationRecordId);

		result = this.createEditModelAndView(educationRecord);

		return result;
	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
			} catch (final Throwable oops) {
				String errorMessage = "educationRecord.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(educationRecord, errorMessage);
			}

		return result;
	}

	// DELETE --------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(EducationRecord educationRecord,
			BindingResult bindingResult) {
		ModelAndView result;

		try {
			educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:/curriculum/ranger/displayMyCurriculum.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(educationRecord,
					"educationRecord.commit.error");
		}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(EducationRecord educationRecord) {
		ModelAndView result;

		result = createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(EducationRecord educationRecord,
			String message) {
		ModelAndView result;

		result = new ModelAndView("educationRecord/edit");

		result.addObject("educationRecord", educationRecord);
		result.addObject("message", message);

		return result;
	}

}
