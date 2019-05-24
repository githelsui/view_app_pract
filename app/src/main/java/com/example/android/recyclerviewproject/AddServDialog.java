package com.example.android.recyclerviewproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddServDialog extends AppCompatDialogFragment {

    private EditText prgrmName;
    private EditText currHrs;
    private EditText role;
    private AddServeDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //creates Dialog java class by taking info from getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates an object that helps configure Dialog java class to its correct xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //creates the View in which we can access the Dialog xml file's contents
        View myView = inflater.inflate(R.layout.layout_dialog, null);

        //Attaches java dialog onto its XML layout file (GUI)
        builder.setView(myView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) { }
                }) //when "Cancelled" do nothing
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        String name = prgrmName.getText().toString();
                        //TODO #2 Change int hrs into double hrs
                        double hrs = Double.parseDouble(currHrs.getText().toString());
                        String myRole = role.getText().toString();

                        listener.applyText(name, hrs, myRole); //creates Exampleitem then appends it to list
                    }
                });
        prgrmName = myView.findViewById(R.id.program_name);
        currHrs = myView.findViewById(R.id.current_hours);
        role = myView.findViewById(R.id.position_lbl);

        //returns the Dialog View
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (AddServeDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "  //  IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface AddServeDialogListener{
        //TODO #3 change parameter int hrs into double hrs
        void applyText(String name, double hrs, String myRole);
    }
}
