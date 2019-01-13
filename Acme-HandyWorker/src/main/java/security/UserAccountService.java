package security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	public UserAccount save(UserAccount entity) {
		return userAccountRepository.save(entity);
	}

	public List<UserAccount> findAll() {
		return userAccountRepository.findAll();
	}

	public UserAccount findOne(Integer id) {
		return userAccountRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return userAccountRepository.exists(id);
	}

	public void delete(UserAccount entity) {
		userAccountRepository.delete(entity);
	}
	
	public UserAccount findUserAccountByUsername(String username) {
		Assert.notNull(username);
		UserAccount res = userAccountRepository.findByUsername(username);
		Assert.notNull(res);
		return res;
	}
	
}
