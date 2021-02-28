package sofrecom.collaborateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sofrecom.collaborateur.model.Campagne;

@Repository
public interface CampagneRepository extends JpaRepository<Campagne, String> {

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value="UPDATE campagne c SET c.actif = false WHERE c.actif = true")
	public void updateActifStatus();

}
