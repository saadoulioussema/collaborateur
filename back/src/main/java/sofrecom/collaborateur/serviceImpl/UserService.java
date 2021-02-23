package sofrecom.collaborateur.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sofrecom.collaborateur.service.IUserService;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.repository.UserRepository;

import java.util.List;



@Component
public class UserService implements IUserService {


@Autowired
UserRepository userRepository;

@Override
public DAOUser getUserByUsername(String username) {
	return userRepository.findByUsername(username);
}


@Override
public DAOUser getUserByEmail(String email) {
	return userRepository.findByEmail(email);
}


@Override
public void deleteUser(Long userId) {
	userRepository.deleteById(userId);
}

@Override
public List<DAOUser> getAllUsers() {
	return (List<DAOUser>)userRepository.findAll();
}

@Override
public void deleteAllUsers() {
	userRepository.deleteAll(getAllUsers());
}
}