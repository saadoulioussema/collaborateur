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
	
    	
	  public List<Objectif> findByEntretienAndEntretienCompagne(Entretien entretien,Compagne compagne);
	  
//	  TO LOOK HOW TO USE BY 
//    @Modifying
//    @Transactional
//    public void deleteByEntretienIdAndCompagneIdCompagneAndDesignation(long idEntretien,String compagne,String designation);
    
	    @Modifying
	    @Transactional
	    public void deleteByEntretienIdAndDesignation(long idEntretien,String designation);
    
    
}


