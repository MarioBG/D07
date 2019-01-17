
package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxServices {

	@Autowired
	private BoxRepository	boxrepository;
	@Autowired
	private ActorService	actorservice;
	@Autowired
	private MessageService	messageService;


	public Box create() {
		Box res = new Box();
		Collection<Message> messages = new LinkedList<>();
		res.setMessages(messages);
		res.setPredefined(false);
		return res;
	}

	public Box newBox(Box name) {
		Actor current = this.actorservice.findSelf();

		Box saved = this.boxrepository.save(name);

		current.getBoxes().add(saved);

		this.actorservice.save(current);

		return saved;
	}

	public boolean exists(Integer id) {
		return this.boxrepository.exists(id);
	}

	public Box findInbox(Actor a) {
		Assert.notNull(a);

		for (Box b : a.getBoxes())
			if ("INBOX".equals(b.getName()))
				return b;

		return null;
	}

	public Box findOutbox(Actor a) {
		Assert.notNull(a);

		for (Box b : a.getBoxes())
			if ("OUTBOX".equals(b.getName()))
				return b;

		return null;
	}

	public Box findTashBox(Actor a) {
		Assert.notNull(a);

		for (Box b : a.getBoxes())
			if ("TRASHBOX".equals(b.getName()))
				return b;

		return null;
	}

	public Box getOutBoxFolderFromActorId(int id) {
		return this.boxrepository.getOutBoxFolderFromActorId(id);
	}

	public Box getInBoxFolderFromActorId(int id) {
		return this.boxrepository.getInBoxFolderFromActorId(id);
	}

	public Box getSpamBoxFolderFromActorId(int id) {
		return this.boxrepository.getSpamBoxFolderFromActorId(id);
	}

	public Box getTrashBoxFolderFromActorId(int id) {
		return this.boxrepository.getTrashBoxFolderFromActorId(id);
	}

	public List<Box> save(Iterable<Box> entities) {
		return this.boxrepository.save(entities);
	}

	public Box save(Box box) {
		Assert.notNull(box);
		return this.boxrepository.save(box);
	}

	public Box saveBox(Box box) {
		Assert.notNull(box);
		Box result, saved;
		Actor logedActor = this.actorservice.findSelf();
		if (this.exists(box.getId())) {
			saved = this.findOne(box.getId());
			Assert.notNull(saved);
			saved.setName(box.getName());
			saved.setParentBox(box.getParentBox());
			result = this.boxrepository.save(saved);
			Assert.notNull(result);
		} else {
			box.setPredefined(false);
			result = this.boxrepository.save(box);
			logedActor.getBoxes().add(result);
			this.actorservice.save(logedActor);
		}
		return result;
	}

	public Collection<Box> findBoxesByUserAccountId(int userAccountId) {
		return this.boxrepository.findBoxesByUserAccountId(userAccountId);
	}

	public List<Box> findAll() {
		return this.boxrepository.findAll();
	}

	public Box findOne(Integer id) {
		Assert.notNull(id);
		return this.boxrepository.findOne(id);
	}

	public void delete(Box entity) {
		Assert.notNull(entity);
		Actor principal = this.actorservice.findByPrincipal();
		Assert.isTrue(principal.getBoxes().contains(entity));
		Assert.isTrue(!"INBOX".equals(entity.getName()) && !"OUTBOX".equals(entity.getName()) && !"TRASHBOX".equals(entity.getName()) && !"SPAMBOX".equals(entity.getName()));
		Collection<Box> childrenBoxes = this.findAllChildrenBoxes(entity);
		this.actorservice.removeBox(principal, entity);
		if (!childrenBoxes.isEmpty()) {
			for (Box b : childrenBoxes)
				this.delete(b);
			for (Message m : entity.getMessages())
				this.messageService.delete(m);
			this.boxrepository.delete(entity);
		} else {
			for (Message m : entity.getMessages())
				this.messageService.delete(m);
			this.boxrepository.delete(entity);
		}

	}

	public Box moveBox(Box dst, Box box) {
		Assert.notNull(dst);
		Assert.notNull(box);

		Actor self = this.actorservice.findSelf();

		if (dst == null)
			box.setParentBox(null);
		else
			box.setParentBox(dst);
		box.setParentBox(dst);

		this.boxrepository.save(self.getBoxes());
		return this.boxrepository.save(dst);
	}

	public Collection<Box> findAllChildrenBoxes(Box box) {
		Collection<Box> res = new LinkedList<>();
		res = this.boxrepository.findChildrenBoxes(box.getId());
		return res;
	}

}
