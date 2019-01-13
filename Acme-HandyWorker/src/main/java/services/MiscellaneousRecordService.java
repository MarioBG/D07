package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaneousRecord;
import repositories.MiscellaneousRecordRepository;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	MiscellaneousRecordRepository miscellaneousRecordService;

	public List<MiscellaneousRecord> findAll() {
		return miscellaneousRecordService.findAll();
	}

	public MiscellaneousRecord findOne(Integer id) {
		return miscellaneousRecordService.findOne(id);
	}

	public MiscellaneousRecord save(MiscellaneousRecord entities) {
		return miscellaneousRecordService.save(entities);
	}

	public boolean exists(Integer id) {
		return miscellaneousRecordService.exists(id);
	}

	public Collection<MiscellaneousRecord> save(Collection<MiscellaneousRecord> entities) {
		return miscellaneousRecordService.save(entities);
	}
	
	
	
	
}
