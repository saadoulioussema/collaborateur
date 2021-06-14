package sofrecom.collaborateur.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Evaluation;
import sofrecom.collaborateur.model.EvaluationPK;
import sofrecom.collaborateur.model.Status;
import sofrecom.collaborateur.repository.CompagneRepository;
import sofrecom.collaborateur.repository.CompetenceRepository;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.EvaluationRepository;
import sofrecom.collaborateur.service.IEvaluationService;

@Service
public class EvaluationService implements IEvaluationService {

	@Autowired
	CompagneService compagneService;

	@Autowired
	EvaluationRepository evalRepo;
	@Autowired
	EntretienRepository entretienRepo;

	@Autowired
	CompagneRepository compagneRepo;

	@Autowired
	CompetenceRepository competenceRepo;

	@Override
	public Evaluation addEvaluationByUserAndCompetence(long idUser, long idCompetence) {
		EvaluationPK evalPK = new EvaluationPK();
		evalPK.setIdUser(idUser);
		evalPK.setIdCompetence(idCompetence);
		Evaluation eval = new Evaluation();
		eval.setEvaluationPK(evalPK);
		evalRepo.save(eval);
		return eval;
	}

	@Override
	public Evaluation addEvaluation(Evaluation evaluation) {

		String key = compagneService.getPreviousSemesterAndYear();
		Entretien entretien = entretienRepo
				.findEntretienByUserIdAndCompagneIdCompagne(evaluation.getEvaluationPK().getIdUser(), key);
		entretien.setStatus(Status.EVALUATION_COMPETENCES);
		entretienRepo.save(entretien);
		evalRepo.save(evaluation);
		return evaluation;
	}

	@Override
	public Evaluation updateEvaluationLevel(Evaluation evaluation) {

		EvaluationPK evalPK = new EvaluationPK();
		evalPK.setIdUser(evaluation.getEvaluationPK().getIdUser());
		evalPK.setIdCompetence(evaluation.getEvaluationPK().getIdCompetence());
		Evaluation eval = evalRepo.findByEvaluationPK(evalPK);

		if (eval != null) {
			eval.setNiveau(evaluation.getNiveau());
			evalRepo.save(eval);
			return eval;
		} else
			return null;
	}

	@Override
	public Evaluation getEvaluationByUserAndCompetence(long idUser, long idCompetence) {
		EvaluationPK evalPK = new EvaluationPK();
		evalPK.setIdUser(idUser);
		evalPK.setIdCompetence(idCompetence);
		return evalRepo.findByEvaluationPK(evalPK);
	}

	@Override
	public List<Evaluation> getEvaluationListByUser(long idUser) {
		return evalRepo.findEvaluationByUserId(idUser);

	}
}
