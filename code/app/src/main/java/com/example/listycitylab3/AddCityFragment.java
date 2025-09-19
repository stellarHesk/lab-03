package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    //Add position element.
    private int itemPosition;
    private String positiveText;

    //Constructor to set the value
    public AddCityFragment(){
        itemPosition = -1;
        positiveText = "Add";
    }
    public AddCityFragment(int position){
        itemPosition = position;
        positiveText = "Modify";
    }
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }
    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityText = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceText = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(positiveText + " a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveText, (dialog, which) -> {
                    String cityName = editCityText.getText().toString();
                    String provinceName = editProvinceText.getText().toString();
                    //If itemPosition is -1, then we create a new city.
                    if (itemPosition == -1){
                        listener.addCity(new City(cityName, provinceName));
                    }
                    //Otherwise if itemPosition is not -1, we edit the existing city.
                    else{
                        listener.editCity(new City(cityName, provinceName), itemPosition);
                    }

                })
                .create();
    }
}
