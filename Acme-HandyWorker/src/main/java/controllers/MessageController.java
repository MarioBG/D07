package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Box;
import domain.Message;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController  extends AbstractController{
	
	// Services
		@Autowired
		private ActorService	actorService;

		@Autowired
		private MessageService	messageService;
		
		// Constructor
		public MessageController() {
			super();
		}
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Message message;

			message = this.messageService.create();

			result = this.createEditModelAndView(message);

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
		public ModelAndView save(@Valid @ModelAttribute(value = "message") final Message message, final BindingResult binding, @RequestParam(value = "usernames") Collection<String> usernames) {
			ModelAndView result;
			if (binding.hasErrors())
				result = this.createEditModelAndView(message);
			else
				try {
					this.messageService.sendMessage(usernames, message);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(message, "message.commit.error");
				}

			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(final Message message, final BindingResult binding) {
			ModelAndView result;
			try {
				this.messageService.removeMessage(message);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
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
		
		protected ModelAndView createEditModelAndView(final Message message) {
			ModelAndView result;

			result = this.createEditModelAndView(message, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
			ModelAndView result;
			
			if(message.getId() != 0) {
				result = new ModelAndView("message/edit");
			} else {
				result = new ModelAndView("message/create");
			}
			
			result.addObject("message", messageCode);
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
	}
