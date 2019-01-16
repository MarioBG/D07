
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import dto.ApplicationAceptDTO;
import dto.ApplicationRejectDTO;
import repositories.ApplicationRepository;
import repositories.HandyWorkerRepository;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private HandyWorkerRepository handyWorkerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	CreditCardService creditcardservice;

	// Simple CRUD methods ----------------------------------------------------

	public boolean exists(final Integer id) {
		return this.applicationRepository.exists(id);
	}

	public Application findAcceptedHandyWorkerApplicationByFixUpTaskId(int fixUpTaskId, int handyWorkerId) {
		return this.applicationRepository.findAcceptedHandyWorkerApplicationByFixUpTaskId(fixUpTaskId, handyWorkerId);
	}

	public Application addComment(final Application aplication, final String... comments) {
		Assert.notNull(aplication);
		if (aplication.getComments() == null)
			aplication.setComments(new LinkedList<String>());

		final List<String> commments = new LinkedList<String>(aplication.getComments());
		commments.addAll(Arrays.asList(comments));
		aplication.setComments(commments);

		return this.applicationRepository.saveAndFlush(aplication);
	}

	public Application addCreditCard(final Application application, final CreditCard creditCard) {
		Assert.notNull(application);
		if (application.getCreditCard() == null)
			application.setCreditCard(creditCard);
		;

		return this.applicationRepository.saveAndFlush(application);
	}

	public List<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final Integer id) {
		return this.applicationRepository.findOne(id);
	}

	public void delete(final Application entity) {
		this.applicationRepository.delete(entity);
	}

	public Application save(Application entity) {
		if (this.applicationRepository.exists(entity.getId()))
			return this.applicationRepository.save(entity);
		else {
			entity.setApplicationMoment(new Date());
			return this.applicationRepository.save(entity);
		}
	}

	public Application reject(ApplicationRejectDTO dto) {
		Assert.isTrue(dto.getComment() != null && !dto.getComment().trim().isEmpty());

		Application application = this.applicationRepository.findOne(dto.getApplicationId());
		application.setStatus("REJECTED");
		application.getComments().add(dto.getComment());

		return this.applicationRepository.save(application);
	}

	public Application accept(ApplicationAceptDTO dto) {
		Assert.isTrue(dto.getComent() != null && !dto.getComent().trim().isEmpty());

		Application application = this.applicationRepository.findOne(dto.getApplicationId());
		application.setStatus("ACCEPTED");

		CreditCard card = new CreditCard();
		card.setBrandName(dto.getBrandName());
		card.setCVV(dto.getcVV());
		card.setExpirationMonth(dto.getExpirationMonth());
		card.setExpirationYear(dto.getExpirationYear());
		card.setHolderName(dto.getHolderName());
		card.setNumber(dto.getNumber());

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(card);

		if (!constraintViolations.isEmpty())
			throw new IllegalArgumentException(constraintViolations.iterator().next().getMessage());

		CreditCard savedCard = this.creditcardservice.save(card);
		application.getComments().add(dto.getComent());
		application.setCreditCard(savedCard);

		return this.applicationRepository.save(application);
	}

	public Application create() {
		Application result;
		result = new Application();
		Collection<String> comments = new LinkedList<>();
		String status = "PENDING";

		result.setStatus(status);
		result.setComments(comments);

		return result;
	}

	public Collection<Application> findApplicationsByCustomer(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(this.customerService.exists(customer.getId()));
		final Collection<Application> res = this.applicationRepository.findByCustomerId(customer.getId());
		Assert.notNull(res);
		return res;
	}

	public Collection<Application> findApplicationsByHandyWorker(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final Collection<Application> res = this.applicationRepository.findByHandyWorkerId(handyWorker.getId());
		Assert.notNull(res);
		return res;
	}

	public Application findAcceptedHandyWorkerApplicationByFixUpTask(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);
		Application res = this.applicationRepository.findAcceptedHandyWorkerApplicationByFixUpTaskId(fixUpTask.getId(),
				this.handyWorkerRepository.findByFixUpTaskId(fixUpTask.getId()).getId());
		Assert.isTrue(res.getStatus().equals("ACCEPTED"));
		return res;
	}

	public Double[] findAvgMinMaxStrDvtApplicationPerFixUpTask() {
		Double[] res = this.applicationRepository.findAvgMinMaxStrDvtApplicationPerFixUpTask();
		return res;
	}

	public Double[] findAvgMinMaxStrDvtPerApplication() {
		Double[] res = this.applicationRepository.findAvgMinMaxStrDvtPerApplication();
		return res;
	}

	public Double ratioOfPendingApplications() {
		Double res = this.applicationRepository.ratioOfPendingApplications();
		return res;
	}

	public Double ratioOfAcceptedApplications() {
		Double res = this.applicationRepository.ratioOfAcceptedApplications();
		return res;
	}

	public Double ratioOfRejectedApplications() {
		Double res = this.applicationRepository.ratioOfRejectedApplications();
		return res;
	}

	public Double ratioOfRejectedApplicationsCantChange() {
		Double res = this.applicationRepository.ratioOfRejectedApplicationsCantChange();
		return res;
	}
}
