package sofrecom.collaborateur.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sofrecom.collaborateur.model.Description;
import sofrecom.collaborateur.model.DescriptionPK;

public interface DescriptionRepository extends CrudRepository<Description, Long> {
	
	
	
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
