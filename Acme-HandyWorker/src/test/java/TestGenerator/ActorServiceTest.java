
package TestGenerator;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.RefereeService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Referee;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml", "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private RefereeService		refereeService;
	@Autowired
	private UserAccountService	userAccountService;


	@Test
	public void saveActorTest() {
		Actor actor, saved;
		Collection<Actor> actors;
		actor = this.actorService.findAll().iterator().next();
		actor.setVersion(57);
		saved = this.actorService.save(actor);
		actors = this.actorService.findAll();
		Assert.isTrue(actors.contains(saved));
	}

	@Test
	public void findAllActorTest() {
		Collection<Actor> result;
		result = this.actorService.findAll();
		Assert.notNull(result);
	}

	@Test
	public void findOneActorTest() {
		Actor actor = this.actorService.findAll().iterator().next();
		int actorId = actor.getId();
		Assert.isTrue(actorId != 0);
		Actor result;
		result = this.actorService.findOne(actorId);
		Assert.notNull(result);
	}

	@Test
	public void deleteActorTest() {
		Actor actor = this.actorService.findAll().iterator().next();
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorService.exists(actor.getId()));
		this.actorService.delete(actor);
	}

	@Test
	public void isSuspiciousTest() {
		UserAccount userAccount = this.userAccountService.findUserAccountByUsername("referee2");
		Assert.notNull(userAccount);
		Referee referee = this.refereeService.findRefereeByUserAccount(userAccount);
		Assert.notNull(referee);
		boolean res = this.actorService.isSuspicious(referee);
		Assert.isTrue(res == true);
		Assert.isTrue(referee.isSuspicious() == true);
	}

}
