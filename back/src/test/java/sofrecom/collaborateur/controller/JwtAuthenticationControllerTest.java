//package sofrecom.collaborateur.controller;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import java.util.Arrays;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import sofrecom.collaborateur.config.JwtAuthenticationEntryPoint;
//import sofrecom.collaborateur.config.JwtRequestFilter;
//import sofrecom.collaborateur.config.JwtTokenUtil;
//import sofrecom.collaborateur.model.DAOUser;
//import sofrecom.collaborateur.model.Objectif;
//import sofrecom.collaborateur.serviceImpl.JwtUserDetailsService;
//import sofrecom.collaborateur.serviceImpl.ObjectifService;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
//
//@WebMvcTest(JwtAuthenticationController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ContextConfiguration
//@WithUserDetails
//class JwtAuthenticationControllerTest {
//
//
//	
//	@MockBean
//	private ObjectifService objectifService;
//	
//	@MockBean
//	private JwtTokenUtil jwtTokenUtil;
//	@MockBean
//	private JwtUserDetailsService jwtUserDetailsService;
//	@MockBean
//	private JwtRequestFilter jwtRequestFilter;
//	@MockBean
//	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//	@Autowired
//	private MockMvc mockMvc;
//	
//	
//    @Autowired
//    private WebApplicationContext context;
//    
//	ObjectMapper om = new ObjectMapper();
//	
//	
//	   @Before
//	    public void setup() {
//		   mockMvc = MockMvcBuilders
//	                .webAppContextSetup(context)
//	                .apply(springSecurity()) 
//	                .build();
//	    }
//	   
//	   
//	@Test
//	void testSaveUser() throws Exception {
//		DAOUser mockeduser = new DAOUser();
//		mockeduser.setId(1L);
//
//		
//		
////		when(userDetailsService.findByUsername(Mockito.anyString())).thenReturn((null));
//		String jsonRequest = om.writeValueAsString(mockeduser);
//
////		mockMvc.perform(post("/auth/register").with(csrf()).with(user(jsonRequest)).content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
////				.andExpect(jsonPath("$.length()", is(6)))
////				.andExpect(jsonPath("$.id").value(1));
//		
//		mockMvc.perform(post("/auth/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
//		.andExpect(jsonPath("$.length()", is(6)))
//		.andExpect(jsonPath("$.id").value(1));
//		
//		
//	}
//	
//	
//
//}
