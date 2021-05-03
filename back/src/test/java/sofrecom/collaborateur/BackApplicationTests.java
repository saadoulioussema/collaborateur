package sofrecom.collaborateur;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.repository.ObjectifRepository;
import sofrecom.collaborateur.service.IObjectifService;


@RunWith(SpringRunner.class)
@SpringBootTest()
class BackApplicationTests {

	@Autowired
	ObjectifRepository objectifRepo;
	
	@Autowired
	IObjectifService objectifService;
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testAddObjectif() {
		int nbr = (int) objectifRepo.count();
		Objectif objectif = new Objectif();
		Objectif returnedObjectif = objectifService.addObjectif(objectif);
		assertNotNull(returnedObjectif);
		assertEquals(nbr + 1, objectifRepo.count());
	}
}
