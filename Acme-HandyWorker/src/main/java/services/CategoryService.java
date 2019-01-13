package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;
import domain.FixUpTask;
import repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	public Category save(Category entity) {
		return categoryRepository.save(entity);
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category findOne(Integer id) {
		return categoryRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		return categoryRepository.exists(id);
	}

	public void delete(Integer id) {
		categoryRepository.delete(id);
	}

	public Category create() {
		Category res = new Category();
		return res;
	}

	public void assignParentCategoryFixUpTask(Category category) {
		Collection<FixUpTask> fixUpTasks = fixUpTaskService.findFixUpTasksByCategory(category);
		for(FixUpTask f:fixUpTasks) {
			if(category.getName()!="Category") {
				f.setCategory(category.getParentCategory());
			}
		}
		
	}
	
	public Collection<Category> findChildrenCategories(Category category){
		Collection<Category> res = new LinkedList<>();
		res.addAll(categoryRepository.findChildrenCategories(category.getId()));
		return res;
	}
	
	public void assingParentCategory(Category category) {
		Collection<Category> childCategories = this.findChildrenCategories(category);
		if(category.getName()!="Category") {
			for(Category c:childCategories) {
				c.setParentCategory(category.getParentCategory());
			}
		}
	}
	
	
	
}
