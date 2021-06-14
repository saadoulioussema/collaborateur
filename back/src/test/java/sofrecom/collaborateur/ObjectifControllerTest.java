package sofrecom.collaborateur;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.repository.ObjectifRepository;
import sofrecom.collaborateur.service.IObjectifService;

public class ObjectifControllerTest extends BackApplicationTests {

	@Autowired
	private IObjectifService objectifService;

	@MockBean
	private ObjectifRepository objectifRepo;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@SuppressWarnings("unused")
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}


	@Test
	public void getById() {
		
		Objectif objectif = new Objectif(3, "obj2", "Objectif dépassé", "objectif dépassé validé", "Objectif dépassé");
		when(objectifRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(objectif));
		Object returnedObject = objectifService.getObjectif(3L);
		
//		check if objectif repo findById is called we use verify 
		Mockito.verify(objectifRepo).findById(Mockito.anyLong());
		assertNotNull(returnedObject);
		assertEquals(3, objectifRepo.findById(2l).get().getId());

	}

	@Test
	public void testGetAllObjectifs() throws Exception {
		when(objectifRepo.findAll()).thenReturn(Stream
				.of(new Objectif(1, "", "", "", ""), new Objectif(2, "", "", "", "")).collect(Collectors.toList()));
		assertEquals(2, objectifService.getAllObjectifs().size());
	}

}
