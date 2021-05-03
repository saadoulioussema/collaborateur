package sofrecom.collaborateur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sofrecom.collaborateur.model.Competence;
import sofrecom.collaborateur.model.Description;
import sofrecom.collaborateur.model.DescriptionPK;

public interface DescriptionRepository extends CrudRepository<Description, Long> {
	
	
	
	public List<Description> findByCompetence(Competence competence);
	
    public Description findByDescriptionPK(DescriptionPK pk);
    
  //verifier le void
    
    @Modifying
    @Transactional
    @Query("UPDATE Description d SET d.descriptionPK=:descriptionPKNEW , d.description=:description where d.descriptionPK=:descriptionPK")
    public void updateDescription(@Param("descriptionPKNEW")DescriptionPK descriptionPKNEW,@Param("descriptionPK")DescriptionPK descriptionPK,@Param("description")String description);
    
    
    @Modifying
    @Transactional
    public void deleteByDescriptionPK(DescriptionPK pk);
	
}
