package sofrecom.collaborateur.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofrecom.collaborateur.model.Description;
import sofrecom.collaborateur.model.DescriptionPK;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Status;
import sofrecom.collaborateur.repository.CompetenceRepository;
import sofrecom.collaborateur.repository.DescriptionRepository;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.NiveauRepository;
import sofrecom.collaborateur.service.IDescriptionService;

@Service
public class DescriptionService implements IDescriptionService {

	@Autowired
	DescriptionRepository descRepo;

	@Autowired
	CompetenceRepository competenceRepo;

	@Autowired
	NiveauRepository niveauRepo;

	@Autowired
	EntretienRepository entretienRepo;

	@Override
	public Description addDescription(long idEntretien, Description description) {
		descRepo.save(description);
		Entretien entretien = entretienRepo.findById(idEntretien).get();
		entretien.setStatus(Status.EVALUATION_COMPETENCES);
		entretienRepo.save(entretien);
		return description;
	}

	@Override
	public Description updateDescriptionLevel(long idNiveau, Description description) {
		Description decriptionFounded = descRepo.findByDescriptionPK(description.getDescriptionPK());
		if (decriptionFounded != null) {

			// update in repo won't return the saved description
			decriptionFounded.getDescriptionPK().setIdNiveau(idNiveau);
			decriptionFounded.setDescriptionPK(decriptionFounded.getDescriptionPK());
			descRepo.updateDescription(decriptionFounded.getDescriptionPK(), description.getDescriptionPK(),
					description.getDescription());
			return descRepo.save(decriptionFounded);
		} else {
			System.out.println("nothing to update , user hasnt evaluate competence yet !");
			return null;
		}
		// OTHER WAY
//		descRepo.deleteByDescriptionPK(description.getDescriptionPK());
//		decriptionFounded.getDescriptionPK().setIdNiveau(idNiveau);	

	}

	@Override
	public List<Description> getDescriptionList() {
		return (List<Description>) descRepo.findAll();
	}

	@Override
	public Description getDescription(long idCompetence, long idNiveau) {
		DescriptionPK descPK = new DescriptionPK();
		descPK.setIdCompetence(idCompetence);
		descPK.setIdNiveau(idNiveau);
		return descRepo.findByDescriptionPK(descPK);
	}

}
