package sofrecom.collaborateur.service;

import java.util.List;


import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.DTOUser;


public interface IUserService {
	List<DAOUser> getAllUsers();
	public DAOUser getUserByUsername(String username);
	public DAOUser getUserByEmail(String email);
	public DAOUser getUserById(long id);
	void deleteUser(Long userId);
	public void deleteAllUsers();
	
	public List<DTOUser> getUsersByManagerAndCompagne(long idManager);
}
