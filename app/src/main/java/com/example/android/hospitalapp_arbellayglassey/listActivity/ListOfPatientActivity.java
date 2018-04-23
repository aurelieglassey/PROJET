package com.example.android.hospitalapp_arbellayglassey.listActivity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.hospitalapp_arbellayglassey.adapter.ListViewWithDelBtnAdapterPatient;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.DatabaseCreator;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.async.patient.AsyncGetPatients;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.PatientEntity;
import com.example.android.hospitalapp_arbellayglassey.patient.PatientAdd;
import com.example.android.hospitalapp_arbellayglassey.patient.PatientDetails;
import com.example.android.hospitalapp_arbellayglassey.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListOfPatientActivity extends AppCompatActivity {

    //Button add a new patient
    private Button btnNewPatient;
    private List<PatientEntity> patientEntities;
    private ListViewWithDelBtnAdapterPatient listAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patient);
        setTitle("");
        //Button to add a new patient for the list
        pressBtnNewPatient();

        //try to read the DB
        try {
            readDB();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView list;


        //Intent to switch between the activities
        Intent intent = new Intent(ListOfPatientActivity.this, PatientDetails.class);
        list = (ListView) findViewById(R.id.listofpatient);
        listAdapter =  new ListViewWithDelBtnAdapterPatient( patientEntities, ListOfPatientActivity.this, intent, R.layout.listofpatient_laylout, R.id.listview_listofpatient, R.id.deletePatientButton);
        list.setAdapter(listAdapter);




    }
    @Override
    public void onRestart(){
        super.onRestart();

        try {
            readDB();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listAdapter.refreshEvents(patientEntities);

    }

    //Read te db from our application
    public void readDB() throws ExecutionException, InterruptedException {



        //Access to the database creator
        //DatabaseCreator dbCreator = DatabaseCreator.getInstance(ListOfPatientActivity.this);

        //Execute and get all the patients from our database
        patientEntities = new AsyncGetPatients(ListOfPatientActivity.this).execute().get();



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

    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
