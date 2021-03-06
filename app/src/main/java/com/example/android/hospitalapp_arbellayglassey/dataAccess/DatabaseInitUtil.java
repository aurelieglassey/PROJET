package com.example.android.hospitalapp_arbellayglassey.dataAccess;

import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.MedecineEntity;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.PatientEntity;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.TreatmentEntity;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.TreatmentMedecineLinkEntity;

import java.util.ArrayList;
import java.util.List;

// this init is used to populate the db the first time with the app
public class DatabaseInitUtil {

   protected static List<MedecineEntity> lm;
   protected static List<PatientEntity> lp;
   protected static List<TreatmentEntity> lt;
   protected static List<TreatmentMedecineLinkEntity> ll;

    //Initalize the database
    static void initializeDb (AppDatabase db){

        //Create lists
        lm = new ArrayList<>();
        lp = new ArrayList<>();
        lt = new ArrayList<>();
        ll = new ArrayList<>();

        //Generate some data and insert them inside a database
        generateData();
        insertData(db);
    }

    //Create some data for the lists
    private static void generateData(){

        //Create medecine
        MedecineEntity m1 = new MedecineEntity();
        m1.setIdM(1);
        m1.setName("Dafalgan");
        m1.setType("Analgesic");
        m1.setActiveIngredient("Paracetamol");
        m1.setApplication("Dilute in a glass of water");
        m1.setManufacturers("Brisol-myers");
        m1.setSideEffects("Headaches");
        m1.setMaxPerDay(3);

        MedecineEntity m2 = new MedecineEntity();
        m2.setIdM(2);
        m2.setName("Neocitran");
        m2.setType("Chloryhdrate de pseudoéphédrine");
        m2.setActiveIngredient("Paracetamol");
        m2.setApplication("Dilute in a glass of water");
        m2.setManufacturers("Sun Store");
        m2.setSideEffects("Asthme");
        m2.setMaxPerDay(2);

        //Add the Medecine created in the list of medecine lm
        lm.add(m1);
        lm.add(m2);




        //Create patients
        PatientEntity p1 = new PatientEntity();
        p1.setIdP(1);
        p1.setName("Aurélie Glassey");
        p1.setGender('F');
        p1.setRoomNumber(302);
        p1.setBloodGroup("A");
        p1.setAge(21);
        p1.setReasonAdmission("Broken collarbone, with sever injuries due to a fall");

        PatientEntity p2 = new PatientEntity();
        p2.setIdP(2);
        p2.setName("Olivier Arbellay");
        p2.setGender('M');
        p2.setRoomNumber(303);
        p2.setBloodGroup("O");
        p2.setAge(22);
        p2.setReasonAdmission("Broken heart, hope to find the love in this hospital or maybe in this application");

        PatientEntity p3 = new PatientEntity();
        p3.setIdP(3);
        p3.setName("Maud Rouvinez");
        p3.setGender('F');
        p3.setRoomNumber(304);
        p3.setBloodGroup("B+");
        p3.setAge(21);
        p3.setReasonAdmission("Broken heart");

        //Add the patients created in the list of patient lp
        lp.add(p1);
        lp.add(p2);



        //create the treatment
        TreatmentEntity t1 = new TreatmentEntity();
        t1.setIdT(1);
        t1.setName("Aurelie_Treatment");
        t1.setMaxQuantity(3);
        t1.setIdPatient(1);

        TreatmentEntity t2 = new TreatmentEntity();
        t2.setIdT(2);
        t2.setName("Olivier_Treatment");
        t2.setMaxQuantity(7);
        t2.setIdPatient(2);

        TreatmentEntity t3 = new TreatmentEntity();
        t3.setIdT(3);
        t3.setName("Maud_Treatment");
        t3.setMaxQuantity(7);
        t3.setIdPatient(3);

        lt.add(t1);
        lt.add(t2);
        lt.add(t3);

        TreatmentMedecineLinkEntity tmtl = new TreatmentMedecineLinkEntity();
        tmtl.setIdMedecine(1);
        tmtl.setIdTreatment(1);
        //tmtl.setQuantityPerDay("2");

        TreatmentMedecineLinkEntity tmt2 = new TreatmentMedecineLinkEntity();
        tmt2.setIdMedecine(2);
        tmt2.setIdTreatment(3);
        // tmt2.setQuantityPerDay("2");

        ll.add(tmtl);
        ll.add(tmt2);
        lp.add(p3);





    }

    //Inserts the list inside the database
    private static void insertData(AppDatabase db) {

        db.beginTransaction();

        try {
            db.medecineDao().insertAllMedecine(lm);
            db.patientDao().insertAllPatient(lp);
            db.treatmentDao().insertAllTreatment(lt);

            db.treatmentMedecineLinkDao().insertAllLink(ll);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


}
