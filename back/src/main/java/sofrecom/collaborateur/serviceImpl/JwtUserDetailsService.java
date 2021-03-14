package sofrecom.collaborateur.serviceImpl;


import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.repository.UserRepository;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.DTOUser;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		DAOUser user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}
	
	public DAOUser findByUsername(String username) {
		DAOUser user = userRepo.findByUsername(username);
		return user ;
		
	}
	
	
	public DAOUser save(DTOUser user) throws ParseException {
		DAOUser newUser = new DAOUser();
		newUser.setFullname(user.getFullname());
		newUser.setEmail(user.getEmail());
		newUser.setMatricule(user.getMatricule());
	    Date dateConverted=new SimpleDateFormat("dd/MM/yyyy").parse(user.getDateIntegration());  
		newUser.setDateIntegration(dateConverted);	
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepo.save(newUser);		
		
	    
	    
	}	
}