
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.PersonalRecord;
import repositories.PersonalRecordRepository;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------


	public PersonalRecord save(PersonalRecord entity) {
		return personalRecordRepository.save(entity);
	}


	public List<PersonalRecord> findAll() {
		return personalRecordRepository.findAll();
	}


	public PersonalRecord findOne(Integer id) {
		return personalRecordRepository.findOne(id);
	}


	public boolean exists(Integer id) {
		return personalRecordRepository.exists(id);
	}
	
	
	
	
}
