
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	@Autowired
	private ActorService				actorService;
	@Autowired
	private AdministratorService		administratorService;


	// Constructors -----------------------------------------------------------

	public SocialIdentityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SocialIdentity create() {
		SocialIdentity result;
		result = new SocialIdentity();

		result.setNick("");
		result.setSocialNetworkName("");
		result.setProfileLink("");

		return result;
	}

	public SocialIdentity save(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		if (socialIdentity.getId() != 0)
			Assert.isTrue(this.actorService.findByPrincipal().getSocialIdentity().contains(socialIdentity));

		SocialIdentity result;

		result = this.socialIdentityRepository.save(socialIdentity);

		Actor a = this.actorService.findByPrincipal();
		a.getSocialIdentity().add(result);
		this.actorService.save(a);

		return result;
	}

	public void delete(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		Assert.isTrue(socialIdentity.getId() != 0);
		Assert.isTrue(this.socialIdentityRepository.exists(socialIdentity.getId()));

		this.socialIdentityRepository.delete(socialIdentity);
	}

	public void delete(Iterable<SocialIdentity> socialIdentities) {
		Assert.notNull(socialIdentities);
		this.socialIdentityRepository.delete(socialIdentities);
	}

	public SocialIdentity findOne(int socialIdentityId) {
		Assert.notNull(socialIdentityId);
		Assert.isTrue(socialIdentityId != 0);

		SocialIdentity result;

		result = this.socialIdentityRepository.findOne(socialIdentityId);

		return result;
	}

	public SocialIdentity findOneToEdit(int socialIdentityId) {
		Assert.notNull(socialIdentityId);
		Assert.isTrue(socialIdentityId != 0);

		SocialIdentity result;

		result = this.socialIdentityRepository.findOne(socialIdentityId);

		Assert.isTrue(this.actorService.findByPrincipal().getSocialIdentity().contains(result));

		return result;
	}

	public Collection<SocialIdentity> findAll() {
		Collection<SocialIdentity> result;

		result = this.socialIdentityRepository.findAll();
		Assert.notNull(result);

		return result;
	}
}
