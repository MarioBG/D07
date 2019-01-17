
package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messagerepository;
	@Autowired
	private ActorService		actorservice;
	@Autowired
	private BoxServices			boxservices;


	public Message save(Message entity) {
		return this.messagerepository.save(entity);
	}

	public List<Message> findAll() {
		return this.messagerepository.findAll();
	}

	public Message findOne(Integer id) {
		return this.messagerepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return this.messagerepository.exists(id);
	}

	public void delete(Message entity) {
		this.messagerepository.delete(entity);
	}

	public Box moveTo(Box src, Message message) {
		Assert.notNull(src);
		Assert.notNull(message);

		Actor self = this.actorservice.findSelf();

		for (Box b : self.getBoxes())
			b.getMessages().remove(message);

		this.boxservices.save(self.getBoxes());

		src.getMessages().add(message);

		return this.boxservices.save(src);
	}

	public void removeMessage(Message message) {
		Assert.notNull(message);
		Actor current = this.actorservice.findSelf();

		Box outbox = this.boxservices.findTashBox(current);

		if (outbox.getMessages().contains(message)) {

			if (message.getSender().equals(current))
				outbox.getMessages().remove(message);

			message.getRecipients().remove(current);

			message = this.messagerepository.save(message);

			if (message.getRecipients().isEmpty() && !this.findAllTrashboxMessages(message.getSender()).contains(message) && !this.findAllMessagesButTrashbox(message.getSender()).contains(message))
				this.messagerepository.delete(message.getId());
		} else {
			for (Box box : current.getBoxes()) {
				if ("TRASHBOX".equals(box.getName()))
					continue;

				box.getMessages().remove(message);
			}

			outbox.getMessages().add(message);

			this.boxservices.save(current.getBoxes());
		}
	}

	public Message sendMessage(Collection<Actor> recipients, Message message) {
		Actor current = this.actorservice.findSelf();

		message.setRecipients(recipients);
		message.setSender(current);
		message.setMoment(new Date());

		Message saved = this.messagerepository.save(message);

		for (Actor a : recipients) {
			Box inbox = this.boxservices.findInbox(a);
			inbox.getMessages().add(saved);

			this.boxservices.save(inbox);
		}

		Box outbox = null;

		for (Box b : current.getBoxes())
			if ("OUTBOX".equals(b.getName())) {
				outbox = b;
				break;
			}
		outbox.getMessages().add(saved);

		this.boxservices.save(outbox);

		return saved;
	}

	public Collection<Message> findMessagesFromActor(Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Collection<Message> res = this.messagerepository.messagesFromActorId(actor.getId());
		Assert.notEmpty(res);
		return res;
	}

	public Message create() {
		Message res = new Message();
		res.setMoment(new Date(System.currentTimeMillis() - 1));
		return res;
	}

	public Collection<String> findAllUsernamesForSender(Administrator administrator) {
		Collection<String> res = new LinkedList<>();
		res.addAll(this.messagerepository.getAllUsernamesForSender(administrator.getUserAccount().getId()));
		return res;
	}

	public Collection<Message> findAllMessagesButTrashbox(Actor actor) {
		Collection<Message> res = new LinkedList<>();
		res.addAll(this.messagerepository.getAllMessagesButTrashbox(actor.getUserAccount().getId()));
		return res;
	}

	public Collection<Message> findAllTrashboxMessages(Actor actor) {
		Collection<Message> res = new LinkedList<>();
		res.addAll(this.messagerepository.getAllTrashboxMessages(actor.getUserAccount().getId()));
		return res;
	}

	public Box findCurrentBox(Actor actor, Message message) {
		Box box;
		box = this.messagerepository.getCurrentBox(actor.getId(), message.getId());
		return box;
	}

}
