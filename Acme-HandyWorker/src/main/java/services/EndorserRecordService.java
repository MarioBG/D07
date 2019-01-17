package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.EndorserRecord;
import repositories.EndorserRecordRepository;

@Service
@Transactional
public class EndorserRecordService {

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------


	public EndorserRecord save(EndorserRecord entity) {
		return endorserRecordRepository.save(entity);
	}


	public List<EndorserRecord> findAll() {
		return endorserRecordRepository.findAll();
	}


	public EndorserRecord findOne(Integer id) {
		return endorserRecordRepository.findOne(id);
	}


	public boolean exists(Integer id) {
		return endorserRecordRepository.exists(id);
	}


	public Collection<EndorserRecord> save(Collection<EndorserRecord> entities) {
		return endorserRecordRepository.save(entities);
	}
	
	
	
	
	
}
