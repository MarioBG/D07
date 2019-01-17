
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	MiscellaneousRecordRepository	miscellaneousRecordRepository;

	@Autowired
	HandyWorkerService				handyWorkerService;


	public MiscellaneousRecord create() {

		MiscellaneousRecord er;
		er = new MiscellaneousRecord();

		return er;

	}

	public List<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord findOne(Integer id) {
		return this.miscellaneousRecordRepository.findOne(id);
	}

	public MiscellaneousRecord findOneToEdit(int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public MiscellaneousRecord save(MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		HandyWorker r;
		Collection<MiscellaneousRecord> c;
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		r = this.handyWorkerService.findByPrincipal();

		if (miscellaneousRecord.getId() == 0) {
			c = r.getCurriculum().getMiscellaneousRecords();
			c.add(result);
			r.getCurriculum().setMiscellaneousRecords(c);
			this.handyWorkerService.save(r);
		}
		return result;
	}

	public void delete(MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		HandyWorker r;
		Collection<MiscellaneousRecord> c;

		r = this.handyWorkerService.findByPrincipal();

		c = r.getCurriculum().getMiscellaneousRecords();
		c.remove(miscellaneousRecord);
		r.getCurriculum().setMiscellaneousRecords(c);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	public boolean exists(Integer id) {
		return this.miscellaneousRecordRepository.exists(id);
	}

	public void checkPrincipal(MiscellaneousRecord mr) {
		HandyWorker r;

		r = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(r.getCurriculum().getMiscellaneousRecords().contains(mr));
	}

}
