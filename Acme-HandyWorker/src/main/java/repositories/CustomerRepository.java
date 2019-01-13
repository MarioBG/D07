
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccountId);

	@Query("select c from Customer c where c.id = ?1")
	Customer findByCustomerId(int customerId);

	@Query("select c from Customer c join c.fixUpTasks t join t.applications a where a.id= ?1")
	Customer findCustomerByApplicationId(int applicationId);

	@Query("select c from Customer c join c.fixUpTasks f where f.id = ?1")
	Customer findCustomerByFixUpTaskId(int fixUpTaskId);

	@Query("select t from Customer t order by t.complaints.size")
	Page<Customer> topThreeCustomersInTermsOfComplaints(Pageable pageable);

	@Query("select distinct c from Customer c left join c.fixUpTasks fix where c.fixUpTasks.size >= (select avg(r.fixUpTasks.size) * 1.1 from Customer r) order by fix.applications.size")
	Collection<Customer> customersWith10PercentMoreAvgFixUpTask();

	@Query("select c from HandyWorker h join h.applications a join a.fixUpTask f, Customer c where f member of c.fixUpTasks and a.status='ACCEPTED' and h.userAccount.id = ?1")
	Collection<Customer> getCustomersForHandyWorkerWithUserAccountId(int id);

}
