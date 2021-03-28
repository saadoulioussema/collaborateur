package sofrecom.collaborateur.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sofrecom.collaborateur.model.Compagne;


public interface CompagneRepository extends CrudRepository<Compagne, Long> {
	
    @Query("select c from Compagne c where c.idCompagne=:id")
    public Compagne findByIdCompagne(@Param("id")String id);

}
