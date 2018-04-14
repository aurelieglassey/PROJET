package com.example.android.hospitalapp_arbellayglassey.listActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.hospitalapp_arbellayglassey.adapter.ListViewWithDelBtnAdapter;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.AppDatabase;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.Patient;
import com.example.android.hospitalapp_arbellayglassey.patient.PatientAdd;
import com.example.android.hospitalapp_arbellayglassey.patient.PatientDetails;
import com.example.android.hospitalapp_arbellayglassey.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOfPatientActivity extends AppCompatActivity {

    //Button add a new patient
    private Button btnNewPatient;
    AppDatabase db ;
    ArrayList<String> patients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patient);

        //Button to add a new patient for the list
        pressBtnNewPatient();

        readDB();
        ListView list;


        Intent intent = new Intent(ListOfPatientActivity.this, PatientDetails.class);
        list = (ListView) findViewById(R.id.listofpatient);
        list.setAdapter(new ListViewWithDelBtnAdapter(patients, ListOfPatientActivity.this, intent, R.layout.listofpatient_laylout, R.id.listview_listofpatient, R.id.deletePatientButton));



    }

    public void readDB(){

        patients = new ArrayList<String>();

        db = AppDatabase.getAppDatabase(this);
        List<Patient> listPatient = db.patientDao().getAllPatient();

        for (Patient p : listPatient){
            patients.add(p.getName().toString());
        }


    }


    //When the user decide to add a patient
    public void pressBtnNewPatient(){
        btnNewPatient = (Button) findViewById(R.id.btn_add_patient);
        btnNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfPatientActivity.this, PatientAdd.class);
                ListOfPatientActivity.this.startActivity(intent);
            }
        });
    }

    //get the db and get the patients list
    private void initiateDB(){


    }
    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
