package sofrecom.collaborateur.controller;

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
			newuser.setFullname(user.getFullnamee());
			newuser.setUsername(user.getUsername());
		}
		if (user2 != null) {
			newuser.setFullname(user2.getFullnamee());
			newuser.setEmail(user2.getEmail());
		}
		return newuser;
	}
}