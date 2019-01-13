
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	
	@Query("select re from Report re where re.finalMode=false")
	Collection<Report> findNotFinalModeReports();
	
	@Query("select u.report from Customer c join c.complaints u where c.id = ?1")
	Collection<Report> findReportsByCustomerId(int customerId);
	
	@Query("select re from Referee r join r.reports re where re.finalMode = true and r.id = ?1")
	Collection<Report> findReportsInFinalModeByRefereeId(int refereeId);
	
	@Query("select com.report from HandyWorker c join c.applications ap join ap.fixUpTask.complaints com where c.id = ?1 and ap.status = 'ACCEPTED'")
	Collection<Report> findReportsByHandyWorkerId(int handyWorkerId);

}
