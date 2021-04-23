// import { Address } from './address.model';
// import { SocialNetworks } from './social-networks.model';
// import { FonctionTable } from '../core/auth/_server/fonction.table';

export class User {

    id: number;
    fullname: string;
    email: string;
    username: string;
    password: string;
    pic: string ="";
    managerId:number;
    // fonctionId=FonctionTable.fonctions;
    // permission=PermissionsTable.permissions=[1];
    // public static fonctions: any = FonctionTable.fonctions;
    fonctionId:number;
    dateIntegration:string;
    matricule:string;
    // accessToken: string;
    // refreshToken: string;
    // roles: number[];
    // pic: string;
    // occupation: string;
	// companyName: string;
	// phone: string;
    // address: Address;
    // socialNetworks: SocialNetworks;

    
    clear(): void {
        this.id = undefined;
        this.fullname = '';
        this.email = '';
        this.username = '';
        this.password = '';
        // this.address = new Address();
        // this.address.clear();
        // this.socialNetworks = new SocialNetworks();
        // this.socialNetworks.clear();
    }
}