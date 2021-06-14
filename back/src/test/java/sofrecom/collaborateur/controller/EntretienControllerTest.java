package sofrecom.collaborateur.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import sofrecom.collaborateur.config.JwtAuthenticationEntryPoint;
import sofrecom.collaborateur.config.JwtRequestFilter;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Status;
import sofrecom.collaborateur.serviceImpl.EntretienService;
import sofrecom.collaborateur.serviceImpl.JwtUserDetailsService;


/****************************************************************************************************************
 * 
 * TESTING ENTRETIEN CONTROLLER REST APIs USING MockMvc and Mockito
 * 
 *************************************************************************************************************/

@WebMvcTest(EntretienController.class)
@AutoConfigureMockMvc(addFilters = false)
class EntretienControllerTest {

	@MockBean
	private EntretienService entretienService;
	@MockBean
	private JwtUserDetailsService jwtUserDetailsService;
	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper om = new ObjectMapper();

	String updateObjectifJson = "{\"id\":\"2\",\"designation\":\"obj2\",\"autoEvaluation\":\"\",\"commentaire\":\"\",\"evaluation\":\"\"}";
	String listObjectifsJson = "[{\"id\": 1,\"designation\": \"obj1\",\"autoEvaluation\": \"\",\"commentaire\": \"\",\"evaluation\": \"\"},"
			+ "{\"id\": 2,\"designation\": \"obj2\",\"autoEvaluation\": \"\",\"commentaire\": \"\",\"evaluation\": \"\"}]";

	@Test
	void testAddEntretien() throws Exception {

		Entretien entretien = new Entretien();
		entretien.setId(1L);
		entretien.setStatus(Status.EVALUATION);

		String jsonRequest = om.writeValueAsString(entretien);

		when(entretienService.addEntretien(Mockito.any(Entretien.class))).thenReturn(entretien);

		mockMvc.perform(post("/newEntretien").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(10))).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.status").value("EVALUATION"));

		Mockito.verify(entretienService).addEntretien(Mockito.any(Entretien.class));
	}

	@Test
	void testGetEIPsByManagerAndCompagne() throws Exception {
		Entretien entretien1 = new Entretien();
		Entretien entretien2 = new Entretien();

		entretien1.setId(1);
		entretien2.setId(2);

		when(entretienService.getEIPsByManagerAndCompagne(Mockito.anyLong()))
				.thenReturn(Arrays.asList(entretien1, entretien2));

		mockMvc.perform(get("/EIPs/1")).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(2))).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[1].id").value(2));

		Mockito.verify(entretienService).getEIPsByManagerAndCompagne(Mockito.anyLong());
	}

	@Test
	void testGetCollaborateurByEntretien() throws Exception {
		DAOUser user = new DAOUser();
		user.setEmail("oussema@gmail.com");
		user.setUsername("oussema");

		when(entretienService.getCollaborateurByEntretien(Mockito.anyLong())).thenReturn((user));

		mockMvc.perform(get("/findCollaborateurByEntretien/1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(11)))
				.andExpect(jsonPath("$.email").value("oussema@gmail.com"))
				.andExpect(jsonPath("$.username").value("oussema"));

		Mockito.verify(entretienService).getCollaborateurByEntretien(Mockito.anyLong());

	}

	@Test
	void testGetEntretienByCollaborateurAndCompagne() throws Exception {
		Entretien entretien = new Entretien();
		entretien.setId(1);

		when(entretienService.getEntretienByCollaborateurAndCompagne(Mockito.anyLong())).thenReturn((entretien));

		mockMvc.perform(get("/findEntretienByCollaborateur/1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(10)))
				.andExpect(jsonPath("$.id").value(1));

		Mockito.verify(entretienService).getEntretienByCollaborateurAndCompagne(Mockito.anyLong());

	}

}
