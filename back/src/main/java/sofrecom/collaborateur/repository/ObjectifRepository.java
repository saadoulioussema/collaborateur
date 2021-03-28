package sofrecom.collaborateur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sofrecom.collaborateur.model.Objectif;

@Repository
public interface ObjectifRepository extends CrudRepository<Objectif, Long> {
	
    @Query("select o from Objectif o join o.compagne c join o.user u where u.id=:idUser and c.idCompagne=:idCompagne")
    public List<Objectif> findByidUserAndidCompagne(@Param("idUser") long idUser,@Param("idCompagne") String idCompagne);
    
    @Query("select o from Objectif o where o.id=:id")
    public Objectif findByIdObjectif(@Param("id")long id);
    
    @Query("select o from Objectif o join o.compagne c join o.user u where u.id=:idUser and c.idCompagne=:idCompagne")
    public List<Objectif> findByIdUser(@Param("idUser")long id,@Param("idCompagne")String key);

}
