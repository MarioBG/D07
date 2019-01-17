
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.HandyWorker;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	//Managed Repository
	@Autowired
	private ComplaintRepository	complaintRepository;
	@Autowired
	private RefereeService		refereeService;


	//Constructor
	public ComplaintService() {
		super();
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

	//Simple CRUD methods
	public Complaint create() {
		Complaint result;

		result = new Complaint();
		result.setAttachments(new ArrayList<String>());
		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setTicker(this.tickerGenerator());

		return result;
	}

	public Complaint save(Complaint entity) {
		return this.complaintRepository.save(entity);
	}

	public List<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}

	public Complaint findOne(Integer id) {
		return this.complaintRepository.findOne(id);
	}

	public boolean exists(final int id) {
		return this.complaintRepository.exists(id);
	}

	//Other Business

	public Collection<Complaint> findComplaintsNoAsigned() {
		return this.complaintRepository.findComplaintsNoAsigned();
	}

	public Collection<Complaint> findByReferee(final Referee r) {
		Assert.isTrue(this.exists(r.getId()));
		Collection<Complaint> res;
		res = this.complaintRepository.findComplaintByReferee(r.getId());
		return res;
	}

	public Collection<Complaint> findSelfAsignedComplaintsByReferee(final Referee r) {
		UserAccount logedUserAccount = LoginService.getPrincipal();
		Authority authority = new Authority();
		authority.setAuthority("REFEREE");
		Assert.isTrue(logedUserAccount.getAuthorities().contains(authority));
		Assert.isTrue(this.refereeService.exists(r.getId()));
		Collection<Complaint> res;
		res = this.complaintRepository.findSelfAsignedComplaintsByRefereeId(r.getId());
		return res;

	}

	public Double[] computeAvgMinMaxStdvComplaintsPerFixUpTask() {
		Double[] res = this.complaintRepository.computeAvgMinMaxStdvComplaintsPerFixUpTask();
		Assert.notNull(res);
		return res;
	}

	public Collection<Complaint> findAcceptedHandyWorkerComplaintsByHandyWorker(HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		Collection<Complaint> res = this.complaintRepository.findAcceptedHandyWorkerComplaintsByHandyWorkerId(handyWorker.getId());
		Assert.notEmpty(res);
		return res;
	}

}
