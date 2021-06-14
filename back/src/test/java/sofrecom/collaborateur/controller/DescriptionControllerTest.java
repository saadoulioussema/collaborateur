package sofrecom.collaborateur.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import sofrecom.collaborateur.model.Description;
import sofrecom.collaborateur.serviceImpl.DescriptionService;
import sofrecom.collaborateur.serviceImpl.JwtUserDetailsService;

/****************************************************************************************************************
 * 
 * TESTING DESCRIPTION CONTROLLER REST APIs USING MockMvc and Mockito
 * 
 *************************************************************************************************************/

@WebMvcTest(DescriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
class DescriptionControllerTest {

	@MockBean
	private DescriptionService descService;
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
	void testAddEvaluationByUserAndCompetence() throws Exception {

		Description desc = new Description();
		desc.setDescription("exemple description");

		String jsonRequest = om.writeValueAsString(desc);

		when(descService.addDescription(Mockito.anyLong(), Mockito.any(Description.class))).thenReturn(desc);

		mockMvc.perform(post("/newDescription/1").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$.description").value("exemple description"));

		Mockito.verify(descService).addDescription(Mockito.anyLong(), Mockito.any(Description.class));
	}

	@Test
	void testUpdateDescriptionLevel() throws Exception {
		Description desc = new Description();
		desc.setDescription("exemple description");

		when(descService.updateDescriptionLevel(Mockito.anyLong(), Mockito.any(Description.class))).thenReturn((desc));

		String jsonRequest = om.writeValueAsString(desc);
		mockMvc.perform(put("/updateDescriptionLevel/1").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$.description").value("exemple description"));

		Mockito.verify(descService).updateDescriptionLevel(Mockito.anyLong(), Mockito.any(Description.class));
	}

	@Test
	void testGetDescriptionList() throws Exception {
		Description desc1 = new Description();
		Description desc2 = new Description();

		desc1.setDescription("exemple1");
		desc2.setDescription("exemple2");

		when(descService.getDescriptionList()).thenReturn(Arrays.asList(desc1, desc2));

		mockMvc.perform(get("/descriptionList"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(2))).andExpect(jsonPath("$[0].description").value("exemple1"))
				.andExpect(jsonPath("$[1].description").value("exemple2"));

		Mockito.verify(descService).getDescriptionList();
	}

	@Test
	void testGetDescription() throws Exception {
		Description desc = new Description();
		
		desc.setDescription("exemple description");

		when(descService.getDescription(Mockito.anyLong(),Mockito.anyLong())).thenReturn((desc));

		mockMvc.perform(get("/description/1/2")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$.description").value("exemple description"));


		Mockito.verify(descService).getDescription(Mockito.anyLong(),Mockito.anyLong());

	}
	
	@Test
	void testGetDescriptionByCompetence() throws Exception {
		Description desc1 = new Description();
		Description desc2 = new Description();

		desc1.setDescription("exemple1");
		desc2.setDescription("exemple2");

		when(descService.getDescriptionByCompetence(Mockito.anyLong())).thenReturn(Arrays.asList(desc1, desc2));

		mockMvc.perform(get("/descriptions/1"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$[0].description").value("exemple1"))
				.andExpect(jsonPath("$[1].description").value("exemple2"));

		Mockito.verify(descService).getDescriptionByCompetence(Mockito.anyLong());
	}

}
