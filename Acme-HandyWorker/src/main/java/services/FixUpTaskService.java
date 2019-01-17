
package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Simple CRUD methods ----------------------------------------------------

	public FixUpTask create() {
		FixUpTask res = new FixUpTask();
		res.setApplications(new LinkedList<Application>());
		res.setComplaints(new LinkedList<Complaint>());
		res.setPhases(new LinkedList<Phase>());
		res.setPublicationMoment(new Date());
		res.setTicker(this.tickerGenerator());

		return res;
	}

	public Collection<Phase> getPhasesOf(int id) {
		return this.fixUpTaskRepository.findOne(id).getPhases();
	}

	public Collection<Complaint> getComplaintsOf(int id) {
		return this.fixUpTaskRepository.findOne(id).getComplaints();
	}

	public Collection<Application> getApplicationsOf(int id) {
		return this.fixUpTaskRepository.findOne(id).getApplications();
	}

	public FixUpTask save(FixUpTask entity) {
		return this.fixUpTaskRepository.save(entity);
	}

	public FixUpTask saveAndFlush(FixUpTask entity) {
		return this.fixUpTaskRepository.saveAndFlush(entity);
	}

	public List<FixUpTask> findAll() {
		return this.fixUpTaskRepository.findAll();
	}

	public FixUpTask findOne(Integer id) {
		return this.fixUpTaskRepository.findOne(id);
	}

	public void delete(FixUpTask entity) {
		this.fixUpTaskRepository.delete(entity);
	}

	public void deleteFixUpTask(FixUpTask entity) {
		HandyWorker hw;
		for (Application a : entity.getApplications()) {
			hw = this.handyWorkerService.findHandyWorkerByApplication(a);
			hw.getApplications().remove(a);

			entity.getApplications().remove(a);

			this.applicationService.delete(a);
			this.fixUpTaskRepository.saveAndFlush(entity);
			this.handyWorkerService.save(hw);
		}

		this.fixUpTaskRepository.delete(entity);
	}

	public boolean exists(final Integer id) {
		return this.fixUpTaskRepository.exists(id);
	}

	// Other business methods

	public Collection<FixUpTask> findFixUpTasksByCustomer(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(this.customerService.exists(customer.getId()));

		Collection<FixUpTask> result;
		result = this.fixUpTaskRepository.findFixUpTasksByCustomer(customer.getId());

		return result;
	}

	public Collection<FixUpTask> findAllFixUpTaskWithAcceptedApplications() {
		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.findAllFixUpTaskWithAcceptedApplications();
		Assert.notEmpty(res);
		return res;
	}

	public Double[] findAvgMinMaxStdDvtFixUpTasksPerUser() {
		Double[] res = this.fixUpTaskRepository.findAvgMinMaxStrDvtFixUpTaskPerUser();
		return res;
	}

	public Double[] findAvgMinMaxStrDvtPerFixUpTask() {
		Double[] res = this.fixUpTaskRepository.findAvgMinMaxStrDvtPerFixUpTask();
		return res;
	}

	public Double ratioFixUpTasksWithComplaints() {
		Double res = this.fixUpTaskRepository.ratioFixUpTasksWithComplaints();
		if (res == null)
			res = 0d;
		return res;
	}

	public String generateAlphanumeric() {
		final Character[] letras = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
		};
		final Random rand = new Random();
		String alpha = "";
		for (int i = 0; i < 6; i++)
			alpha += letras[rand.nextInt(letras.length - 1)];

		return alpha;
	}

	@SuppressWarnings("deprecation")
	public String tickerGenerator() {
		String str = "";
		Date date = new Date(System.currentTimeMillis());
		str += Integer.toString(date.getYear()).substring(Integer.toString(date.getYear()).length() - 2);
		str += String.format("%02d", date.getMonth());
		str += String.format("%02d", date.getDay());
		String res = str + "-" + this.generateAlphanumeric();
		return res;
	}

	public Collection<FixUpTask> findFixUpTasksByCategory(Category category) {
		Collection<FixUpTask> res = new LinkedList<>();
		res.addAll(this.fixUpTaskRepository.findFixUpTasksByCategoryId(category.getId()));
		return res;
	}

	public FixUpTask findForComplaint(Complaint c) {
		return this.fixUpTaskRepository.findForComplaintId(c.getId());
	}

	public Boolean canBeDeleted(FixUpTask fixuptask) {
		Assert.notNull(fixuptask);
		Collection<Application> applications = fixuptask.getApplications();
		for (Application a : applications)
			if ("ACCEPTED".equalsIgnoreCase(a.getStatus()))
				return false;

		return true;
	}
}
