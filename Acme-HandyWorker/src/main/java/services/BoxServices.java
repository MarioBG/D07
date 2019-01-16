package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Box;
import domain.Message;
import repositories.BoxRepository;

@Service
@Transactional
public class BoxServices {

	@Autowired
	private BoxRepository boxrepository;
	@Autowired
	private ActorService actorservice;
	@Autowired
	private MessageService messageService;

	public Box create() {
		Box res = new Box();
		Collection<Message> messages = new LinkedList<>();
		res.setMessages(messages);
		res.setPredefined(false);
		return res;
	}
	
	public Box newBox(Box name) {
		Actor current = actorservice.findSelf();

		Box saved = boxrepository.save(name);

		current.getBoxes().add(saved);

		actorservice.save(current);

		return saved;
	}

	public boolean exists(Integer id) {
		return actorservice.exists(id);
	}

	public Box findInbox(Actor a) {
		Assert.notNull(a);

		for (Box b : a.getBoxes()) {
			if ("INBOX".equals(b.getName())) {
				return b;
			}
		}

		return null;
	}

	public Box findOutbox(Actor a) {
		Assert.notNull(a);

		for (Box b : a.getBoxes()) {
			if ("OUTBOX".equals(b.getName())) {
				return b;
			}
		}

		return null;
	}

	public Box findTashBox(Actor a) {
		Assert.notNull(a);

		for (Box b : a.getBoxes()) {
			if ("TRASHBOX".equals(b.getName())) {
				return b;
			}
		}

		return null;
	}

	public Box getOutBoxFolderFromActorId(int id) {
		return boxrepository.getOutBoxFolderFromActorId(id);
	}

	public Box getInBoxFolderFromActorId(int id) {
		return boxrepository.getInBoxFolderFromActorId(id);
	}

	public Box getSpamBoxFolderFromActorId(int id) {
		return boxrepository.getSpamBoxFolderFromActorId(id);
	}

	public Box getTrashBoxFolderFromActorId(int id) {
		return boxrepository.getTrashBoxFolderFromActorId(id);
	}

	public List<Box> save(Iterable<Box> entities) {
		return boxrepository.save(entities);
	}

	public Box save(Box box) {
		Assert.notNull(box);
		return boxrepository.save(box);
	}
	
	public Box saveBox(Box box) {
		Assert.notNull(box);
		Box result, saved;
		Actor logedActor = actorservice.findSelf();
		if(this.exists(box.getId()) ) {
			saved = this.findOne(box.getId());
			Assert.notNull(saved);
			saved.setName(box.getName());
			saved.setParentBox(box.getParentBox());
			result = boxrepository.save(saved);
			Assert.notNull(result);
		}else {
			box.setPredefined(false);
			result = boxrepository.save(box);
			logedActor.getBoxes().add(result);
			actorservice.save(logedActor);
		}
		return result;
	}

	public Collection<Box> findBoxesByUserAccountId(int userAccountId) {
		return boxrepository.findBoxesByUserAccountId(userAccountId);
	}

	public List<Box> findAll() {
		return boxrepository.findAll();
	}

	public Box findOne(Integer id) {
		Assert.notNull(id);
		return boxrepository.findOne(id);
	}

	public void delete(Box entity) {
		Assert.notNull(entity);
		Assert.isTrue(!"INBOX".equals(entity.getName()) && !"OUTBOX".equals(entity.getName())
				&& !"TRASHBOX".equals(entity.getName()) && !"SPAMBOX".equals(entity.getName()));
		Collection<Box> childrenBoxes = this.findAllChildrenBoxes(entity);
		if (!childrenBoxes.isEmpty()) {
			for (Box b : childrenBoxes) {
				b.setParentBox(entity.getParentBox());
			}
			for(Message m : entity.getMessages()) {
				messageService.delete(m);
			}
			boxrepository.delete(entity);
		} else {
			for(Message m : entity.getMessages()) {
				messageService.delete(m);
			}
			boxrepository.delete(entity);
		}

	}

	public Box moveBox(Box dst, Box box) {
		Assert.notNull(dst);
		Assert.notNull(box);

		Actor self = actorservice.findSelf();

		if(dst == null) {
			box.setParentBox(null);
		}else {
			box.setParentBox(dst);
		}
		box.setParentBox(dst);

		boxrepository.save(self.getBoxes());
		return boxrepository.save(dst);
	}

	public Collection<Box> findAllChildrenBoxes(Box box) {
		Collection<Box> res = new LinkedList<>();
		res = boxrepository.findChildrenBoxes(box.getId());
		return res;
	}

}
