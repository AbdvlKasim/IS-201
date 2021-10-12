create database if not exists AMV;
use AMV;
CREATE OR REPLACE TABLE Ansatt
(
    Ansatt_ID               INTEGER UNIQUE auto_increment,
    Mobilnummer             INTEGER NOT NULL,
    Fornavn                 varchar(30) NOT NULL,
    Etternavn               varchar(30) NOT NULL,
    Epost                   varchar(255) NOT NULL,
    Adresse                 varchar(50) NOT NULL,
    Ansattnummer            INTEGER NOT NULL,
    PRIMARY KEY (Ansatt_ID)
 );

CREATE OR REPLACE TABLE Kategori
(
    Kategori_ID             INTEGER UNIQUE auto_increment,
    Kategori                varchar(50) NOT NULL,
    PRIMARY KEY (Kategori_ID)
);

CREATE OR REPLACE TABLE Betalingsmetode
(
    Betalingsmetode_ID      INTEGER UNIQUE auto_increment,
    Metode                  varchar(80),
    PRIMARY KEY (Betalingsmetode_ID)
);

CREATE OR REPLACE TABLE Utstyr
(
    Utstyr_ID               INTEGER UNIQUE auto_increment,
    Utstyr_Navn             varchar(50) NOT NULL,
    Utstyr_Beskrivelse      varchar(50) NOT NULL,
    Kategori_ID             INTEGER NOT NULL,
    PRIMARY KEY (Utstyr_ID),
    FOREIGN KEY (Kategori_ID) REFERENCES Kategori(Kategori_ID)
);

CREATE OR REPLACE TABLE Foresporsel
(
    Foresporsel_ID          INTEGER UNIQUE auto_increment,
    Ansatt_ID               INTEGER NOT NULL,
    Utstyr_ID               INTEGER NOT NULL,
    PRIMARY KEY (Foresporsel_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID)
    );

CREATE OR REPLACE TABLE Status
(
    Status_ID               integer UNIQUE auto_increment,
    /* date skrives på format dd.mm.åååå */
    Start_Dato              date,
    Slutt_Dato              date,
    Tilgjengelig            varchar(30),
    Utstyr_ID               INTEGER NOT NULL,
    PRIMARY KEY (Status_ID),
    FOREIGN KEY (Utstyr_ID) references Utstyr(Utstyr_ID)
);

CREATE OR REPLACE TABLE LisensiertUtstyr
(
    Lisens_ID               INTEGER UNIQUE auto_increment,
    Utstyr_kommentar        varchar (30) NOT NULL,
    Utstyr_ID               INTEGER NOT NULL,
    PRIMARY KEY (Lisens_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID)
);


CREATE OR REPLACE TABLE Forslag
(
    Forslag_ID              integer UNIQUE auto_increment,
    Forslag_Utstyr          varchar(30) NOT NULL,
    Forslag_Kommentar       varchar(60) NOT NULL,
    Ansatt_ID               integer     NOT NULL,
    PRIMARY KEY (Forslag_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID)
);

CREATE OR REPLACE TABLE Rapport
(
    Rapport_ID              integer UNIQUE auto_increment,
    Rapport_Tittel          varchar(30) NOT NULL,
    Rapport_Kommentar       varchar(60) NOT NULL,
    Utstyr_ID               integer     NOT NULL,
    Ansatt_ID               integer     NOT NULL,
    PRIMARY KEY (Rapport_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID)
);

CREATE OR REPLACE TABLE LisensiertAnsatt
(
    Lisens_ID               INTEGER NOT NULL,
    Ansatt_ID               INTEGER NOT NULL,
    Ansatt_Kommentar        varchar(255),
    FOREIGN KEY (Lisens_ID) REFERENCES LisensiertUtstyr(Lisens_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID)
);

CREATE OR REPLACE TABLE Administrator (
    Ansatt_ID               INTEGER NOT NULL,
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID)
);

CREATE OR REPLACE TABLE Superbruker
(
    Ansatt_ID               INTEGER NOT NULL,
    FOREIGN KEY(Ansatt_ID) references Ansatt(Ansatt_ID)
);

CREATE OR REPLACE TABLE Betaling
(
    Ansatt_ID               integer NOT NULL,
    Utstyr_ID               integer NOT NULL,
    Betalingsmetode_ID      integer NOT NULL,
    FOREIGN KEY(Betalingsmetode_ID) references Betalingsmetode(Betalingsmetode_ID)
);