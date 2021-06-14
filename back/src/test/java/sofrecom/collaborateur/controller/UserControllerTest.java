package sofrecom.collaborateur.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import sofrecom.collaborateur.config.JwtAuthenticationEntryPoint;
import sofrecom.collaborateur.config.JwtRequestFilter;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Fonction;
import sofrecom.collaborateur.serviceImpl.JwtUserDetailsService;
import sofrecom.collaborateur.serviceImpl.UserService;

/****************************************************************************************************************
 * 
 * TESTING USER CONTROLLER REST APIs USING MockMvc and Mockito
 * 
 *************************************************************************************************************/

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)

class UserControllerTest {

	@MockBean
	private UserService userService;
	@MockBean
	private JwtUserDetailsService jwtUserDetailsService;
	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private MockMvc mockMvc;


	@Test
	void testGetUserByUsername() throws Exception {
		DAOUser mockeduser = new DAOUser();
		mockeduser.setId(1L);
		mockeduser.setUsername("oussemasaadouli");
		mockeduser.setFullname("SAADOULI oussema");
		Fonction fonction = new Fonction();
		fonction.setId(1L);
		mockeduser.setFonction(fonction);

		when(userService.getUserByUsername(Mockito.anyString())).thenReturn((mockeduser));
		when(userService.getUserByEmail(Mockito.anyString())).thenReturn((null));

		mockMvc.perform(get("/findUser/oussemasaadouli/oussema@sofrecom.com")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(9)))
				.andExpect(jsonPath("$.username").value("oussemasaadouli"))
				.andExpect(jsonPath("$.fullname").value("SAADOULI oussema"))
				.andExpect(jsonPath("$.fonctionId").value(1));

		Mockito.verify(userService).getUserByEmail(Mockito.anyString());
	}
	
	
	@Test
	void testGetUserByUsername2() throws Exception {
		DAOUser mockeduser = new DAOUser();
		mockeduser.setId(1L);
		mockeduser.setEmail("oussema@sofercom.com");
		mockeduser.setFullname("SAADOULI oussema");
		Fonction fonction = new Fonction();
		fonction.setId(1L);
		mockeduser.setFonction(fonction);

		when(userService.getUserByUsername(Mockito.anyString())).thenReturn((null));
		when(userService.getUserByEmail(Mockito.anyString())).thenReturn((mockeduser));

		mockMvc.perform(get("/findUser/oussemasaadouli/oussema@sofrecom.com")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(9)))
				.andExpect(jsonPath("$.email").value("oussema@sofercom.com"))
				.andExpect(jsonPath("$.fullname").value("SAADOULI oussema"))
				.andExpect(jsonPath("$.fonctionId").value(1));

		Mockito.verify(userService).getUserByEmail(Mockito.anyString());
	}
	
	@Test
	void testGetUserById() throws Exception {
		DAOUser mockeduser = new DAOUser();
		mockeduser.setId(1L);
		mockeduser.setUsername("oussemasaadouli");
		mockeduser.setFullname("SAADOULI oussema");

		when(userService.getUserById(Mockito.anyLong())).thenReturn((mockeduser));


		mockMvc.perform(get("/findUser/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.length()", is(11)))
				.andExpect(jsonPath("$.username").value("oussemasaadouli"))
				.andExpect(jsonPath("$.fullname").value("SAADOULI oussema"));

		Mockito.verify(userService).getUserById(Mockito.anyLong());
	}

}
