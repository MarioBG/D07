
package TestGenerator;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import utilities.AbstractTest;
import domain.Customer;
import domain.FixUpTask;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml", "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	@Autowired
	private FixUpTaskService	fixuptaskService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	//	@Test
	//	public void findFixUpTask() {
	//		List<FixUpTask> list = handyWorkerService.filter("970203", 10);
	//
	//		Assert.isTrue(!list.isEmpty());
	//	}

	@Test
	public void findAllFixUpTaskTest() {
		this.authenticate("handyWorker1");
		Collection<FixUpTask> result;
		result = this.fixuptaskService.findAll();
		Assert.notNull(result);
	}

	@Test
	public void findOneFixUpTaskTest() {
		FixUpTask result;

		final FixUpTask fixuptask = this.fixuptaskService.findAll().iterator().next();

		this.authenticate(this.customerService.findCustomerByFixUpTask(fixuptask).getUserAccount().getUsername());
		Assert.isTrue(fixuptask.getId() != 0);
		Assert.isTrue(this.fixuptaskService.exists(fixuptask.getId()));
		result = this.fixuptaskService.findOne(fixuptask.getId());
		Assert.notNull(result);
	}

	@Test
	public void deleteFixUpTaskTest() {
		final FixUpTask fixuptask = this.fixuptaskService.findAll().iterator().next();
		this.authenticate(this.customerService.findCustomerByFixUpTask(fixuptask).getUserAccount().getUsername());
		Assert.notNull(fixuptask);
		Assert.isTrue(fixuptask.getId() != 0);
		Assert.isTrue(this.fixuptaskService.exists(fixuptask.getId()));
		this.fixuptaskService.delete(fixuptask);
	}

	@Test
	public void findFixUpTasksByCustomerTest() {
		Collection<FixUpTask> fixUpTasks;
		final Customer customer = this.customerService.findAll().iterator().next();
		Assert.notNull(customer);
		fixUpTasks = this.fixuptaskService.findFixUpTasksByCustomer(customer);
		Assert.notNull(fixUpTasks);
	}

	@Test
	public void findAvgMinMaxStdDvtFixUpTasksTest() {
		Double[] res = this.fixuptaskService.findAvgMinMaxStdDvtFixUpTasksPerUser();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}

	@Test
	public void findAvgMinMaxStrDvtPerFixUpTaskTest() {
		Double[] res = this.fixuptaskService.findAvgMinMaxStrDvtPerFixUpTask();
		Assert.notNull(res);
		Assert.notEmpty(res);
	}

}
