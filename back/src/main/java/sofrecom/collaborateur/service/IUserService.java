package sofrecom.collaborateur.service;

import java.util.List;


import sofrecom.collaborateur.model.DAOUser;


public interface IUserService {
	List<DAOUser> getAllUsers();
	public DAOUser getUserByUsername(String username);
	public DAOUser getUserByEmail(String email);
	public DAOUser getUserById(long id);
	void deleteUser(Long userId);
	public void deleteAllUsers();
}
