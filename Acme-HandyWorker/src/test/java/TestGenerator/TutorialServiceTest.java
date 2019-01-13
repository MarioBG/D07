package TestGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;
import services.HandyWorkerService;
import services.TutorialService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml", "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TutorialServiceTest extends AbstractTest{

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Test
	public void saveTutorialTest() {
		Tutorial tutorial, saved;
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();
		this.authenticate(h.getUserAccount().getUsername());
		Collection<Tutorial> tutorials;
		tutorial = this.tutorialService.findAll().iterator().next();
		final Date previous = tutorial.getUpdateTime();
		tutorial.setVersion(57);
		saved = this.tutorialService.save(tutorial);
		tutorials = this.tutorialService.findAll();
		Assert.isTrue(tutorials.contains(saved));
		Assert.isTrue(!previous.equals(saved.getUpdateTime()));
	}

	@Test
	public void createTutorialTest() {
		Tutorial tutorial, saved;
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();
		this.authenticate(h.getUserAccount().getUsername());
		Collection<Tutorial> tutorials;
		tutorial = new Tutorial();
		tutorial.setTitle("title");
		tutorial.setSummary("asdasd");
		tutorial.setPictures(new ArrayList<String>());
		tutorial.setSections(new ArrayList<Section>());
		saved = this.tutorialService.save(tutorial);
		tutorials = this.tutorialService.findAll();
		Assert.isTrue(tutorials.contains(saved));
	}

	@Test
	public void addSectionTest() {
		Tutorial tutorial, saved;
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();
		this.authenticate(h.getUserAccount().getUsername());
		Collection<Tutorial> tutorials;
		tutorial = this.tutorialService.findAll().iterator().next();
		Assert.notNull(tutorial);
		Section s = new Section();
		s.setPictures(new ArrayList<String>());
		s.setText("asdasd");
		s.setTitle("adasdasda");
		saved = this.tutorialService.addSection(tutorial, s);
		tutorials = this.tutorialService.findAll();
		Assert.isTrue(tutorials.contains(saved));
	}

	@Test
	public void findAllTutorialTest() {
		Collection<Tutorial> result;
		result = this.tutorialService.findAll();
		Assert.notNull(result);
	}

	@Test
	public void findOneTutorialTest() {
		final Tutorial tutorial = this.tutorialService.findAll().iterator().next();
		final int tutorialId = tutorial.getId();
		Assert.isTrue(tutorialId != 0);
		Tutorial result;
		result = this.tutorialService.findOne(tutorialId);
		Assert.notNull(result);
	}

	@Test
	public void deleteTutorialTest() {
		final HandyWorker h = this.handyWorkerService.findAll().iterator().next();
		this.authenticate(h.getUserAccount().getUsername());
		final Tutorial tutorial = this.tutorialService.findAll().iterator().next();
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() != 0);
		Assert.isTrue(this.tutorialService.exists(tutorial.getId()));
		this.tutorialService.delete(tutorial);
	}
}
