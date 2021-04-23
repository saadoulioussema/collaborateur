package sofrecom.collaborateur.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sofrecom.collaborateur.model.Compagne;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Objectif;

@Repository
public interface ObjectifRepository extends CrudRepository<Objectif, Long> {
	
    	
    public List<Objectif> findByEntretienAndCompagne(Entretien entretien,Compagne compagne);
     
    @Modifying
    @Transactional
    public void deleteByEntretienIdAndCompagneIdCompagneAndDesignation(long iduser,String compagne,String designation);
    
    
}


