DROP TABLE IF EXISTS DROIT;
DROP TABLE IF EXISTS STATUT_ALERTE;
DROP TABLE IF EXISTS ALERTE;
DROP TABLE IF EXISTS FICHIER;
DROP TABLE IF EXISTS UTILISATEUR;


CREATE TABLE UTILISATEUR (
	id_user int AUTO_INCREMENT PRIMARY KEY,
	prenom varchar(20),
	nom varchar(20),
	racine_rep text
);

CREATE TABLE FICHIER (
	id_fichier int AUTO_INCREMENT PRIMARY KEY,
	chemin_absolu TEXT(200),
	date_derniere_modif date,
	id_createur int REFERENCES UTILISATEUR(id_user) ON DELETE CASCADE,
	id_user_derniere_modif int REFERENCES UTILISATEUR(id_user) ON DELETE CASCADE
	
);

CREATE TABLE DROIT (
	id_user int REFERENCES UTILISATEUR(id_user) ON DELETE CASCADE,
	id_fichier int REFERENCES FICHIER(id_fichier) ON DELETE CASCADE,
	PRIMARY KEY (id_user,id_fichier)

);

CREATE TABLE ALERTE (
	id_alerte int AUTO_INCREMENT PRIMARY KEY,
	id_fichier int REFERENCES FICHIER(id_fichier) ON DELETE CASCADE,
	libelle_alerte varchar(50),
	date_generation date
);

CREATE TABLE STATUT_ALERTE(
	id_alerte int REFERENCES ALERTE(id_alerte) ON DELETE CASCADE,
	statut ENUM ('important', 'moyen','faible'),
	PRIMARY KEY (id_alerte,statut)
);