package sofrecom.collaborateur.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import sofrecom.collaborateur.config.JwtAuthenticationEntryPoint;
import sofrecom.collaborateur.config.JwtRequestFilter;
import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.serviceImpl.JwtUserDetailsService;
import sofrecom.collaborateur.serviceImpl.ObjectifService;

/****************************************************************************************************************
 * 
 * TESTING OBJECTIF CONTROLLER REST APIs USING MockMvc and Mockito
 * 
 *************************************************************************************************************/

@WebMvcTest(ObjectifController.class)
@AutoConfigureMockMvc(addFilters = false)
class ObjectifControllerTest {

	

	
	
	@MockBean
	private ObjectifService objectifService;
	@MockBean
	private JwtUserDetailsService jwtUserDetailsService;
	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper om = new ObjectMapper();

	/****************************************************************************************************************
	 * 
	 * NO AUTOWIRED mockMvc ( USED IN TESTING OBJECTIFS SERVICES )
	 * 
	 * 
	 * 
	 * Old way @SpringBootTest()
	 * 
	 * @Autowired private WebApplicationContext webApplicationContext;
	 * 
	 *            private MockMvc mockMvc;
	 * @Before public void setup() { mockMvc =
	 *         MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	 *         MockitoAnnotations.openMocks(this);
	 * 
	 *         }
	 * 
	 * 
	 *         WHEN WE USE THIS METHOD ==> NULL POINER EXCEPTION :
	 *         mockMvc.perform(get("/getAllObjectifs")).andExpect(status().isOk());
	 *************************************************************************************************************/

	String updateObjectifJson = "{\"id\":\"2\",\"designation\":\"obj2\",\"autoEvaluation\":\"\",\"commentaire\":\"\",\"evaluation\":\"\"}";
	String listObjectifsJson = "[{\"id\": 1,\"designation\": \"obj1\",\"autoEvaluation\": \"\",\"commentaire\": \"\",\"evaluation\": \"\"},"
			+ "{\"id\": 2,\"designation\": \"obj2\",\"autoEvaluation\": \"\",\"commentaire\": \"\",\"evaluation\": \"\"}]";


	@Test
	void testGetObjectifListByEntretienAndEntretienCompagne() throws Exception {
		Objectif objectif1 = new Objectif(1, "obj1", "", "objectif dépassé commentaire", "Objectif dépassé");
		Objectif objectif2 = new Objectif(2, "obj2", "", "objectif dépassé commentaire", "Objectif dépassé");

		when(objectifService.getObjectifListByEntretienAndEntretienCompagne(Mockito.anyLong()))
				.thenReturn(Arrays.asList(objectif1, objectif2));

		mockMvc.perform(get("/entretien/1/objectifs")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[0].designation").value("obj1"))
				.andExpect(jsonPath("$[0].autoEvaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$[0].commentaire").value("objectif dépassé commentaire"))
				.andExpect(jsonPath("$[0].evaluation").value("")).andExpect(jsonPath("$[1].designation").value("obj2"))
				.andExpect(jsonPath("$[1].autoEvaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$[1].commentaire").value("objectif dépassé commentaire"))
				.andExpect(jsonPath("$[1].evaluation").value(""));

		Mockito.verify(objectifService).getObjectifListByEntretienAndEntretienCompagne(Mockito.anyLong());
	}

	@Test
	void testAutoEvaluateObjectif() throws Exception {
		Objectif objectif = new Objectif(1, "obj1", "", "objectif dépassé commentaire", "Objectif dépassé");
		when(objectifService.autoEvaluateObjectif(Mockito.any(Objectif.class))).thenReturn((objectif));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/autoEvaluateObjectif")
				.accept(MediaType.APPLICATION_JSON).content(updateObjectifJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(6))).andExpect(jsonPath("$.designation").value("obj1"))
				.andExpect(jsonPath("$.autoEvaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$.commentaire").value("objectif dépassé commentaire"))
				.andExpect(jsonPath("$.evaluation").value(""));

		Mockito.verify(objectifService, times(2)).autoEvaluateObjectif(Mockito.any(Objectif.class));
	}

