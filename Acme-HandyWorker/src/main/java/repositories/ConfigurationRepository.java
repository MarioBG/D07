
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

	@Query("select c.countryCode from Configuration c")
	String findCountryCode();
	
	@Query("select s from Configuration c join c.spamWords s")
	Collection<String> findSpamWords();

	@Query("select c.VATTax from Configuration c")
	Double findVat();
}
