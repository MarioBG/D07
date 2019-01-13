
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Actor a join a.boxes b join b.messages m where a.id=?1")
	Collection<Message> messagesFromActorId(int actorId);

	@Query("select m from Actor a join a.boxes b join b.messages m where a.userAccount.id=?1")
	Collection<Message> messagesFromUserAccountId(int userAccountId);

	@Query("select a.username from UserAccount a where a.id != ?1")
	Collection<String> getAllUsernamesForSender(int senderId);
	
	@Query("select m from Actor a join a.boxes b join b.messages m where b.name <> 'TRASHBOX' and a.userAccount.id = ?1")
	Collection<Message> getAllMessagesButTrashbox(int actorId);
	
	@Query("select m from Actor a join a.boxes b join b.messages m where b.name = 'TRASHBOX' and a.userAccount.id = ?1")
	Collection<Message> getAllTrashboxMessages(int actorId);
	
	@Query("select b from Actor a join a.boxes b join b.messages m where a.id =?1 and m.id = ?2")
	Box getCurrentBox(int actorId, int messageId);
}