	@Test
	void testEvaluateObjectif() throws Exception {
		Objectif objectif = new Objectif(1, "obj1", "", "objectif depasse commentaire", "Objectif depasse");
		when(objectifService.evaluateObjectif(Mockito.any(Objectif.class))).thenReturn((objectif));

		String jsonRequest = om.writeValueAsString(objectif);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/evaluateObjectif")
				.accept(MediaType.APPLICATION_JSON).content(jsonRequest).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		Objectif recievedObjectif = om.readValue(response.getContentAsString(), Objectif.class);

		assertEquals("obj1", recievedObjectif.getDesignation());
		assertEquals("Objectif depasse", recievedObjectif.getAutoEvaluation());
		assertEquals("objectif depasse commentaire", recievedObjectif.getCommentaire());
		assertEquals("", recievedObjectif.getEvaluation());

		Mockito.verify(objectifService).evaluateObjectif(Mockito.any(Objectif.class));

	}

	@Test
	void testNewObjectif() throws Exception {
		Objectif objectif1 = new Objectif(1, "obj1", "Objectif dépassé", "objectif dépassé commentaire",
				"Objectif dépassé");
		Objectif objectif2 = new Objectif(2, "obj2", "Objectif dépassé", "objectif dépassé commentaire",
				"Objectif dépassé");

		List<Objectif> mockedList = new ArrayList<Objectif>();

		mockedList.add(objectif1);
		mockedList.add(objectif2);

		String jsonRequest = om.writeValueAsString(mockedList);

		when(objectifService.newObjectif(Mockito.anyList(), Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Arrays.asList(objectif1, objectif2));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/newObjectifs/2/1")
				.accept(MediaType.APPLICATION_JSON).content(jsonRequest).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
//		assertEquals(listObjectifsJson, response.getContentAsString());
		Mockito.verify(objectifService).newObjectif(Mockito.anyList(), Mockito.anyLong(), Mockito.anyLong());
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
		Mockito.verify(objectifService).getAllObjectifs();
	}

	@Test
	public void testAddObjectif() throws Exception {
		Objectif objectif = new Objectif(1, "obj1", "", "objectif depasse commentaire", "Objectif depasse");
		when(objectifService.addObjectif(Mockito.any(Objectif.class))).thenReturn((objectif));

		String jsonRequest = om.writeValueAsString(objectif);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addObjectif").accept(MediaType.APPLICATION_JSON)
				.content(jsonRequest).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		Objectif recievedObjectif = om.readValue(response.getContentAsString(), Objectif.class);

		assertEquals("obj1", recievedObjectif.getDesignation());
		assertEquals("Objectif depasse", recievedObjectif.getAutoEvaluation());
		assertEquals("objectif depasse commentaire", recievedObjectif.getCommentaire());
		assertEquals("", recievedObjectif.getEvaluation());
		Mockito.verify(objectifService).addObjectif(Mockito.any(Objectif.class));

	}

	@Test
	public void testAddObjectif2() throws Exception {
		Objectif objectif = new Objectif(1, "obj1", "", "objectif dépassé commentaire", "Objectif dépassé");
		when(objectifService.addObjectif(Mockito.any(Objectif.class))).thenReturn((objectif));
		String jsonRequest = om.writeValueAsString(objectif);

		mockMvc.perform(post("/addObjectif").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(6))).andExpect(jsonPath("$.designation").value("obj1"))
				.andExpect(jsonPath("$.autoEvaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$.commentaire").value("objectif dépassé commentaire"))
				.andExpect(jsonPath("$.evaluation").value(""));


		Mockito.verify(objectifService, times(1)).addObjectif(Mockito.any(Objectif.class));
	}

	@Test
	public void testAddObjectif3() throws Exception {
		Objectif mockObjectif = new Objectif(1, "obj1", "", "", "", null);
		Mockito.when(objectifService.addObjectif(Mockito.any(Objectif.class))).thenReturn(mockObjectif);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addObjectif2").accept(MediaType.APPLICATION_JSON)
				.content(updateObjectifJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/addObjectif2", response.getHeader(HttpHeaders.LOCATION));

	}

	@Test
	public void testGetObjectif() throws Exception {
		Objectif mockObjectif = new Objectif();
		mockObjectif.setId(1L);
		mockObjectif.setDesignation("obj1");
		mockObjectif.setAutoEvaluation("");
		mockObjectif.setCommentaire("");
		mockObjectif.setEvaluation("");

		when(objectifService.getObjectif(Mockito.anyLong())).thenReturn(mockObjectif);

		mockMvc.perform(get("/getObjectif/1")).andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.length()", is(6)))
				.andExpect(jsonPath("$.designation").value("obj1")).andExpect(jsonPath("$.autoEvaluation").value(""))
				.andExpect(jsonPath("$.commentaire").value("")).andExpect(jsonPath("$.evaluation").value(""))
				.andExpect(jsonPath("$.entretien").doesNotExist());

		Mockito.verify(objectifService).getObjectif(Mockito.anyLong());

	}

}
