package sofrecom.collaborateur.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.tools.rmi.ObjectNotFoundException;
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
	ObjectifRepository ObjectifRepo;

	@Autowired
	UserRepository UserRepo;

	@Autowired
	CompagneRepository CompagneRepo;
	
	@Autowired
	EntretienRepository EntretienRepo;

	List<String> semestre = Arrays.asList("01", "02", "03", "04", "05", "06");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date(System.currentTimeMillis());
	String year = formatter.format(date).substring(0, 4);
	String month = formatter.format(date).substring(5, 7);

	@Override
	public List<Objectif> getObjectifListByidUserAndidCompagne(long idUser) {
		int yearConverted = Integer.parseInt(year);
		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted - 1);
			String key = "S2" + correctYear;
			return (List<Objectif>) ObjectifRepo.findByidUserAndidCompagne(idUser,key);

		} else {
			String key = "S1" + year;
			return (List<Objectif>) ObjectifRepo.findByidUserAndidCompagne(idUser,key);
		}
	}

	@Override
	public void autoEvaluateObjectif(Objectif objectif) {
		Objectif obj = ObjectifRepo.findByIdObjectif(objectif.getId());
		objectif.setCompagne(obj.getCompagne());
		objectif.setUser(obj.getUser());
		
		Entretien entretien = EntretienRepo.findEntretienByUserId(obj.getUser().getId());
		entretien.setStatus(Status.AUTO_EVALUATION);
		EntretienRepo.save(entretien);
		ObjectifRepo.save(objectif);
	}
	
	@Override
	public void evaluateObjectif(Objectif objectif) {
		Objectif obj = ObjectifRepo.findByIdObjectif(objectif.getId());
		objectif.setCompagne(obj.getCompagne());
		objectif.setUser(obj.getUser());
		Entretien entretien = EntretienRepo.findEntretienByUserId(obj.getUser().getId());
		entretien.setStatus(Status.EVALUATION);
		EntretienRepo.save(entretien);
		ObjectifRepo.save(objectif);
	}

	@Override
	public List<Objectif> getCollaborateurObjectifsForManager(long id) {
		int yearConverted = Integer.parseInt(year);
		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted - 1);
			String key = "S2" + correctYear;
			return (List<Objectif>) ObjectifRepo.findByIdUser(id, key);

		} else {
			String key = "S1" + year;
			return (List<Objectif>) ObjectifRepo.findByIdUser(id, key);

		}

	}

	@Override
	public void newObjectif(Objectif objectif, long id) throws ObjectNotFoundException {

		DAOUser user = UserRepo.findById(id).get();
		objectif.setUser(user);

		if (semestre.contains(month)) {
			String key = "S1" + year;
			Compagne compagne = CompagneRepo.findByIdCompagne(key);
			if (compagne != null)
				objectif.setCompagne(compagne);
			else
				throw new ObjectNotFoundException("Compagne" + key + "not found");
		} else {
			String key = "S2" + year;
			Compagne compagne = CompagneRepo.findByIdCompagne(key);
			if (compagne != null)
				objectif.setCompagne(compagne);
			else
				throw new ObjectNotFoundException("Compagne" + key + "not found");
		}
		Entretien entretien = EntretienRepo.findEntretienByUserId(id);
		entretien.setStatus(Status.FIXATION_OBJECTIFS);
		EntretienRepo.save(entretien);
		ObjectifRepo.save(objectif);
	}


}
