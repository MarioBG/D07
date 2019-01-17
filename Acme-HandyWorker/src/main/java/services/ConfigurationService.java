
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;


	//	@Autowired
	//	private Validator				validator;

	public Configuration save(Configuration entity) {
		return this.configurationRepository.save(entity);
	}

	public List<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(Integer id) {
		return this.configurationRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return this.configurationRepository.exists(id);
	}

	public void delete(Configuration entity) {
		this.configurationRepository.delete(entity);
	}

	public Configuration findConfiguration() {
		return this.configurationRepository.findAll().iterator().next();
	}

	public String findCountryCode() {
		String result;

		result = this.configurationRepository.findCountryCode();

		return result;
	}

	//	public Errors validateMessage(WelcomeMessage toValidate) {
	//		Errors errors = null;
	//		this.validator.validate(toValidate, errors);
	//		return errors;
	//	}

	public Double findVat() {
		Double result;

		result = this.configurationRepository.findVat();

		return result;
	}
}
