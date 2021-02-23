package sofrecom.collaborateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import sofrecom.collaborateur.config.JwtTokenUtil;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.DTOUser;
import sofrecom.collaborateur.model.JwtRequest;
import sofrecom.collaborateur.model.JwtResponse;
import sofrecom.collaborateur.serviceImpl.JwtUserDetailsService;



@RestController
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	
	//================================================================================
    // Authentication and Register 
    //================================================================================
	
	
	
	@PostMapping("/auth/register")
	public ResponseEntity<?> saveUser(@RequestBody DTOUser user) throws Exception {
		try {
			DAOUser userfounded = userDetailsService.findByUsername(user.getUsername());
			if (userfounded == null)
			return ResponseEntity.ok(userDetailsService.save(user));
			else 
			return new ResponseEntity<>("ALREADY REGISTRED!", HttpStatus.CONFLICT);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		}
		catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	
	
	//================================================================================
    // User Crud
    //================================================================================
	
}