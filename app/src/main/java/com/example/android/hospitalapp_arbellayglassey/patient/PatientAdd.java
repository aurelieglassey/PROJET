package com.example.android.hospitalapp_arbellayglassey.patient;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.hospitalapp_arbellayglassey.R;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.async.patient.AsyncAddPatient;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.async.treatment.AsyncAddTreatment;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.PatientEntity;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.TreatmentEntity;
import com.example.android.hospitalapp_arbellayglassey.listActivity.ListOfMedecineActivity;
import com.example.android.hospitalapp_arbellayglassey.listActivity.ListOfPatientActivity;
import com.example.android.hospitalapp_arbellayglassey.medecine.MedecineModify;
import com.example.android.hospitalapp_arbellayglassey.settings.Settings;
import com.example.android.hospitalapp_arbellayglassey.treatment.TreatmentDetails;

import java.util.concurrent.ExecutionException;

public class PatientAdd extends AppCompatActivity {

    // variable
    private Button okAddPatient;
    private PatientEntity patientEntity;
    private TreatmentEntity treatmentEntity;
    private String messageError = "";
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add);

        // set the message
        messageError = this.getString(R.string.error_enter_field);

        //Button to confirm that we want to add this new patient
        pressOkAddPatient();

    }

    //When the user want to add a new patient and the data are ok to be added
    //button ok
    public void pressOkAddPatient(){
        //Find the id view for the button ok to add a patient
        okAddPatient = (Button) findViewById(R.id.btn_ok_add_patient);

        //Add a listener to access to the List of patient activity
        okAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get all id of a patient of the texte view
                EditText namePatient = (EditText)findViewById(R.id.namePatientDetails);
                EditText agePatient = (EditText)findViewById(R.id.agePatientAdd);
                EditText genderPatient = (EditText)findViewById(R.id.genderPatientAdd);
                EditText roomPatient = (EditText)findViewById(R.id.ageRoomPatientAdd);
                EditText bloodPatient = (EditText)findViewById(R.id.bloodGroupPatientAdd);
                EditText admissionPatient = (EditText)findViewById(R.id.admissionPatientAdd);

                //Check if there is an error when the user fill the textview
                //or if the user doesn't fill anyting.
                int error = 0;

                //Check name
                if (namePatient.getText().toString().length() == 0  ){
                   //namePatient.setError(messageError);
                    namePatient.setError(messageError);
                    namePatient.requestFocus();
                    error = 1;
                }

                //Check age
                if(agePatient.getText().toString().length() == 0){
                    agePatient.setError(messageError);
                    agePatient.requestFocus();
                    error = 1;
                }

                //Check Gender
                if (genderPatient.getText().toString().length() != 1){
                    genderPatient.setError(messageError);
                    genderPatient.requestFocus();
                    error = 1;
                }

                //Check Room
                if(roomPatient.getText().toString().length() == 0){
                    roomPatient.setError(messageError);
                    roomPatient.requestFocus();
                    error = 1;
                }

                //Check blood Group
                if (bloodPatient.getText().toString().length() == 0  ){
                    bloodPatient.setError(messageError);
                    bloodPatient.requestFocus();
                    error = 1;
                }

                //Check reason of admission
                if (admissionPatient.getText().toString().length() == 0  ){
                    admissionPatient.setError(messageError);
                    admissionPatient.requestFocus();
                    error = 1;
                }


                //if the error is 0, it means that the fields are correctly fill
                if (error == 0){

                    //Add the data of the text view in the patient
                    patientEntity = new PatientEntity();
                    patientEntity.setName(namePatient.getText().toString());
                    patientEntity.setAge(Integer.parseInt(agePatient.getText().toString()));
                    patientEntity.setGender(genderPatient.getText().charAt(0));
                    patientEntity.setRoomNumber(Integer.parseInt(roomPatient.getText().toString()));
                    patientEntity.setBloodGroup(bloodPatient.getText().toString());
                    patientEntity.setReasonAdmission(admissionPatient.getText().toString());

                    //Call the method add patient
                    addPatient(patientEntity);

                    //PatientAdd.this.startActivity(intent);
                    finish();
                }

                error = 0;

            }

        });
    }


    //Add a patient with the class AsyncAddPatient
    public void addPatient(PatientEntity patientEntity){

        try {
            //Add the new patient in the db
            Long id = new AsyncAddPatient(PatientAdd.this, patientEntity).execute().get();

            //Add a treatment for the new patient.
            //Create a name  of a treatment just for the new patient
            //By default the new name of treatment is : Treatment - xxx
            String namePatient = patientEntity.getName().toString();
            String officialNameTreatment = " Treatment - " + namePatient;

            //create a treatment
            //1 is the default value for max quantity of this new treatment assigned to this new patient
            treatmentEntity = new TreatmentEntity(officialNameTreatment, 1,  id.intValue());

            //Add the treatment in the db
            Long idTreatment = new AsyncAddTreatment(PatientAdd.this, treatmentEntity).execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        setTitle(R.string.title_new_patient);
        setupActionBar();
        setupNavBar();
        return true;

    }
    private void setupActionBar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }
        else{
            finish();
        }

        return true;
    }

    //setup navigation drawer
    //this method setup the navigation drawer and implement the button to go to the list
    public void setupNavBar() {
        mDrawerLayout = findViewById(R.id.drawer_layout_add_patient);

        NavigationView navigationView = findViewById(R.id.nav_view_add_patient);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped

                        switch (menuItem.getItemId()) {
                            case R.id.nav_list_of_patient:
                                Intent intentPatient = new Intent(PatientAdd.this, ListOfPatientActivity.class);
                                PatientAdd.this.startActivity(intentPatient);
                                finish();
                                break;
                            case R.id.nav_list_of_medicine:
                                Intent intentMed = new Intent(PatientAdd.this, ListOfMedecineActivity.class);
                                PatientAdd.this.startActivity(intentMed);
                                finish();
                                break;
                            default:
                                break;
                        }
                        mDrawerLayout.closeDrawers();


                        return true;
                    }
                });
    }
}

