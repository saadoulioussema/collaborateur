package sofrecom.collaborateur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;


public interface EntretienRepository extends CrudRepository<Entretien, Long> {
	
    @Query("select e from Entretien e join e.user u join u.manager m where m.id=:id")
    public List<Entretien> findEIPsByManager(@Param("id")long id);
    
    @Query("select e.user from Entretien e where e.id=:id")
    public DAOUser findCollaborateurByEntretien(@Param("id")long id);
    
}
