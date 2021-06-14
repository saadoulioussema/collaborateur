package sofrecom.collaborateur.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Status;
import sofrecom.collaborateur.repository.CompagneRepository;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.UserRepository;
import sofrecom.collaborateur.service.IEntretienService;

@Service
public class EntretienService implements IEntretienService {

	@Autowired
	CompagneService compagneService;

	@Autowired
	EntretienRepository EntretienRepo;

	@Autowired
	UserRepository UserRepo;

	@Autowired
	CompagneRepository compagneRepo;

	@Override
	public Entretien addEntretien(Entretien entretien) {
		return EntretienRepo.save(entretien);
	}

	@Override
	public List<Entretien> getEIPsByManagerAndCompagne(long id) {
		List<Entretien> entretiens = new ArrayList<Entretien>();
		String key = compagneService.getPreviousSemesterAndYear();
		List<Entretien> entretiensByManager = (List<Entretien>) EntretienRepo.findEIPsByManager(id);
		for (Entretien entretien : entretiensByManager) {
			if (entretien.getCompagne().getIdCompagne().equals(key)) {
				entretiens.add(entretien);
			}
		}
		return entretiens;
	}

	@Override
	public DAOUser getCollaborateurByEntretien(long id) {
		return EntretienRepo.findCollaborateurByEntretien(id);
	}

	@Override
	public Entretien getEntretienByCollaborateurAndCompagne(long id) {
		String key = compagneService.getPreviousSemesterAndYear();
		return EntretienRepo.findByUserAndCompagne(UserRepo.findById(id).get(), compagneRepo.findByIdCompagne(key));
	}

	@Override
	public Entretien saveProjectAndFormation(String projet, String formation, long idEntretien) {
		Entretien entretien = EntretienRepo.findById(idEntretien).get();
		entretien.setProjet(projet);
		entretien.setFormations(formation);
		entretien.setStatus(Status.PROJET_PROFESSIONEL);
		return EntretienRepo.save(entretien);
	}

	@Override
	public Entretien closeEntretien(Entretien entretien) {
		Entretien entretienReturned = EntretienRepo.findById(entretien.getId()).get();
		entretienReturned.setFeedback(entretien.getFeedback());
		entretienReturned.setStatus(Status.CLOTURE);
		return EntretienRepo.save(entretienReturned);
	}
}
