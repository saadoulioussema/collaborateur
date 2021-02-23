package sofrecom.collaborateur.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sofrecom.collaborateur.model.DAOUser;

@Repository
public interface UserDao extends CrudRepository<DAOUser, Long> {
	DAOUser findByUsername(String username);
}