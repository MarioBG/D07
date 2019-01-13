package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Report;
import repositories.ReportRepository;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository reportrepository;

	public Report save(Report entity) {
		Assert.notNull(entity);
		return reportrepository.save(entity);
	}

	public Report findOne(Integer id) {
		Assert.notNull(id);
		return reportrepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return reportrepository.exists(id);
	}

	public void delete(Report report) {
		Assert.notNull(report);
		reportrepository.delete(report);
	}

	public List<Report> findAll() {
		return reportrepository.findAll();
	}
	
	public Collection<Report> findNotFinalModeReports() {
		Collection<Report> res = reportrepository.findNotFinalModeReports();
		Assert.notEmpty(res);
		return res;
	}
	
	public Collection<Report> findReportsByCustomer(Customer customer){
		Assert.notNull(customer);
		Assert.isTrue(customer.getId()!=0);
		Collection<Report> res = reportrepository.findReportsByCustomerId(customer.getId());
		Assert.notEmpty(res);
		return res;
	}
	
	public Collection<Report> findReportsInFinalModeByReferee (Referee referee) {
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);
		Collection<Report> res = reportrepository.findReportsInFinalModeByRefereeId(referee.getId());
		Assert.notNull(res);
		return res;	
	}
	
	public Collection<Report> findReportsByHandyWorker(HandyWorker handyWorker){
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId()!=0);
		Collection<Report> res = reportrepository.findReportsByHandyWorkerId(handyWorker.getId());
		Assert.notEmpty(res);
		return res;
	}
	
}
