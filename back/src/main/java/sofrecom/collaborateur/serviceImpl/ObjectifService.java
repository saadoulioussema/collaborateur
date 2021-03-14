package sofrecom.collaborateur.serviceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.repository.ObjectifRepository;

import sofrecom.collaborateur.service.IObjectifService;

@Component
public class ObjectifService implements IObjectifService {

	
	@Autowired
	ObjectifRepository ObjectifRepo;
	
	
	@Override
	public List<Objectif> getAllObjectifs(String idCompagne,long idUser) {
		return (List<Objectif>)ObjectifRepo.findByidCompagne(idCompagne,idUser);
	}
	
	@Override
	public void autoEvaluateObjectif(Objectif objectif) {
	  Objectif obj=ObjectifRepo.findByIdObjectif(objectif.getId());
	  objectif.setCompagne(obj.getCompagne());
	  objectif.setUser(obj.getUser());
	  ObjectifRepo.save(objectif);
	}

	@Override
	public List<Objectif> getCollaborateurObjectifsForManager(long id) {
		return (List<Objectif>)ObjectifRepo.findByIdUser(id);
	}
}
