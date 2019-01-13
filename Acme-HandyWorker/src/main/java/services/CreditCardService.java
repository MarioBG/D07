package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;
import repositories.CredirCardRepository;

@Service
@Transactional
public class CreditCardService {
	
	@Autowired
	CredirCardRepository credircardrepository;

	public CreditCard save(CreditCard entity) {
		return credircardrepository.save(entity);
	}

	public CreditCard findOne(Integer id) {
		return credircardrepository.findOne(id);
	}

	public void delete(Integer id) {
		credircardrepository.delete(id);
	}

}
