package sofrecom.collaborateur.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sofrecom.collaborateur.model.Evaluation;
import sofrecom.collaborateur.model.EvaluationPK;
import sofrecom.collaborateur.repository.EvaluationRepository;
import sofrecom.collaborateur.service.IEvaluationService;

@Service
public class EvaluationService implements IEvaluationService {

	@Autowired
	EvaluationRepository evalRepo;
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
		}
		else 
			return null;
	}

	@Override
	public Evaluation getEvaluationByUserAndCompetence(long idUser, long idCompetence) {
		EvaluationPK evalPK = new EvaluationPK();
		evalPK.setIdUser(idUser);
		evalPK.setIdCompetence(idCompetence);
		return evalRepo.findByEvaluationPK(evalPK);
	}
}
