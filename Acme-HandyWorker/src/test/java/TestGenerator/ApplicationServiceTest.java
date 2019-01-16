
package TestGenerator;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.FixUpTaskService;
import utilities.AbstractTest;
import domain.Application;
import domain.FixUpTask;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml", "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@Test
	public void findAllApplicationTest() {
		Collection<Application> result;
		result = this.applicationService.findAll();
		Assert.notNull(result);
	}

	@Test
	public void findOneApplicationTest() {
		final Application application = this.applicationService.findAll().iterator().next();
		final int applicationId = application.getId();
		Assert.isTrue(applicationId != 0);
		Application result;
		result = this.applicationService.findOne(applicationId);
		Assert.notNull(result);
	}

	@Test
	public void deleteApplicationTest() {
		final Application application = this.applicationService.findAll().iterator().next();
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(this.applicationService.exists(application.getId()));
		this.applicationService.delete(application);
	}

	@Test
	public void findAcceptedHandyWorkerApplicationByFixUpTaskTest() {
		FixUpTask fixUpTask = this.fixUpTaskService.findAllFixUpTaskWithAcceptedApplications().iterator().next();
		Assert.notNull(fixUpTask);
		Application res = this.applicationService.findAcceptedHandyWorkerApplicationByFixUpTask(fixUpTask);
		Assert.isTrue(res.getStatus().equals("ACCEPTED"));
	}

	@Test
	public void findAvgMinMaxStrDvtApplicationPerFixUpTaskTest() {
		Double[] res = this.applicationService.findAvgMinMaxStrDvtApplicationPerFixUpTask();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}

	@Test
	public void findAvgMinMaxStrDvtPerApplicationTest() {
		Double[] res = this.applicationService.findAvgMinMaxStrDvtPerApplication();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}

	@Test
	public void ratioOfPendingApplicationsTest() {
		Double res = this.applicationService.ratioOfPendingApplications();
		Assert.notNull(res);
	}

	@Test
	public void ratioOfAcceptedApplicationsTest() {
		Double res = this.applicationService.ratioOfAcceptedApplications();
		Assert.notNull(res);
	}

	@Test
	public void ratioOfRejectedApplicationsTest() {
		Double res = this.applicationService.ratioOfRejectedApplications();
		Assert.notNull(res);
	}

	@Test
	public void ratioOfRejectedApplicationsCantChange() {
		Double res = this.applicationService.ratioOfRejectedApplicationsCantChange();
		Assert.notNull(res);
	}

}
