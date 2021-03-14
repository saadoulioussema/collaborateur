package sofrecom.collaborateur.repository;




import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sofrecom.collaborateur.model.DAOUser;



@Repository
public interface UserRepository extends CrudRepository<DAOUser, Long> {
	
	public List<DAOUser> findUsersByManager(long id);
	public DAOUser findByUsername(String username);
	public DAOUser findByEmail(String email);

}