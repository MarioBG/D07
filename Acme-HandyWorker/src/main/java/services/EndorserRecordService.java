
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private ConfigurationService		configurationService;


	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public EndorserRecord create() {

		EndorserRecord er;
		er = new EndorserRecord();

		return er;

	}

	public EndorserRecord findOneToEdit(int endorserRecordId) {
		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public void delete(EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);

		HandyWorker r;
		Collection<EndorserRecord> c;

		r = this.handyWorkerService.findByPrincipal();

		c = r.getCurriculum().getEndorserRecords();
		c.remove(endorserRecord);
		r.getCurriculum().setEndorserRecords(c);

		this.endorserRecordRepository.delete(endorserRecord);
	}

	public EndorserRecord save(EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		HandyWorker r;
		Collection<EndorserRecord> c;
		EndorserRecord result;

		result = this.endorserRecordRepository.save(endorserRecord);
		r = this.handyWorkerService.findByPrincipal();

		if (endorserRecord.getId() == 0) {
			c = r.getCurriculum().getEndorserRecords();
			c.add(result);
			r.getCurriculum().setEndorserRecords(c);
			this.handyWorkerService.save(r);
		}

		return result;
	}

	public List<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}

	public EndorserRecord findOne(Integer id) {
		return this.endorserRecordRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return this.endorserRecordRepository.exists(id);
	}

	public Collection<EndorserRecord> save(Collection<EndorserRecord> entities) {
		return this.endorserRecordRepository.save(entities);
	}

	public void checkPrincipal(EndorserRecord er) {
		HandyWorker r;

		r = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(r.getCurriculum().getEndorserRecords().contains(er));
	}

}
