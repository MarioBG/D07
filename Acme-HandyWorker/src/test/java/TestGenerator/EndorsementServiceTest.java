
package TestGenerator;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.CustomerService;
import services.EndorsementService;
import services.HandyWorkerService;
import utilities.AbstractTest;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml", "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	@Test
	public void endorseAsCustomer() {
		Endorsement endorsement, saved;
		Collection<Endorsement> endorsements;
		endorsement = new Endorsement();
		final Customer c = this.customerService.findAll().iterator().next();
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();
		this.authenticate(c.getUserAccount().getUsername());
		endorsement.setHandyWorker(h);
		endorsement.setComment("Cosas cosos de chapuzas");
		saved = this.endorsementService.save(endorsement);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(endorsements.contains(saved));
	}

	@Test
	// TODO TransientObjectException (objeto sin guardar?)
	public void endorseAsHandyWorker() {
		Endorsement endorsement, saved;
		Collection<Endorsement> endorsements;
		endorsement = new Endorsement();
		final Customer c = this.customerService.findAll().iterator().next();
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();
		this.authenticate(h.getUserAccount().getUsername());
		endorsement.setCustomer(c);
		endorsement.setComment("Cosas cosos de chapuzas");
		saved = this.endorsementService.save(endorsement);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(endorsements.contains(saved));
	}

	@Test
	public void findAllEndorsementTest() {
		Collection<Endorsement> result;
		result = this.endorsementService.findAll();
		Assert.notNull(result);
	}

	@Test
	public void findOneEndorsementTest() {
		final Endorsement endorsement = this.endorsementService.findAll().iterator().next();
		final int endorsementId = endorsement.getId();
		Assert.isTrue(endorsementId != 0);
		Endorsement result;
		result = this.endorsementService.findOne(endorsementId);
		Assert.notNull(result);
	}

	@Test
	public void deleteEndorsementTest() {
		final Endorsement endorsement = this.endorsementService.findAll().iterator().next();
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(this.endorsementService.exists(endorsement.getId()));
		this.endorsementService.delete(endorsement.getId());
	}
}
