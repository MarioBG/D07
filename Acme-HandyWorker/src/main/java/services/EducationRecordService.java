package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.EducationRecord;
import repositories.EducationRecordRepository;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	educationRecordRepository;


	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------


	public EducationRecord save(EducationRecord entity) {
		return educationRecordRepository.save(entity);
	}


	public List<EducationRecord> findAll() {
		return educationRecordRepository.findAll();
	}


	public EducationRecord findOne(Integer id) {
		return educationRecordRepository.findOne(id);
	}


	public boolean exists(Integer id) {
		return educationRecordRepository.exists(id);
	}


	public Collection<EducationRecord> save(Collection<EducationRecord> entities) {
		return educationRecordRepository.save(entities);
	}
	
	
	
}
