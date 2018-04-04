package com.example.android.hospitalapp_arbellayglassey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PatientDetails extends AppCompatActivity {

    //Button to show the treatment
    private Button btnShowTreatment;
    private ImageButton btnModifyPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        //Press on the button Show Treatment
        pressBtnShowTreatment();
        pressBtnModifyPatient();


    }

    //When the user decide to display the treatment of a patient
    public void pressBtnShowTreatment(){
        //Find the id view for the button show the treatement of a patient
        btnShowTreatment = (Button) findViewById(R.id.btn_show_treatment);

        //Add a listener to access to the  Treatment details activity
        btnShowTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetails.this, TreatmentDetails.class);
                PatientDetails.this.startActivity(intent);
            }
        });

    }

    //When the user decide to modify the data of the patient
    public void pressBtnModifyPatient(){

            //Find the id view for the button modify the patient
            btnModifyPatient = (ImageButton) findViewById(R.id.btn_modify_Patient);

            //Add a listener to modify the patient
            btnModifyPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PatientDetails.this, PatientModify.class);
                    PatientDetails.this.startActivity(intent);
                }
            });

    }
}
