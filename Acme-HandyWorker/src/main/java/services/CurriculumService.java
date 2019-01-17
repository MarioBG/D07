
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed repository -----------------------------------------------------

	//	@Autowired
	//	private CurriculumRepository	curriculumRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CurriculumRepository		curriculumRepository;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	public Curriculum save(Curriculum curriculum, PersonalRecord personalRecord, Collection<MiscellaneousRecord> miscellaneousRecord, Collection<EndorserRecord> endorserRecord, Collection<EducationRecord> educationRecord,
		Collection<ProfessionalRecord> professionalRecord) {
		Assert.notNull(curriculum);
		Assert.notNull(personalRecord);

		this.personalRecordService.save(personalRecord);
		for (MiscellaneousRecord misc : miscellaneousRecord)
			this.miscellaneousRecordService.save(misc);
		this.endorserRecordService.save(endorserRecord);
		this.educationRecordService.save(educationRecord);
		for (ProfessionalRecord professionalRecord2 : professionalRecord)
			this.professionalRecordService.save(professionalRecord2);
		curriculum.setPersonalRecord(personalRecord);
		curriculum.setTicker(this.tickerGenerator());
		curriculum.getMiscellaneousRecords().addAll(miscellaneousRecord);
		curriculum.getEndorserRecords().addAll(endorserRecord);
		curriculum.getEducationRecords().addAll(educationRecord);
		curriculum.getProfessionalRecords().addAll(professionalRecord);

		return this.curriculumRepository.save(curriculum);
	}

	public List<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}

	public List<Curriculum> findAll(Sort sort) {
		return this.curriculumRepository.findAll(sort);
	}

	public boolean exists(Integer id) {
		return this.curriculumRepository.exists(id);
	}

	public String generateAlphanumeric() {
		final Character[] letras = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
		};
		final Random rand = new Random();
		String alpha = "";
		for (int i = 0; i < 6; i++)
			alpha += letras[rand.nextInt(letras.length - 1)];

		return alpha;
	}

	@SuppressWarnings("deprecation")
	public String tickerGenerator() {
		String str = "";
		Date date = new Date(System.currentTimeMillis());
		str += Integer.toString(date.getYear()).substring(Integer.toString(date.getYear()).length() - 2);
		str += String.format("%02d", date.getMonth());
		str += String.format("%02d", date.getDay());
		String res = str + "-" + this.generateAlphanumeric();
		return res;
	}

	public Curriculum findOne(int curriculumId) {
		return this.curriculumRepository.findOne(curriculumId);
	}

	public Curriculum getCurriculumByHandyWorkerId(int id) {
		return this.curriculumRepository.getCurriculumByHandyWorkerId(id);
	}

	public Curriculum findOneToEdit(int curriculumId) {
		Assert.notNull(curriculumId);

		Curriculum c;

		c = this.curriculumRepository.findOne(curriculumId);

		this.checkPrincipal(c.getId());

		return c;
	}

	public void checkPrincipal(int curriculumId) {
		HandyWorker a;
		HandyWorker r;

		a = this.handyWorkerService.findByPrincipal();
		r = this.handyWorkerService.getHandyWorkerByCurriculumId(curriculumId);
		Assert.isTrue(a.equals(r));
	}

	public Curriculum create() {
		Curriculum c;
		String ticker;
		Collection<EndorserRecord> endorserRecords;
		Collection<ProfessionalRecord> professionalRecords;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Collection<EducationRecord> educationRecords;

		ticker = this.tickerGenerator();
		endorserRecords = new ArrayList<>();
		professionalRecords = new ArrayList<>();
		miscellaneousRecords = new ArrayList<>();
		educationRecords = new ArrayList<>();

		c = new Curriculum();
		c.setTicker(ticker);
		c.setEndorserRecords(endorserRecords);
		c.setProfessionalRecords(professionalRecords);
		c.setMiscellaneousRecords(miscellaneousRecords);
		c.setEducationRecords(educationRecords);

		return c;
	}

	public void save(Curriculum curriculum) {
		// TODO Auto-generated method stub

	}

	public void delete(Curriculum curriculum) {
		Assert.notNull(curriculum);
		this.checkPrincipal(curriculum.getId());

		HandyWorker r;
		r = this.handyWorkerService.findByPrincipal();
		r.setCurriculum(null);
		this.handyWorkerService.save(r);
		this.curriculumRepository.delete(curriculum);
	}

	// Simple CRUD methods ----------------------------------------------------

}
