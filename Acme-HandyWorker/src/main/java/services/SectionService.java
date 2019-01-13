package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Section;
import repositories.SectionRepository;

@Service
@Transactional
public class SectionService {

	// Managed Repository
	@Autowired
	private SectionRepository sectionRepository;

	// Constructor
	public SectionService() {
		super();
	}

	// Simple CRUD methods
	public Section create() {
		Section result;

		result = new Section();
		result.setPictures(new ArrayList<String>());

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;
		Assert.notNull(this.sectionRepository);
		result = this.sectionRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Section findOne(final Integer id) {
		Section res;
		res = this.sectionRepository.findOne(id);
		return res;
	}

	public Section save(Section c) {
		Section res;
		res = this.sectionRepository.save(c);
		return res;
	}

	public boolean exists(final int id) {
		return this.sectionRepository.exists(id);
	}

	public void delete(Collection<Section> sections) {
		Assert.notNull(sections);
		if (sections.size() > 0)
			for (Section section : sections)
				this.sectionRepository.delete(section);
		sections = null;
	}

	public void delete(Section entity) {
		sectionRepository.delete(entity);
	}

	// Other Business

	public List<Section> getSectionsOrderedFromTutorial(final int tutorialId) {
		return this.sectionRepository.getSectionsOrderedFromTutorial(tutorialId);
	}
}
