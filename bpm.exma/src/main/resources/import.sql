
CREATE TABLE "ExmaAntrag" (
    "exmaAntragId" bigint  NOT NULL,
    "erstelldatum" date  NULL,
    "exmaTermin" date  NULL,
    "mitarbeiterId" int  NOT NULL,
    "studentId" bigint  NOT NULL,
    "exmaGrund" varchar(100)  NOT NULL,
    CONSTRAINT "ExmaAntrag_pk" PRIMARY KEY ("exmaAntragId")
);


CREATE TABLE "Mitarbeiter" (
    "mitarbeiterId" int  NOT NULL,
    "vorname" varchar(30)  NOT NULL,
    "nachname" varchar(30)  NOT NULL,
    CONSTRAINT "Mitarbeiter_pk" PRIMARY KEY ("mitarbeiterId")
);

-- Table: Pruefung
CREATE TABLE "Pruefung" (
    "pruefungId" bigint  NOT NULL,
    "bezeichnung" varchar(50)  NOT NULL,
    "semester" varchar(4)  NOT NULL,
    CONSTRAINT "Pruefung_pk" PRIMARY KEY ("pruefungId")
);

-- Table: Pruefungsanmeldung
CREATE TABLE "Pruefungsanmeldung" (
    "pruefungsanmeldungId" bigint  NOT NULL,
    "studentId" bigint  NOT NULL,
    "pruefungId" bigint  NOT NULL,
    "pruefungstermin" date  NOT NULL,
    "versuch" int  NOT NULL,
    CONSTRAINT "Pruefungsanmeldung_pk" PRIMARY KEY ("pruefungsanmeldungId")
);

-- Table: Pruefungsleistung
CREATE TABLE "Pruefungsleistung" (
    "pruefungsleistungId" bigint  NOT NULL,
    "bestanden" boolean  NOT NULL,
    "versuch" int  NOT NULL,
    "note" int  NOT NULL,
    "credits" int  NOT NULL,
    "studentId" bigint  NOT NULL,
    "pruefungId" bigint  NOT NULL,
    CONSTRAINT "Pruefungsleistung_pk" PRIMARY KEY ("pruefungsleistungId")
);

-- Table: Student
CREATE TABLE "Student" (
    "studentId" bigint  NOT NULL,
    "vorname" varchar(30)  NOT NULL,
    "nachname" varchar(30)  NOT NULL,
    "studiensemester" int  NOT NULL,
    "hochschulsemester" int  NOT NULL,
    "rueckgemeldetFuerSemester" int  NOT NULL,
    "immatrikulationsdatum" date  NOT NULL,
    "studiengang" varchar(90)  NOT NULL,
    CONSTRAINT "Student_pk" PRIMARY KEY ("studentId")
);

ALTER TABLE "ExmaAntrag" ADD CONSTRAINT "Exmatrikulationsantrag_Mitarbeiter"
    FOREIGN KEY ("mitarbeiterId") 
    REFERENCES "Mitarbeiter" ("mitarbeiterId");

ALTER TABLE "ExmaAntrag" ADD CONSTRAINT "Exmatrikulationsantrag_Student"
    FOREIGN KEY ("studentId")
    REFERENCES "Student" ("studentId");

ALTER TABLE "Pruefungsanmeldung" ADD CONSTRAINT "Pruefung_Pruefungsanmeldung"
    FOREIGN KEY ("pruefungId")
    REFERENCES "Pruefung" ("pruefungId");

ALTER TABLE "Pruefungsanmeldung" ADD CONSTRAINT "Pruefungsanmeldung_Student"
    FOREIGN KEY ("studentId")
    REFERENCES "Student" ("studentId");

ALTER TABLE "Pruefungsleistung" ADD CONSTRAINT "Pruefungsleistung_Pruefung"
    FOREIGN KEY ("pruefungId")
    REFERENCES "Pruefung" ("pruefungId");

ALTER TABLE "Pruefungsleistung" ADD CONSTRAINT "student"
    FOREIGN KEY ("studentId")
    REFERENCES "Student" ("studentId");

