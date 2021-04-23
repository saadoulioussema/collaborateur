package sofrecom.collaborateur.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.model.Competence;
import sofrecom.collaborateur.repository.CompetenceRepository;
import sofrecom.collaborateur.service.ICompetenceService;

@Service
public class CompetenceService implements ICompetenceService{
	
	
	@Autowired
	CompetenceRepository competenceRepo;
	
	
	@Override
	public List<Competence> getCompetenceList() {
		return (List<Competence>) competenceRepo.findAll();
	}

}
