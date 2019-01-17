package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.ProfessionalRecord;
import repositories.ProfessionalRecordRepository;

@Service
@Transactional
public class ProfessionalRecordService {

	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;

	public List<ProfessionalRecord> findAll() {
		return professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(Integer id) {
		return professionalRecordRepository.findOne(id);
	}

	public ProfessionalRecord save(ProfessionalRecord entities) {
		return professionalRecordRepository.save(entities);
	}

	public boolean exists(Integer id) {
		return professionalRecordRepository.exists(id);
	}

	public Collection<ProfessionalRecord> save(Collection<ProfessionalRecord> entities) {
		return professionalRecordRepository.save(entities);
	}
	
	
}
