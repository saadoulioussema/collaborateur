import { Compagne } from './compagne';
import { User } from './user';
export class Entretien {
    id: number;
    date:Date;
    formations:string;
    certifications:string;
    status:string;
    axes:string;
    points:string;
    projet:string;
    feedback:string;
    user:User;
    compagne:Compagne;
}