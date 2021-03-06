package com.example.android.hospitalapp_arbellayglassey.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hospitalapp_arbellayglassey.dataAccess.async.link.AsyncDeleteLink;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.async.medecine.AsyncDeleteMedecine;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.MedecineEntity;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.PatientEntity;
import com.example.android.hospitalapp_arbellayglassey.dataAccess.entity.TreatmentMedecineLinkEntity;

import java.util.List;

public class ListViewWithDelBtnAdapterLink extends BaseAdapter implements ListAdapter {

    //variables
    private int layout ;
    private int listViewLayout;
    private Context context;
    private Intent intent;
    List<MedecineEntity> Entities;
    List<TreatmentMedecineLinkEntity> linkEntities;
    int idDelButton;


    // constructor to get all the necessary variables
    public ListViewWithDelBtnAdapterLink(
            List<TreatmentMedecineLinkEntity> linkEntities,List<MedecineEntity> Entities, Context context, Intent intent, int layout, int idListViewLayout, int idDelButton ) {
        this.linkEntities = linkEntities;
        this.context = context;
        this.intent = intent;
        this.layout = layout;
        this.listViewLayout = idListViewLayout;
        this.idDelButton = idDelButton;
        this.Entities = Entities;
    }

    @Override
    public int getCount() {
        return Entities.size();
    }

    @Override
    public Object getItem(int pos) {
        return Entities.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return  0;

    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view = convertView;

        // create the view if null
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
        }

        //Handle TextView and display string from your list
        TextView txtView = (TextView)view.findViewById(listViewLayout);
        txtView.setText(Entities.get(position).getName());


        //On click listener to get the correct details
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("idM", Entities.get(position).getIdM());
                context.startActivity(intent);
                Toast.makeText(context, "Object to see details: "+ Entities.get(position).toString(), Toast.LENGTH_LONG).show();

            }
        });

        //Handle buttons and add onClickListeners
        ImageButton delbtn= (ImageButton)view.findViewById(idDelButton);
        delbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // add a pop up when delete button is activated
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you really want to delete this Medecine ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(context, "Object to vanish: "+ Entities.get(position).getName(), Toast.LENGTH_SHORT).show();



                                new AsyncDeleteLink(context, linkEntities.get(position)).execute();

                              // delete the entites that was delete and notify changes
                                Entities.remove(position);
                                linkEntities.remove(position);
                                notifyDataSetChanged();



                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });


        return view;
    }

    // this method refresh the list to get the new data in the adapter
    public void refreshEvents(List<MedecineEntity> medecineEntities, List<TreatmentMedecineLinkEntity> linkEntities) {
        this.Entities.clear();
        this.Entities.addAll(medecineEntities);
        this.linkEntities.clear();
        this.linkEntities.addAll(linkEntities);
        notifyDataSetChanged();
    }
}
