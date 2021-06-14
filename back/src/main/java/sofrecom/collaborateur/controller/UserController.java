package sofrecom.collaborateur.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.DTOUser;
import sofrecom.collaborateur.service.IUserService;

@RestController
public class UserController {

	@Autowired
	IUserService userService;
	

	@GetMapping("findUser/{username}/{email}")
	public DTOUser getUserByUsername(@PathVariable("username") String username, @PathVariable("email") String email) {
		// to avoid securtiy issue cannot permit a simple user to fetch user data : return DTOUser
		DTOUser newuser = new DTOUser();
		DAOUser user = userService.getUserByUsername(username);
		DAOUser user2 = userService.getUserByEmail(email);
	
		if (user != null) {
			if ( user.getManager()!= null) {
				newuser.setManagerId(user.getManager().getId());
			}
			else {
				newuser.setManagerId(user.getId());
			}
			newuser.setId(user.getId());
			newuser.setFullname(user.getFullname());
			newuser.setUsername(user.getUsername());
			newuser.setFonctionId(user.getFonction().getId());
		}

		if (user2 != null) {
			if ( user2.getManager()!= null) {
				newuser.setManagerId(user2.getManager().getId());
			}
			else {
				newuser.setManagerId(user2.getId());
			}
			newuser.setId(user2.getId());
			newuser.setFullname(user2.getFullname());
			newuser.setEmail(user2.getEmail());
			newuser.setFonctionId(user2.getFonction().getId());
		}
		return newuser;
	}
	
	@GetMapping("findUser/{id}")
	public DAOUser getUserById(@PathVariable("id") long idUser) {
		return 	userService.getUserById(idUser);

	}	
	
	
	@GetMapping("equipeEnCours/{idManager}")
	public List<DTOUser> getUsersByManagerAndCompagne(@PathVariable("idManager") long idManager) {
		return userService.getUsersByManagerAndCompagne(idManager);
	}
	
}