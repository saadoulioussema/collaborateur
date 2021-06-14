package sofrecom.collaborateur;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.ObjectifRepository;
import sofrecom.collaborateur.serviceImpl.ObjectifService;

/****************************************************************************************************************
 * 
 * TESTING OBJECTIF REST APIs USING MockMvc and Mockito
 * 
 *************************************************************************************************************/

public class TestObjectif2 extends BackApplicationTests {

	@InjectMocks
	private ObjectifService objectifService;

	@MockBean
	private ObjectifRepository objectifRepo;
	
	@MockBean
	private EntretienRepository entretienRepo;
	

	String exampleObjectifJson = "{\"designation\":\"obj200\"}";

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.openMocks(this);

	}

//	@Test
//	public void testAddObjectif() throws Exception {
//		Objectif mockObjectif = new Objectif(1, "obj1", "", "", "", null);
//		Mockito.when(objectifService.addObjectif(Mockito.any(Objectif.class))).thenReturn(mockObjectif);
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addObjectif").accept(MediaType.APPLICATION_JSON)
//				.content(exampleObjectifJson).contentType(MediaType.APPLICATION_JSON);
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//		assertEquals("http://localhost/addObjectif", response.getHeader(HttpHeaders.LOCATION));
//
//	}

	@Test
	public void testAddObjectif() throws Exception {
		Objectif mockObjectif = new Objectif(1, "obj1", "", "", "", null);
		Mockito.when(objectifRepo.save(Mockito.any(Objectif.class))).thenReturn(mockObjectif);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addObjectif").accept(MediaType.APPLICATION_JSON)
				.content(exampleObjectifJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/addObjectif", response.getHeader(HttpHeaders.LOCATION));

	}

//	@Test
//	public void testGetObjectif() throws Exception {
//		Objectif mockObjectif = new Objectif();
//		mockObjectif.setId(1L);
//		mockObjectif.setDesignation("obj1");
//		mockObjectif.setAutoEvaluation("");
//		mockObjectif.setCommentaire("");
//		mockObjectif.setEvaluation("");
//
//		 when(objectifService.getObjectif(Mockito.anyLong())).thenReturn(mockObjectif); 
//		Objectif objectif = objectifService.getObjectif(Mockito.anyLong());
//		
//		Mockito.verify(objectifService).getObjectif(Mockito.anyLong());
//		assertNotNull(objectif);
//		assertEquals("obj1",objectif.getDesignation());
//
//	}

	@Test
	public void testGetObjectif() throws Exception {
		Objectif mockObjectif = new Objectif();
		mockObjectif.setId(1L);
		mockObjectif.setDesignation("obj1");
		mockObjectif.setAutoEvaluation("");
		mockObjectif.setCommentaire("");
		mockObjectif.setEvaluation("");

		when(objectifRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(mockObjectif));
		Objectif objectif = objectifService.getObjectif(Mockito.anyLong());

		Mockito.verify(objectifRepo).findById(Mockito.anyLong());
		assertNotNull(objectif);
		assertEquals("obj1", objectif.getDesignation());

	}

	@Test
	public void testGetAllObjectifs() throws Exception {
		Objectif objectif1 = new Objectif(1, "obj1", "Objectif dépassé", "objectif dépassé commentaire",
				"Objectif dépassé");
		Objectif objectif2 = new Objectif(2, "obj2", "Objectif dépassé", "objectif dépassé commentaire",
				"Objectif dépassé");
		when(objectifService.getAllObjectifs()).thenReturn(Arrays.asList(objectif1, objectif2));

		mockMvc.perform(get("/getAllObjectifs")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[0].designation").value("obj1"))
				.andExpect(jsonPath("$[0].autoEvaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$[0].commentaire").value("objectif dépassé commentaire"))
				.andExpect(jsonPath("$[0].evaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$[1].designation").value("obj2"))
				.andExpect(jsonPath("$[1].autoEvaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$[1].commentaire").value("objectif dépassé commentaire"))
				.andExpect(jsonPath("$[1].evaluation").value("Objectif dépassé"));
	}

	@Test
	public void testGetObjectifListByEntretienAndEntretienCompagne_NullException() {
		when(entretienRepo.findById(Mockito.anyLong())).thenReturn(null);
		assertThrows(NullPointerException.class, () -> {
			objectifService.getObjectifListByEntretienAndEntretienCompagne(Mockito.anyLong());
		});
		
		Mockito.verify(entretienRepo).findById(Mockito.anyLong());
	}
}
