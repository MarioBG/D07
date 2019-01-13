
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select avg(r.notes.size), min(r.notes.size), max(r.notes.size), sqrt(sum(r.notes.size * r.notes.size)/count(r.notes.size) - (avg(r.notes.size)*avg(r.notes.size))) from Report r")
	Double[] computeAvgMinmaxStdvNotesPerReport();
	
	@Query("select u.report.notes from Customer c join c.complaints u where c.id = ?1")
	Collection<Note> findNotesByCustomerId(int customerId);
}
