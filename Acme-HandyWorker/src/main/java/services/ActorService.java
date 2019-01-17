
package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Configuration;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;
	@Autowired
	private ConfigurationService	configurationService;
	@PersistenceContext
	EntityManager					manager;


	// Supporting services ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public boolean exists(final Integer id) {
		return this.actorRepository.exists(id);
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		Actor result;

		result = this.actorRepository.save(actor);

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor actor;

		final UserAccount principalUserAccount = LoginService.getPrincipal();
		if (principalUserAccount == null)
			actor = null;
		else
			actor = this.actorRepository.findByUserAccountId(principalUserAccount.getId());

		return actor;
	}

	public Collection<String> findAllUsername(final int adminId) {
		return this.actorRepository.findAllUsername(adminId);
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	@SuppressWarnings("unchecked")
	public Collection<Actor> findByUsernames(final Collection<String> usernames) {
		Assert.notNull(usernames);
		final List<String> transformed = new LinkedList<String>();

		for (final String e : usernames)
			if (e != null)
				transformed.add(String.format("'%s'", e));

		if (transformed.isEmpty())
			return new LinkedList<Actor>();

		final StringBuilder queryParam = new StringBuilder(transformed.toString());
		queryParam.deleteCharAt(0);
		queryParam.deleteCharAt(queryParam.length() - 1);

		final Query query = this.manager.createQuery(String.format("select c from Actor c where c.userAccount.username in (%s)", queryParam));

		return query.getResultList();
	}

	public Actor findSelf() {
		final UserAccount account = LoginService.getPrincipal();
		Assert.notNull(account);

		return this.actorRepository.findSelf(account.getUsername());
	}

	public Collection<Actor> findSuspiciousActor() {
		return this.actorRepository.findSuspiciousActor();
	}

	@SuppressWarnings("unchecked")
	public boolean isSuspicious(final Actor handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);

		final Query query = this.manager.createQuery("select m.body from Actor c join c.boxes b join b.messages m where c.id = :id");
		query.setParameter("id", handyWorker.getId());

		final List<String> bodies = query.getResultList();
		Assert.notNull(bodies);

		final Configuration configuration = this.configurationService.findAll().get(0);
		Assert.notNull(configuration);

		for (final String e : configuration.getSpamWords())
			for (final String b : bodies)
				if (b.toLowerCase().contains(e.toLowerCase())) {
					handyWorker.setSuspicious(true);
					this.actorRepository.save(handyWorker);
					return true;
				}

		return false;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Assert.isTrue(userAccount.getId() != 0);
		Actor res = this.actorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Actor findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		Actor res = this.actorRepository.findByUserAccountId(userAccountId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Actor> findAllSuspisiousActors() {
		final Collection<Actor> res = this.actorRepository.findAllSuspisiousActors();
		Assert.notNull(res);
		return res;

	}

	//	public Collection<Actor> findAllUnbannedActors() {
	//		final Collection<Actor> res = this.actorRepository.findAllUnbannedActors();
	//		Assert.notNull(res);
	//		return res;
	//	}
	//
	//	public Collection<Actor> findAllBannedActors() {
	//		final Collection<Actor> res = this.actorRepository.findAllBannedActors();
	//		Assert.notNull(res);
	//		return res;
	//	}

	public Actor ban(final int actorId) {
		final Actor res = this.actorRepository.findOne(actorId);
		UserAccount useraccount;

		Assert.isTrue(res.isSuspicious());
		useraccount = res.getUserAccount();
		Assert.isTrue(useraccount.isEnabled());
		useraccount.setEnabled(false);

		res.setUserAccount(useraccount);

		return res;

	}

	public Actor unban(final int actorId) {
		final Actor res = this.actorRepository.findOne(actorId);
		UserAccount useraccount;

		useraccount = res.getUserAccount();
		Assert.isTrue(!useraccount.isEnabled());
		useraccount.setEnabled(true);

		res.setUserAccount(useraccount);

		return res;

	}

	public void removeBox(Actor principal, Box entity) {
		Assert.isTrue(this.findByPrincipal().equals(principal));
		principal.getBoxes().remove(entity);
		this.actorRepository.save(principal);
	}

	// public Actor create() {
	//
	// Actor result;
	// UserAccount userAccount;
	//
	// result = new Actor() {};
	// userAccount = new UserAccount();
	//
	// result.setSuspicious(false);
	//
	// userAccount.setEnabled(true);
	//
	// Collection<Box> boxes = new LinkedList<>();
	// result.setBoxes(boxes);
	// Collection<SocialIdentity> socialIdentity = new LinkedList<>();
	// result.setSocialIdentity(socialIdentity);
	// result.setUserAccount(userAccount);
	//
	// return result;
	//
	// }

	// HandyWorker res = handyWorkerService.save(handyWorker);

}
