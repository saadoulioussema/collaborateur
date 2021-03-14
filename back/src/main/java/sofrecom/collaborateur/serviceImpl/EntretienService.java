package sofrecom.collaborateur.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Status;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.UserRepository;
import sofrecom.collaborateur.service.IEntretienService;

@Component
public class EntretienService implements IEntretienService {
	
	
	@Autowired
	EntretienRepository EntretienRepo;
	
	@Autowired
	UserRepository UserRepo;
	
	@Override
	public List<Entretien> getEIPsByManager(long id) {
		return (List<Entretien>)EntretienRepo.findEIPsByManager(id);
	}

	@Override
	public DAOUser getCollaborateurByEntretien(long id) {
		return EntretienRepo.findCollaborateurByEntretien(id);
	}

	@Override
	public void evaluateObjectif(Entretien entretien) {
		Date date = new Date(System.currentTimeMillis());
		entretien.setDate(date);
		entretien.setStatus(Status.EVALUE);
		EntretienRepo.save(entretien);
	}

}
