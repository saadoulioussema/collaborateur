
import { PermissionsTable } from './permissions.table';
import { FonctionTable } from './fonction.table';

// Wrapper class
export class AuthDataContext {
	public static fonctions: any = FonctionTable.fonctions;
	public static permissions = PermissionsTable.permissions;
}
