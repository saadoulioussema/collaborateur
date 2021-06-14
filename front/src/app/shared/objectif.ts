import { Entretien } from './entretien';
export class Objectif {
    id:number;
    designation:string="";
    evaluation:string="";
    commentaire:string="";
    autoEvaluation:string="";
    feedback:string;
    entretien:Entretien;
}