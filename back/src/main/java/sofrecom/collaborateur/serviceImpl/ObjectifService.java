package sofrecom.collaborateur.serviceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofrecom.collaborateur.model.Compagne;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.model.Status;
import sofrecom.collaborateur.repository.CompagneRepository;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.ObjectifRepository;
import sofrecom.collaborateur.repository.UserRepository;
import sofrecom.collaborateur.service.IObjectifService;

@Service
public class ObjectifService implements IObjectifService {

	@Autowired
	CompagneService compagneService;

	@Autowired
	ObjectifRepository objectifRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	CompagneRepository compagneRepo;

	@Autowired
	EntretienRepository entretienRepo;


	@Override
	public List<Objectif> getObjectifListByEntretienAndEntretienCompagne(long id) {
		// For Testing
		if (entretienRepo.findById(id) == null)
			throw new NullPointerException();

		Entretien entretien = entretienRepo.findById(id).get();
		String key = compagneService.getPreviousSemesterAndYear();
		Compagne compagne = compagneRepo.findByIdCompagne(key);
		return (List<Objectif>) objectifRepo.findByEntretienAndEntretienCompagne(entretien, compagne);
	}

	@Override
	public List<Objectif> getObjectifListByCurrentEntretienAndEntretienCompagne(long id) {

		Entretien entretien = entretienRepo.findById(id).get();
		String key = compagneService.getSemesterAndYear();
		Compagne compagne = compagneRepo.findByIdCompagne(key);
		return (List<Objectif>) objectifRepo.findByEntretienAndEntretienCompagne(entretien, compagne);

	}

	@Override
	public Objectif autoEvaluateObjectif(Objectif objectif) {
		Objectif saveObjectif = objectifRepo.save(objectif);

		Entretien entretien = entretienRepo.findById(objectif.getEntretien().getId()).get();
		entretien.setStatus(Status.AUTO_EVALUATION);
		entretienRepo.save(entretien);
		return saveObjectif;
	}

	@Override
	public Objectif evaluateObjectif(Objectif objectif) {
		Objectif saveObjectif = objectifRepo.save(objectif);
		Entretien entretien = entretienRepo.findById(objectif.getEntretien().getId()).get();
		entretien.setStatus(Status.EVALUATION);
		entretienRepo.save(entretien);
		return saveObjectif;
	}

	@Override
	public List<Objectif> newObjectif(List<Objectif> objectifs, long newEntretienId, long idEntretien) {

		/****************************************************************************************************************
		 * 
		 * UPDATING CURRENT ENTRETIEN //
		 * 
		 *************************************************************************************************************/
		DAOUser user = userRepo.findById(entretienRepo.findById(idEntretien).get().getUser().getId()).get();

		System.out.println("UPDATE Current Entretien");
		entretienRepo.findById(idEntretien);
		Compagne compagne = compagneRepo
				.findByIdCompagne(entretienRepo.findById(idEntretien).get().getCompagne().getIdCompagne());
		Entretien entretienCourant = entretienRepo.findByUserAndCompagne(user, compagne);
		entretienCourant.setStatus(Status.FIXATION_OBJECTIFS);
		entretienRepo.save(entretienCourant);

		/****************************************************************************************************************
		 * 
		 * SAVING CHANGES ON THE NEW ENTRETIEN CREATED FROM THE FRONT END AND AFFECTING
		 * NEW OBJECTIFS TO THIS ENTRETIEN //
		 * 
		 *************************************************************************************************************/

		// en train de valider les objectifs de la compagne précedente donc semestre
		// doit contenir la semestre courante et l'année kifkif
		String key = compagneService.getSemesterAndYear();
		compagne = compagneRepo.findByIdCompagne(key);
		Entretien newEntretien = entretienRepo.findById(newEntretienId).get();
		newEntretien.setStatus(Status.ATTENTE_EVALUATION);
		newEntretien.setCompagne(compagne);
		newEntretien.setUser(user);
		entretienRepo.save(newEntretien);
		for (int i = 0; i < objectifs.size(); i++) {
			objectifs.get(i).setEntretien(newEntretien);
			objectifRepo.save(objectifs.get(i));
		}
		return objectifs;
	}

	@Override
	public void addOtherObjectifsByUserAndCompagne(List<Objectif> objectifs, long idUser) {
		DAOUser user = userRepo.findById(idUser).get();
		String key = compagneService.getSemesterAndYear();
		Compagne compagne = compagneRepo.findByIdCompagne(key);
		Entretien newEntretienFounded = entretienRepo.findByUserAndCompagne(user, compagne);
		for (int i = 0; i < objectifs.size(); i++) {
			objectifs.get(i).setEntretien(newEntretienFounded);
			objectifRepo.save(objectifs.get(i));
		}
	}

	@Override
	public List<Objectif> deleteNewObjectif(long idUser, String designation) {
		DAOUser user = userRepo.findById(idUser).get();
		String key = compagneService.getSemesterAndYear();
		Compagne compagne = compagneRepo.findByIdCompagne(key);
		Entretien newEntretien = entretienRepo.findByUserAndCompagne(user, compagne);
		objectifRepo.deleteByEntretienIdAndDesignation(newEntretien.getId(), designation);
		return newEntretien.getObjectifs();
	}

	@Override
	public Objectif feedbackObjectif(Objectif objectif) {
		Objectif objectifReturned = objectifRepo.findById(objectif.getId()).get();
		objectifReturned.setFeedback(objectif.getFeedback());
		return objectifRepo.save(objectifReturned);

	}

	@Override
	public List<Objectif> getAssignedObjectifs(long idUser) {
		DAOUser user = userRepo.findById(idUser).get();
		String key = compagneService.getSemesterAndYear();
		Compagne compagne = compagneRepo.findByIdCompagne(key);
		Entretien entretien = entretienRepo.findByUserAndCompagne(user, compagne);
		return entretien.getObjectifs();

	}

	/****************************************************************************************************************
	 * 
	 * FOR TESTING PURPOSE
	 * 
	 *************************************************************************************************************/

	@Override
	public Objectif addObjectif(Objectif objectif) {
		return objectifRepo.save(objectif);
	}

	@Override
	public Objectif getObjectif(long id) {
		return objectifRepo.findById(id).get();
	}

	@Override
	public Objectif updateObjectif(Objectif objectif) {
		Objectif obj = objectifRepo.findById(objectif.getId()).get();

		obj.setAutoEvaluation(objectif.getAutoEvaluation());
		obj.setEvaluation(objectif.getEvaluation());
		obj.setCommentaire(objectif.getCommentaire());
		obj.setDesignation(objectif.getDesignation());
		obj.setEntretien(objectif.getEntretien());

		return objectifRepo.save(objectif);

	}

	@Override
	public void deleteObjectif(Objectif objectif) {
		final Objectif obj = objectifRepo.findById(objectif.getId()).get();
		if (obj != null) {
			objectifRepo.delete(obj);
		}

		objectifRepo.deleteById(objectif.getId());
	}

	@Override
	public List<Objectif> getAllObjectifs() {
		return (List<Objectif>) objectifRepo.findAll();
	}
}
