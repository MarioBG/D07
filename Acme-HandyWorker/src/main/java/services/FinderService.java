
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	//Managed repository
	@Autowired
	private FinderRepository		finderRepository;

	//Supporting services
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	//Constructor
	public FinderService() {
		super();
	}

	//Simple CRUD methods
	public Finder create() {

		Finder finder = new Finder();
		return finder;

	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);

		return result;
	}

	public Finder save(Finder finder) {
		Assert.notNull(finder);

		Finder result;

		finder.setLastSearchDate(new Date(System.currentTimeMillis()));

		//		Collection<Trip> trips = finder.getTrips();
		//		finder.setTrips(trips);

		result = this.finderRepository.save(finder);
		return result;
	}

	public void delete(Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);

		this.finderRepository.delete(finder);
	}

	// OTHER BUSSINES METHODS -------------------

	public Finder findForHandyWorkerId(int handyWorkerId) {
		return this.finderRepository.findForHandyWorkerId(handyWorkerId);
	}

	public Boolean checkSearchIsInCache(Finder finder) {

		HandyWorker hw;
		Finder lastFinder;
		Long passedTime;
		Long diffHours;
		Boolean isInCache = true;
		Date now = new Date(System.currentTimeMillis());
		Long nowMS = now.getTime();
		Date search = finder.getLastSearchDate();
		Long searchMS = search.getTime();

		//Calculamos cuanto ha pasado desde la última búsqueda
		hw = (HandyWorker) this.actorService.findByPrincipal();
		lastFinder = this.finderRepository.findForHandyWorkerId(hw.getId());
		passedTime = (nowMS - searchMS) / 1000; //Pasamos a segundos

		diffHours = passedTime / 3600; //Pasamos a horas

		if ((finder.getEndDate() != null && !finder.getEndDate().equals(lastFinder.getEndDate())) || (finder.getStartDate() != null && !finder.getStartDate().equals(lastFinder.getStartDate()))
			|| (finder.getKeyWord() != null && !finder.getKeyWord().equals(lastFinder.getKeyWord())) || (finder.getMaxPrice() != null && !finder.getMaxPrice().equals(lastFinder.getMaxPrice()))
			|| (finder.getMinPrice() != null && !finder.getMinPrice().equals(lastFinder.getMinPrice())) || diffHours > this.configurationService.findConfiguration().getFinderCached())
			isInCache = false;

		return isInCache;

	}

	//	public Finder transformFinder(Finder finder){
	//		
	//		if(finder.getMinPrice()==null){
	//			finder.setMinPrice(0.);
	//		}
	//		
	//		if(finder.getMaxPrice()==null){
	//			finder.setMPrice(999999.);
	//		}
	//		
	//		if(finder.getStartDate()==null){
	//			Date startDate = new Date();
	//			String startDateString = "06/06/1000";
	//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	//			try {
	//			    startDate = df.parse(startDateString);
	//			    finder.setStartDate(startDate);
	//			} catch (ParseException e) {
	//			    e.printStackTrace();
	//			}
	//		}
	//		
	//		if(finder.getEndDate()==null){
	//			Date endDate = new Date();
	//			String endDateString = "06/06/3000";
	//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	//			try {
	//			    endDate = df.parse(endDateString);
	//			    finder.setEndDate(endDate);
	//			} catch (ParseException e) {
	//			    e.printStackTrace();
	//			}
	//		}
	//		
	//		if(finder.getKeyWord()==null){
	//			finder.setKeyWord("");
	//		}
	//		
	//		return finder;
	//	}

	public void saveNewResults(Finder finder, Collection<FixUpTask> fixUpTasks) {
		finder.setFixUpTasks(fixUpTasks);
	}

}
