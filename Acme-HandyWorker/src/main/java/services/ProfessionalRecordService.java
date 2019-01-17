
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	@Autowired
	private HandyWorkerService				handyWorkerService;


	public List<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(Integer id) {
		return this.professionalRecordRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return this.professionalRecordRepository.exists(id);
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		HandyWorker r;
		Collection<ProfessionalRecord> c;

		r = this.handyWorkerService.findByPrincipal();

		c = r.getCurriculum().getProfessionalRecords();
		c.remove(professionalRecord);
		r.getCurriculum().setProfessionalRecords(c);

		this.professionalRecordRepository.delete(professionalRecord);
	}

	public ProfessionalRecord create() {

		ProfessionalRecord pr;

		pr = new ProfessionalRecord();

		return pr;

	}

	public ProfessionalRecord findOneToEdit(int EducationRecordId) {
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(EducationRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public void checkPrincipal(ProfessionalRecord mr) {

		Assert.isTrue(this.handyWorkerService.findByPrincipal().getCurriculum().getProfessionalRecords().contains(mr));
	}

	public ProfessionalRecord save(final ProfessionalRecord pr) {
		Assert.notNull(pr);
		Assert.isTrue(pr.getStartDate().before(pr.getEndDate()), "message.error.startDateEndDate");
		HandyWorker r;
		Collection<ProfessionalRecord> c;
		ProfessionalRecord result;

		result = this.professionalRecordRepository.save(pr);
		r = this.handyWorkerService.findByPrincipal();

		if (pr.getId() == 0) {
			c = r.getCurriculum().getProfessionalRecords();
			c.add(pr);
			r.getCurriculum().setProfessionalRecords(c);
			this.handyWorkerService.save(r);
		}
		return result;
	}

}
