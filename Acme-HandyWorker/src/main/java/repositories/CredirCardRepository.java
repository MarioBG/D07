
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CreditCard;

@Repository
public interface CredirCardRepository extends JpaRepository<CreditCard, Integer> {

}
