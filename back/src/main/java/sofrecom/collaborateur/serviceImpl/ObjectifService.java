package sofrecom.collaborateur.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.tools.rmi.ObjectNotFoundException;
import sofrecom.collaborateur.model.Compagne;
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

	@Override
	public List<Objectif> getObjectifListByEntretienAndCompagne(long id) {
		Entretien entretien  = entretienRepo.findById(id).get();
		int yearConverted = Integer.parseInt(year);
		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted - 1);
			String key = "S2" + correctYear;
		    Compagne compagne=compagneRepo.findByIdCompagne(key);
			return (List<Objectif>) objectifRepo.findByEntretienAndCompagne(entretien,compagne);

		} else {
			String key = "S1" + year;
			Compagne compagne=compagneRepo.findByIdCompagne(key);
			return (List<Objectif>) objectifRepo.findByEntretienAndCompagne(entretien,compagne);
		}
	}
	
	@Override
	public void autoEvaluateObjectif(Objectif objectif) {
		Objectif obj = objectifRepo.findById(objectif.getId()).get();
		objectif.setCompagne(obj.getCompagne());
		
		Entretien entretien = entretienRepo.findById(obj.getEntretien().getId()).get();
		entretien.setStatus(Status.AUTO_EVALUATION);
		entretienRepo.save(entretien);
		objectifRepo.save(objectif);
	}
	
	@Override
	public void evaluateObjectif(Objectif objectif) {
		Objectif obj = objectifRepo.findById(objectif.getId()).get();
		objectif.setCompagne(obj.getCompagne());
		Entretien entretien = entretienRepo.findById(obj.getEntretien().getId()).get();
		entretien.setStatus(Status.EVALUATION);
		entretienRepo.save(entretien);
		objectifRepo.save(objectif);
	}



	@Override
	public void newObjectif(Objectif objectif, long id) throws ObjectNotFoundException {
		if (semestre.contains(month)) {
			String key = "S1" + year;
			Compagne compagne = compagneRepo.findByIdCompagne(key);
			if (compagne != null)
				objectif.setCompagne(compagne);
			else
				throw new ObjectNotFoundException("Compagne" + key + "not found");
		} else {
			String key = "S2" + year;
			Compagne compagne = compagneRepo.findByIdCompagne(key);
			if (compagne != null)
				objectif.setCompagne(compagne);
			else
				throw new ObjectNotFoundException("Compagne" + key + "not found");
		}
		Entretien entretien = entretienRepo.findEntretienByUserId(id);
		entretien.setStatus(Status.FIXATION_OBJECTIFS);
		entretienRepo.save(entretien);
		objectifRepo.save(objectif);
	}

	@Override
	public void deleteObjectif(long id,String designation) {
		
		if (semestre.contains(month)) {
			String key = "S1" + year;
			objectifRepo.deleteByEntretienIdAndCompagneIdCompagneAndDesignation(id,key,designation);
			

		} else {
			String key = "S2" + year;
			System.out.println(id+designation+key);
			objectifRepo.deleteByEntretienIdAndCompagneIdCompagneAndDesignation(id,key,designation);
			
		}
	}
}
