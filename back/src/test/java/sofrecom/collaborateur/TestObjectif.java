package sofrecom.collaborateur;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;




public class TestObjectif extends BackApplicationTests {

	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;


	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/****************************************************************************************************************
	 * 
	 * TESTING AUTO EVALUATE METHODS
	 * 
	 *************************************************************************************************************/



//	@Test
//	public void testAutoEvaluateObjectif() throws Exception {
//		mockMvc.perform(get("/autoEvaluateObjectif")).andExpect(status().isOk())
//				.andExpect(content().contentType("application/json"))
//			    .andExpect(jsonPath("$.designation").value("obj2"))
//				.andExpect(jsonPath("$.evaluation").value("Objectif dépassé")).andExpect(jsonPath("$.commentaire").value("objectif dépassé validé "));
//
//	}

	@Test
	public void testObjectif() throws Exception {
		mockMvc.perform(get("/getObjectif/2")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
//				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id").value("2")).andExpect(jsonPath("$.designation").value("obj2"))
				.andExpect(jsonPath("$.evaluation").value("Objectif dépassé"))
				.andExpect(jsonPath("$.commentaire").value("objectif dépassé validé "));

	}

}
