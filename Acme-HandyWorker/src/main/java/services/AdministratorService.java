
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Category;
import domain.Configuration;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;
import domain.Message;
import domain.Referee;
import domain.SocialIdentity;
import domain.Sponsor;
import domain.Warranty;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorservice;

	@Autowired
	LoginService					loginservice;

	@Autowired
	private MessageService			messageservice;

	@Autowired
	private WarrantyService			warrantyService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private EndorsementService		endorsementService;

	@Autowired
	private BoxServices				boxservices;


	// Simple CRUD methods ----------------------------------------------------

	public void sendAll(Message message) {
		Assert.notNull(message);

		Actor self = this.actorservice.findSelf();
		this.messageservice.sendMessage(this.actorservice.findAll(), message);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findSuspiciousActor() {
		final Collection<Actor> actors = new LinkedList<>();
		actors.addAll(this.actorservice.findSuspiciousActor());
		return actors;

	}

	public boolean exists(final Integer arg0) {
		return this.administratorRepository.exists(arg0);
	}

	public Administrator findOne(final int administratorId) {
		Assert.isTrue(administratorId != 0);

		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator result, saved;
		final UserAccount logedUserAccount;
		Authority authority;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		Assert.notNull(administrator, "administrator.not.null");

		if (this.exists(administrator.getId())) {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "administrator.notLogged ");
			Assert.isTrue(logedUserAccount.equals(administrator.getUserAccount()), "administrator.notEqual.userAccount");
			saved = this.administratorRepository.findOne(administrator.getId());
			Assert.notNull(saved, "administrator.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(administrator.getUserAccount().getUsername()), "administrator.notEqual.username");
			Assert.isTrue(administrator.getUserAccount().getPassword().equals(saved.getUserAccount().getPassword()), "administrator.notEqual.password");
			Assert.isTrue(administrator.getUserAccount().isAccountNonLocked() == saved.getUserAccount().isAccountNonLocked() && administrator.isSuspicious() == saved.isSuspicious(), "administrator.notEqual.accountOrSuspicious");

		} else {
			Assert.isTrue(administrator.isSuspicious() == false, "administrator.notSuspicious.false");
			administrator.getUserAccount().setPassword(encoder.encodePassword(administrator.getUserAccount().getPassword(), null));
			administrator.getUserAccount().setEnabled(true);
			Collection<Message> messages = new LinkedList<>();
			Box inbox = new Box();
			inbox.setName("INBOX");
			inbox.setPredefined(true);
			inbox.setMessages(messages);
			Box outbox = new Box();
			outbox.setName("OUTBOX");
			outbox.setPredefined(true);
			outbox.setMessages(messages);
			Box trashbox = new Box();
			trashbox.setName("TRASHBOX");
			trashbox.setPredefined(true);
			trashbox.setMessages(messages);
			Box spambox = new Box();
			spambox.setName("INBOX");
			spambox.setPredefined(true);
			spambox.setMessages(messages);
			Collection<Box> boxes = new LinkedList<Box>();
			boxes.add(this.boxservices.save(inbox));
			boxes.add(this.boxservices.save(outbox));
			boxes.add(this.boxservices.save(trashbox));
			boxes.add(this.boxservices.save(spambox));
			administrator.setBoxes(boxes);

		}

		result = this.administratorRepository.save(administrator);

		return result;

	}

	public Administrator create() {

		Administrator result;
		UserAccount userAccount;
		Authority authority;

		result = new Administrator();
		userAccount = new UserAccount();
		authority = new Authority();

		result.setSuspicious(false);

		authority.setAuthority("ADMINISTRATOR");
		userAccount.addAuthority(authority);
		userAccount.setEnabled(true);

		Collection<Box> boxes = new LinkedList<>();
		result.setBoxes(boxes);
		Collection<SocialIdentity> socialIdentity = new LinkedList<>();
		result.setSocialIdentity(socialIdentity);
		result.setUserAccount(userAccount);

		return result;

	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(this.administratorRepository.exists(administrator.getId()));
		this.administratorRepository.delete(administrator);
	}

	public UserAccount changeEnabledActor(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Assert.isTrue(this.actorservice.isSuspicious(this.actorservice.findByUserAccount(userAccount)));
		userAccount.setEnabled(!userAccount.isEnabled());
		return this.loginservice.save(userAccount);
	}

	public Warranty saveWarranty(Warranty warranty) {
		Warranty result, saved;
		UserAccount logedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		logedUserAccount = LoginService.getPrincipal();

		if (this.warrantyService.exists(warranty.getId()) && logedUserAccount.getAuthorities().contains(authority) && !warranty.isFinalMode()) {
			saved = this.warrantyService.findOne(warranty.getId());
			Assert.notNull(saved);
			result = this.warrantyService.save(warranty);
			Assert.notNull(result);
			return result;
		} else
			result = this.warrantyService.save(warranty);

		return result;
	}

	public List<Warranty> findAllWarranties() {
		UserAccount logedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		logedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(logedUserAccount.getAuthorities().contains(authority));
		return this.warrantyService.findAll();
	}

	public Warranty findOneWarranty(Integer warrantyId) {
		Assert.isTrue(this.warrantyService.exists(warrantyId));
		UserAccount logedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		logedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(logedUserAccount.getAuthorities().contains(authority));
		return this.warrantyService.findOne(warrantyId);
	}

	public void deleteWarranty(Warranty warranty) {
		UserAccount logedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		logedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(logedUserAccount.getAuthorities().contains(authority) && !warranty.isFinalMode());
		this.warrantyService.delete(warranty);
	}

	public Category saveCategory(Category category) {
		if (this.categoryService.exists(category.getId())) {
			Category saved = this.categoryService.findOne(category.getId());
			saved.setEspName(category.getEspName());
			saved.setName(category.getName());
			saved.setParentCategory(category.getParentCategory());

			return this.categoryService.save(saved);
		} else
			return this.categoryService.save(category);
	}

	public List<Category> findAllCategories() {
		UserAccount logedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		logedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(logedUserAccount.getAuthorities().contains(authority));
		return this.categoryService.findAll();
	}

	public Category findOneCategory(Integer categoryId) {
		Assert.isTrue(this.categoryService.exists(categoryId));
		UserAccount logedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		logedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(logedUserAccount.getAuthorities().contains(authority));
		return this.categoryService.findOne(categoryId);
	}

	public void deleteCategory(Category category) {
		Assert.notNull(category);
		Assert.isTrue(this.exists(category.getId()));

		UserAccount logedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		logedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(logedUserAccount.getAuthorities().contains(authority));
		this.categoryService.delete(category.getId());
	}

	public Double[] findAvgMinMaxStdDvtFixUpTasksPerUser() {
		Double[] res = this.fixUpTaskService.findAvgMinMaxStdDvtFixUpTasksPerUser();
		return res;
	}

	public Double[] findAvgMinMaxStrDvtApplicationPerFixUpTask() {
		Double[] res = this.applicationService.findAvgMinMaxStrDvtApplicationPerFixUpTask();
		return res;
	}

	public Double[] findAvgMinMaxStrDvtPerFixUpTask() {
		Double[] res = this.fixUpTaskService.findAvgMinMaxStrDvtPerFixUpTask();
		return res;
	}

	public Double[] findAvgMinMaxStrDvtPerApplication() {
		Double[] res = this.applicationService.findAvgMinMaxStrDvtPerApplication();
		return res;
	}

	public Double ratioOfPendingApplications() {
		Double res = this.applicationService.ratioOfPendingApplications();
		return res;
	}

	public Double ratioOfAcceptedApplications() {
		Double res = this.applicationService.ratioOfAcceptedApplications();
		return res;
	}

	public Double ratioOfRejectedApplications() {
		Double res = this.applicationService.ratioOfRejectedApplications();
		return res;
	}

	public Double ratioOfRejectedApplicationsCantChange() {
		Double res = this.applicationService.ratioOfRejectedApplicationsCantChange();
		return res;
	}

	public Collection<Customer> customersWith10PercentMoreAvgFixUpTask() {
		Collection<Customer> res = this.customerService.customersWith10PercentMoreAvgFixUpTask();
		return res;
	}

	public Collection<HandyWorker> handyWorkersWith10PercentMoreAvgApplicatios() {
		Collection<HandyWorker> res = this.handyWorkerService.handyWorkersWith10PercentMoreAvgApplicatios();
		return res;
	}

	public Sponsor saveSponsor(final Sponsor sponsor) {
		Sponsor result, saved;
		final UserAccount logedUserAccount;
		Authority authority1;
		Authority authority2;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		authority1 = new Authority();
		authority1.setAuthority("REFEREE");
		authority2 = new Authority();
		authority2.setAuthority("ADMINISTRATOR");
		Assert.notNull(sponsor, "sponsor.not.null");

		if (this.exists(sponsor.getId())) {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "sponsor.notLogged ");
			Assert.isTrue(logedUserAccount.equals(sponsor.getUserAccount()), "sponsor.notEqual.userAccount");
			saved = this.sponsorService.findOne(sponsor.getId());
			Assert.notNull(saved, "referee.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(sponsor.getUserAccount().getUsername()), "sponsor.notEqual.username");
			Assert.isTrue(sponsor.getUserAccount().getPassword().equals(saved.getUserAccount().getPassword()), "sponsor.notEqual.password");
			Assert.isTrue(sponsor.getUserAccount().isAccountNonLocked() == saved.getUserAccount().isAccountNonLocked() && sponsor.isSuspicious() == saved.isSuspicious(), "referee.notEqual.accountOrSuspicious");

		} else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "admin.notLogged ");
			Assert.isTrue(logedUserAccount.getAuthorities().contains(authority2), "admin.notEqual.userAccount");
			Assert.isTrue(sponsor.isSuspicious() == false, "admin.notSuspicious.false");
			sponsor.getUserAccount().setPassword(encoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
			sponsor.getUserAccount().setEnabled(true);
			Collection<Message> messages = new LinkedList<>();
			Box inbox = new Box();
			inbox.setName("INBOX");
			inbox.setPredefined(true);
			inbox.setMessages(messages);
			Box outbox = new Box();
			outbox.setName("OUTBOX");
			outbox.setPredefined(true);
			outbox.setMessages(messages);
			Box trashbox = new Box();
			trashbox.setName("TRASHBOX");
			trashbox.setPredefined(true);
			trashbox.setMessages(messages);
			Box spambox = new Box();
			spambox.setName("INBOX");
			spambox.setPredefined(true);
			spambox.setMessages(messages);
			Collection<Box> boxes = new LinkedList<Box>();
			boxes.add(this.boxservices.save(inbox));
			boxes.add(this.boxservices.save(outbox));
			boxes.add(this.boxservices.save(trashbox));
			boxes.add(this.boxservices.save(spambox));
			sponsor.setBoxes(boxes);

		}

		result = this.sponsorService.save(sponsor);

		return result;

	}

	public Referee saveReferee(final Referee referee) {
		Referee result, saved;
		final UserAccount logedUserAccount;
		Authority authority1;
		Authority authority2;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		authority1 = new Authority();
		authority1.setAuthority("REFEREE");
		authority2 = new Authority();
		authority2.setAuthority("ADMINISTRATOR");
		Assert.notNull(referee, "referee.not.null");

		if (this.exists(referee.getId())) {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "referee.notLogged ");
			Assert.isTrue(logedUserAccount.equals(referee.getUserAccount()), "referee.notEqual.userAccount");
			saved = this.refereeService.findOne(referee.getId());
			Assert.notNull(saved, "referee.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(referee.getUserAccount().getUsername()), "referee.notEqual.username");
			Assert.isTrue(referee.getUserAccount().getPassword().equals(saved.getUserAccount().getPassword()), "referee.notEqual.password");
			Assert.isTrue(referee.getUserAccount().isAccountNonLocked() == saved.getUserAccount().isAccountNonLocked() && referee.isSuspicious() == saved.isSuspicious(), "referee.notEqual.accountOrSuspicious");

		} else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "admin.notLogged ");
			Assert.isTrue(logedUserAccount.getAuthorities().contains(authority2), "admin.notEqual.userAccount");
			Assert.isTrue(referee.isSuspicious() == false, "admin.notSuspicious.false");
			referee.getUserAccount().setPassword(encoder.encodePassword(referee.getUserAccount().getPassword(), null));
			referee.getUserAccount().setEnabled(true);
			Collection<Message> messages = new LinkedList<>();
			Box inbox = new Box();
			inbox.setName("INBOX");
			inbox.setPredefined(true);
			inbox.setMessages(messages);
			Box outbox = new Box();
			outbox.setName("OUTBOX");
			outbox.setPredefined(true);
			outbox.setMessages(messages);
			Box trashbox = new Box();
			trashbox.setName("TRASHBOX");
			trashbox.setPredefined(true);
			trashbox.setMessages(messages);
			Box spambox = new Box();
			spambox.setName("INBOX");
			spambox.setPredefined(true);
			spambox.setMessages(messages);
			Collection<Box> boxes = new LinkedList<Box>();
			boxes.add(this.boxservices.save(inbox));
			boxes.add(this.boxservices.save(outbox));
			boxes.add(this.boxservices.save(trashbox));
			boxes.add(this.boxservices.save(spambox));
			referee.setBoxes(boxes);

		}

		result = this.refereeService.save(referee);

		return result;

	}

	public Configuration saveConfiguration(Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(this.configurationService.exists(configuration.getId()));
		Configuration saved = this.configurationService.save(configuration);
		Assert.notNull(saved);
		return saved;
	}

	private Pattern getPattern(boolean isGood) {
		Configuration conf = this.configurationService.findConfiguration();
		Collection<String> words;
		if (isGood)
			words = conf.getGoodWords();
		else
			words = conf.getBadWords();
		String patternString = "^";
		for (String word : words)
			patternString += (word + "|");
		patternString = patternString.substring(0, patternString.length() - 1);
		patternString += "$";
		Pattern ans = Pattern.compile(patternString);
		return ans;
	}
	public Map<Customer, Double> calculateCustomerScore() {
		Pattern goodPattern = this.getPattern(true);
		Pattern badPattern = this.getPattern(false);
		Matcher good, bad = null;
		final Map<Customer, Double> ans = new HashMap<Customer, Double>();
		for (final Customer c : this.customerService.findAll()) {
			Double score = 0d;
			for (final Endorsement e : this.endorsementService.findAll())
				if (e.getCustomer() != null && e.getCustomer().equals(c)) {
					good = goodPattern.matcher(e.getComment());
					bad = badPattern.matcher(e.getComment());
					while (good.find())
						score++;
					while (bad.find())
						score--;
				}
			ans.put(c, score);
		}
		final List<Double> values = new ArrayList<Double>(ans.values());
		Collections.sort(values);
		for (final Customer c : ans.keySet()) {
			Double value = -1 + ((ans.get(c) - values.get(0)) * (2)) / (values.get(values.size() - 1) - values.get(0));
			if (Double.isInfinite(value))
				value = 0d;
			ans.put(c, value);
		}
		return ans;
	}

	public Map<HandyWorker, Double> calculateHandyWorkerScore() {

		Pattern goodPattern = this.getPattern(true);
		Pattern badPattern = this.getPattern(false);
		Matcher good, bad = null;
		final Map<HandyWorker, Double> ans = new HashMap<HandyWorker, Double>();
		for (final HandyWorker h : this.handyWorkerService.findAll()) {
			Double score = 0d;
			for (final Endorsement e : this.endorsementService.findAll())
				if (e.getHandyWorker() != null && e.getHandyWorker().equals(h)) {
					good = goodPattern.matcher(e.getComment());
					bad = badPattern.matcher(e.getComment());
					while (good.find())
						score++;
					while (bad.find())
						score--;
				}
			ans.put(h, score);
		}
		final List<Double> values = new ArrayList<Double>(ans.values());
		Collections.sort(values);
		for (final HandyWorker c : ans.keySet()) {
			Double value = -1 + ((ans.get(c) - values.get(0)) * (2)) / (values.get(values.size() - 1) - values.get(0));
			if (Double.isInfinite(value))
				value = 0d;
			ans.put(c, value);
		}
		System.out.println(ans);
		return ans;
	}

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}
}
