package sofrecom.collaborateur.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
	ObjectifRepository objectifRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	CompagneRepository compagneRepo;

	@Autowired
	EntretienRepository entretienRepo;

	List<String> semestre = Arrays.asList("01", "02", "03", "04", "05", "06");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date(System.currentTimeMillis());
	String year = formatter.format(date).substring(0, 4);
	String month = formatter.format(date).substring(5, 7);
	int yearConverted = Integer.parseInt(year);

	Entretien saved;

	@Override
	public List<Objectif> getObjectifListByEntretienAndEntretienCompagne(long id) {
		Entretien entretien = entretienRepo.findById(id).get();

		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted - 1);
			String key = "S2" + correctYear;
			Compagne compagne = compagneRepo.findByIdCompagne(key);
			return (List<Objectif>) objectifRepo.findByEntretienAndEntretienCompagne(entretien, compagne);
		} else {
			String key = "S1" + year;
			Compagne compagne = compagneRepo.findByIdCompagne(key);
			return (List<Objectif>) objectifRepo.findByEntretienAndEntretienCompagne(entretien, compagne);
		}
	}

	@Override
	public void autoEvaluateObjectif(Objectif objectif) {
		objectifRepo.save(objectif);
		Entretien entretien = entretienRepo.findById(objectif.getEntretien().getId()).get();
		entretien.setStatus(Status.AUTO_EVALUATION);
		entretienRepo.save(entretien);
	}

	@Override
	public void evaluateObjectif(Objectif objectif) {
		objectifRepo.save(objectif);
		Entretien entretien = entretienRepo.findById(objectif.getEntretien().getId()).get();
		entretien.setStatus(Status.EVALUATION);
		entretienRepo.save(entretien);
	}

	@Override
	public void newObjectif(List<Objectif> objectifs, long newEntretienId, long idEntretien) {

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

		if (semestre.contains(month)) {
			// en train de valider les objectifs de la compagne S2+year donc key => S1+year
			String key = "S1" + year;
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
		} else {
			String key = "S2" + year;
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
		}

	}

	@Override
	public void addOtherObjectifsByUserAndCompagne(List<Objectif> objectifs, long idUser) {
		DAOUser user = userRepo.findById(idUser).get();
		if (semestre.contains(month)) {
			String key = "S1" + year;
			Compagne compagne = compagneRepo.findByIdCompagne(key);
			Entretien newEntretienFounded = entretienRepo.findByUserAndCompagne(user, compagne);
			for (int i = 0; i < objectifs.size(); i++) {
				objectifs.get(i).setEntretien(newEntretienFounded);
				objectifRepo.save(objectifs.get(i));
			}
		}
	}

	@Override
	public void deleteNewObjectif(long idUser, String designation) {
		DAOUser user = userRepo.findById(idUser).get();
		if (semestre.contains(month)) {
			String key = "S1" + year;
			Compagne compagne = compagneRepo.findByIdCompagne(key);
			Entretien newEntretien = entretienRepo.findByUserAndCompagne(user, compagne);			
			objectifRepo.deleteByEntretienIdAndDesignation(newEntretien.getId(),designation);
			

		} else {
			String key = "S2" + year;
			Compagne compagne = compagneRepo.findByIdCompagne(key);
			Entretien newEntretien = entretienRepo.findByUserAndCompagne(user, compagne);
			objectifRepo.deleteByEntretienIdAndDesignation(newEntretien.getId(),designation);	
		}
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
	objectifRepo.deleteById(objectif.getId());
	}
	
	
	

}
