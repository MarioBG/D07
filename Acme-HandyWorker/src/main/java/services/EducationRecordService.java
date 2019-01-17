
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public EducationRecord save(EducationRecord entity) {
		return this.educationRecordRepository.save(entity);
	}

	public List<EducationRecord> findAll() {
		return this.educationRecordRepository.findAll();
	}

	public EducationRecord findOne(Integer id) {
		return this.educationRecordRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return this.educationRecordRepository.exists(id);
	}

	public Collection<EducationRecord> save(Collection<EducationRecord> entities) {
		return this.educationRecordRepository.save(entities);
	}

	public EducationRecord create() {

		EducationRecord er;
		er = new EducationRecord();

		return er;

	}

	public EducationRecord findOneToEdit(int educationRecordId) {

		EducationRecord c;

		c = this.educationRecordRepository.findOne(educationRecordId);

		this.checkPrincipal(c.getId());

		return c;
	}

	public void checkPrincipal(int educationRecordId) {
		EducationRecord e = this.findOne(educationRecordId);
		Assert.notNull(e);
		Assert.isTrue(this.handyWorkerService.findByPrincipal().getCurriculum().getEducationRecords().contains(e));
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		HandyWorker r;
		Collection<EducationRecord> c;

		r = this.handyWorkerService.findByPrincipal();

		c = r.getCurriculum().getEducationRecords();
		c.remove(educationRecord);
		r.getCurriculum().setEducationRecords(c);

		this.educationRecordRepository.delete(educationRecord);

	}

}
