package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Phase;
import repositories.PhaseRepository;

@Service
@Transactional
public class PhaseService {
	
	@Autowired
	PhaseRepository phaserepository;

	public Phase save(Phase entity) {
		Assert.notNull(entity);
		return phaserepository.save(entity);
	}

	public Phase saveAndFlush(Phase entity) {
		return phaserepository.saveAndFlush(entity);
	}

	public List<Phase> findAll() {
		return phaserepository.findAll();
	}

	public Phase findOne(int id) {
		return phaserepository.findOne(id);
	}

	public boolean exists(int id) {
		return phaserepository.exists(id);
	}

	public void delete(int id) {
		phaserepository.delete(id);
	}

}
