
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Box;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services
	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;


	// Constructor
	public MessageController() {
		super();
	}

	public ModelAndView createListModelAndView(String messageCode) {
		ModelAndView result;
		Actor actor;
		UserAccount userAccount;
		Collection<Message> messages;

		userAccount = LoginService.getPrincipal();

		actor = this.actorService.findByUserAccount(userAccount);
		messages = this.messageService.findMessagesFromActor(actor);

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("messageCode", messageCode);

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;

		message = this.messageService.create();

		MessageForm messageForm = this.construct(message);
		result = this.createEditModelAndView(messageForm);

		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Actor actor;
		UserAccount userAccount;
		Collection<Message> messages;

		userAccount = LoginService.getPrincipal();

		actor = this.actorService.findByUserAccount(userAccount);
		messages = this.messageService.findMessagesFromActor(actor);

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageForm messageForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else
			try {
				Message message = this.reconstruct(messageForm);
				this.messageService.sendMessage(message.getRecipients(), message);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageId) {
		ModelAndView result;
		try {
			Message message = this.messageService.findOne(messageId);
			this.messageService.removeMessage(message);
			result = new ModelAndView("redirect:/box/list.do");
		} catch (final Throwable oops) {
			result = this.createListModelAndView("message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);
		Assert.notNull(message);

		result = this.moveModelAndView(message);

		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		result = new ModelAndView("message/display");

		result.addObject("messageObject", message);

		return result;

	}

	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public ModelAndView move(final Message message, Box dst, final BindingResult binding) {
		ModelAndView result;
		try {
			this.messageService.moveTo(dst, message);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.moveModelAndView(message, "message.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String messageCode) {
		ModelAndView result;

		if (messageForm.getId() != 0)
			result = new ModelAndView("message/edit");
		else
			result = new ModelAndView("message/send");

		result.addObject("messageForm", messageForm);
		result.addObject("allActors", this.actorService.findAll());
		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView moveModelAndView(final Message message) {
		ModelAndView result;

		result = this.moveModelAndView(message, null);

		return result;
	}

	protected ModelAndView moveModelAndView(final Message message, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("message/move");

		result.addObject("message", message);
		result.addObject("messageCode", messageCode);

		return result;
	}

	private MessageForm construct(Message message) {
		MessageForm ans = new MessageForm();
		ans.setBody(message.getBody());
		ans.setId(message.getId());
		ans.setPriority(message.getPriority());
		ans.setRecipientIds(new ArrayList<Integer>());
		ans.setSubject(message.getSubject());
		return ans;
	}

	private Message reconstruct(MessageForm messageForm) {
		Message msg = new Message();
		msg.setBody(messageForm.getBody());
		msg.setId(messageForm.getId());
		msg.setMoment(new Date(System.currentTimeMillis() - 1));
		msg.setPriority(messageForm.getPriority());
		ArrayList<Actor> recipients = new ArrayList<Actor>();
		for (Integer id : messageForm.getRecipientIds())
			recipients.add(this.actorService.findByUserAccountId(id));
		msg.setRecipients(recipients);
		msg.setSender(this.actorService.findByPrincipal());
		msg.setSubject(messageForm.getSubject());
		if (messageForm.getId() == 0)
			msg.setVersion(0);
		else
			msg.setVersion(this.messageService.findOne(messageForm.getId()).getVersion());
		return msg;
	}
}
