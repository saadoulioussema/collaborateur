export class FonctionTable {
	public static fonctions: any = [
        {
            id: 1,
            title: 'admin',
            isCoreRole: true,
            permissions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        },
        {
            id: 2,
            title: 'manager',
            isCoreRole: true,
            permissions: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
			// permissions: [3, 4, 10]
        },
        {
            id: 3,
            title: 'collaborateur',
            isCoreRole: false,
			permissions: []
        }
    ];
}
