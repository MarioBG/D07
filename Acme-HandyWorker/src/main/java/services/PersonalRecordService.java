
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.HandyWorker;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public PersonalRecord save(PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		PersonalRecord pr;

		pr = this.personalRecordRepository.save(personalRecord);

		return pr;
	}

	public PersonalRecord findOneToEdit(int personalRecordId) {
		PersonalRecord result;

		result = this.personalRecordRepository.findOne(personalRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public void checkPrincipal(PersonalRecord mr) {
		HandyWorker r;

		r = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(r.getCurriculum().getPersonalRecord().equals(mr));
	}

	public List<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(Integer id) {
		return this.personalRecordRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return this.personalRecordRepository.exists(id);
	}

	public PersonalRecord create() {
		PersonalRecord personalRecord;
		personalRecord = new PersonalRecord();

		return personalRecord;
	}

	public void delete(PersonalRecord personalRecord) {
		this.personalRecordRepository.delete(personalRecord.getId());

	}

}
