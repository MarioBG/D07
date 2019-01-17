
package TestGenerator;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.BoxServices;
import services.MessageService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Box;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml", "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorservice;
	@Autowired
	private BoxServices		boxservices;


	@Test
	public void moveMessage() {
		Actor sender = this.actorservice.findByUsernames(Arrays.asList("admin1")).iterator().next();
		this.authenticate(sender.getUserAccount().getUsername());

		Box inbox = this.boxservices.findInbox(sender);
		Box trashbox = this.boxservices.findTashBox(sender);
		Message message = inbox.getMessages().iterator().next();

		this.messageService.moveTo(trashbox, message);

		inbox = this.boxservices.findInbox(sender);
		trashbox = this.boxservices.findTashBox(sender);

		Assert.isTrue(!inbox.getMessages().contains(message));
		Assert.isTrue(trashbox.getMessages().contains(message));
	}

	@Test
	public void removeMessage() {
		Actor sender = this.actorservice.findByUsernames(Arrays.asList("admin1")).iterator().next();
		this.authenticate(sender.getUserAccount().getUsername());

		Box inbox = this.boxservices.findInbox(sender);
		Box trashbox = this.boxservices.findOutbox(sender);

		Message message = inbox.getMessages().iterator().next();

		this.messageService.removeMessage(message);

		inbox = this.boxservices.findInbox(sender);
		trashbox = this.boxservices.findTashBox(sender);

		Assert.isTrue(trashbox.getMessages().contains(message));

		this.messageService.removeMessage(message);

		inbox = this.boxservices.findInbox(sender);
		trashbox = this.boxservices.findInbox(sender);

		Assert.isTrue(!trashbox.getMessages().contains(message));
	}

	@Test
	public void sendMessage() {
		Actor sender = this.actorservice.findByUsernames(Arrays.asList("admin1")).iterator().next();
		this.authenticate(sender.getUserAccount().getUsername());

		Message message = new Message();
		message.setBody("sample body");
		message.setSubject("query subject unique");
		message.setPriority(Message.NEUTRAL);

		Message saved = this.messageService.sendMessage(this.actorservice.findByUsernames(Arrays.asList("admin1", "handyWorker1")), message);

		Collection<Actor> actorsSends = this.actorservice.findByUsernames(Arrays.asList("admin1", "handyWorker1"));

		boolean result = true;

		for (Actor a : actorsSends) {
			Box inbox = this.boxservices.findInbox(a);
			//result = result && inbox.getMessages().contains(saved);
		}

		Actor self = this.actorservice.findSelf();

		Box outbox = this.boxservices.findOutbox(self);

		result = result && outbox.getMessages().contains(saved);

		Assert.isTrue(result);
	}

	@Test
	public void saveMessageTest() {
		Message message, saved;
		Collection<Message> messages;
		message = this.messageService.findAll().iterator().next();
		message.setVersion(57);
		saved = this.messageService.save(message);
		messages = this.messageService.findAll();
		Assert.isTrue(messages.contains(saved));
	}

	@Test
	public void findAllMessageTest() {
		Collection<Message> result;
		result = this.messageService.findAll();
		Assert.notNull(result);
	}

	@Test
	public void findOneMessageTest() {
		Message message = this.messageService.findAll().iterator().next();
		int messageId = message.getId();
		Assert.isTrue(messageId != 0);
		Message result;
		result = this.messageService.findOne(messageId);
		Assert.notNull(result);
	}

	@Test
	public void deleteMessageTest() {
		Actor sender = this.actorservice.findByUsernames(Arrays.asList("admin1")).iterator().next();
		this.authenticate(sender.getUserAccount().getUsername());

		Box inbox = this.boxservices.findInbox(sender);
		Message message = inbox.getMessages().iterator().next();

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(this.messageService.exists(message.getId()));

		this.messageService.delete(message);
	}

}
