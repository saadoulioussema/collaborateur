package sofrecom.collaborateur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sofrecom.collaborateur.model.Objectif;

@Repository
public interface ObjectifRepository extends CrudRepository<Objectif, Long> {
	
    @Query("select o from Objectif o join o.campagne c where c.idCampagne=:idCampagne")
    public List<Objectif> findByidCampagne(@Param("idCampagne")String idCampagne);
    
    @Query("select o from Objectif o where o.id=:id")
    public Objectif findByIdObjectif(@Param("id")long id);

}
