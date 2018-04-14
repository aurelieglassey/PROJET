package com.example.android.hospitalapp_arbellayglassey.dataAccess;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.example.android.hospitalapp_arbellayglassey.dataAccess.dao.MedecineDao;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.dao.PatientDao;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.dao.TreatmentDao;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.dao.TreatmentMedecineLinkDao;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.Medecine;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.Patient;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.Treatment;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.TreatmentMedecineLink;


@Database(entities = {Patient.class, Medecine.class, Treatment.class, TreatmentMedecineLink.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;


    //singleton instance object
    private static final Object LOCK = new Object();

    public abstract PatientDao patientDao();
   // public abstract MedecineDao medecineDao();
    //public abstract TreatmentDao treatmentDao();
    //public abstract TreatmentMedecineLinkDao treatmentMedecineLinkDao();

    public synchronized static AppDatabase getAppDatabase (Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "hospitalDatabase").build();

                }
            }
        }

    return INSTANCE;
    }

}
