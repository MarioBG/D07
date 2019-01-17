
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import repositories.CurriculumRepository;

@Service
@Transactional
public class CurriculumService {

	// Managed repository -----------------------------------------------------

//	@Autowired
//	private CurriculumRepository	curriculumRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CurriculumRepository curriculumRepository;

	@Autowired
	private PersonalRecordService personalRecordService;

	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	@Autowired
	private EndorserRecordService endorserRecordService;

	@Autowired
	private EducationRecordService educationRecordService;

	@Autowired
	private ProfessionalRecordService professionalRecordService;

	public Curriculum save(Curriculum curriculum, PersonalRecord personalRecord,
			Collection<MiscellaneousRecord> miscellaneousRecord, Collection<EndorserRecord> endorserRecord,
			Collection<EducationRecord> educationRecord, Collection<ProfessionalRecord> professionalRecord) {
		Assert.notNull(curriculum);
		Assert.notNull(personalRecord);

		personalRecordService.save(personalRecord);
		miscellaneousRecordService.save(miscellaneousRecord);
		endorserRecordService.save(endorserRecord);
		educationRecordService.save(educationRecord);
		professionalRecordService.save(professionalRecord);
		curriculum.setPersonalRecord(personalRecord);
		curriculum.setTicker(tickerGenerator());
		curriculum.getMiscellaneousRecords().addAll(miscellaneousRecord);
		curriculum.getEndorserRecords().addAll(endorserRecord);
		curriculum.getEducationRecords().addAll(educationRecord);
		curriculum.getProfessionalRecords().addAll(professionalRecord);

		return curriculumRepository.save(curriculum);
	}

	public List<Curriculum> findAll() {
		return curriculumRepository.findAll();
	}

	public List<Curriculum> findAll(Sort sort) {
		return curriculumRepository.findAll(sort);
	}

	public boolean exists(Integer id) {
		return curriculumRepository.exists(id);
	}

	public String generateAlphanumeric() {
		final Character[] letras = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		final Random rand = new Random();
		String alpha = "";
		for (int i = 0; i < 6; i++) {
			alpha += letras[rand.nextInt(letras.length - 1)];
		}

		return alpha;
	}

	@SuppressWarnings("deprecation")
	public String tickerGenerator() {
		String str = "";
		Date date = new Date(System.currentTimeMillis());
		str += Integer.toString(date.getYear()).substring(Integer.toString(date.getYear()).length() - 2);
		str += String.format("%02d", date.getMonth());
		str += String.format("%02d", date.getDay());
		String res = str + "-" + generateAlphanumeric();
		return res;
	}

	// Simple CRUD methods ----------------------------------------------------

}
