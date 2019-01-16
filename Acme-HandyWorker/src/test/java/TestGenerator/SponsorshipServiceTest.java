
package TestGenerator;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.SponsorService;
import services.SponsorshipService;
import services.TutorialService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml", "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorService		sponsorService;


	@Test
	public void saveSponsorshipTest() {
		Sponsorship sponsorship, saved;
		Collection<Sponsorship> sponsorships;
		sponsorship = this.sponsorshipService.findAll().iterator().next();
		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		for (final Sponsor s : sponsors)
			if (s.getSponsorships().contains(sponsorship)) {
				this.authenticate(s.getUserAccount().getUsername());
				break;
			}
		sponsorship.setVersion(57);
		saved = this.sponsorshipService.save(sponsorship);
		sponsorships = this.sponsorshipService.findAll();
		Assert.isTrue(sponsorships.contains(saved));
	}

	@Test
	public void createSponsorshipTest() {
		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		this.authenticate(sponsors.iterator().next().getUserAccount().getUsername());
		final Sponsorship s = new Sponsorship();
		final CreditCard c = new CreditCard();
		c.setBrandName("VISA");
		c.setCVV(123);
		c.setExpirationMonth(10);
		c.setExpirationYear(20);
		c.setHolderName("Pepe Flores");
		c.setNumber("4161991501387341");
		s.setBanner("http://www.imagen.com/this.png");
		s.setCreditCard(c);
		s.setLink("http://www.google.com");
		s.setTutorial(this.tutorialService.findAll().iterator().next());
		final Sponsorship saved = this.sponsorshipService.save(s);
		Assert.notNull(this.sponsorshipService.findOne(saved.getId()));
	}
	@Test
	public void findAllSponsorshipTest() {
		Collection<Sponsorship> result;
		result = this.sponsorshipService.findAll();
		Assert.notNull(result);
	}

	@Test
	public void findOneSponsorshipTest() {
		final Sponsorship sponsorship = this.sponsorshipService.findAll().iterator().next();
		final int sponsorshipId = sponsorship.getId();
		Assert.isTrue(sponsorshipId != 0);
		Sponsorship result;
		result = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(result);
	}

	@Test
	public void deleteSponsorshipTest() {
		final Sponsorship sponsorship = this.sponsorshipService.findAll().iterator().next();
		Assert.notNull(sponsorship);
		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		for (final Sponsor s : sponsors)
			if (s.getSponsorships().contains(sponsorship)) {
				this.authenticate(s.getUserAccount().getUsername());
				break;
			}
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipService.exists(sponsorship.getId()));
		this.sponsorshipService.delete(sponsorship);
	}

}
