package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.databinding.FragmentFieldDetailsBinding;
import com.serdararici.dronemarket.databinding.FragmentFieldsBinding;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FieldDetailsFragment extends Fragment {
    private FragmentFieldDetailsBinding binding;
    private MainViewModel viewModelMain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelMain = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFieldDetailsBinding.inflate(inflater,container,false);

        FieldDetailsFragmentArgs bundle = FieldDetailsFragmentArgs.fromBundle(getArguments());
        Field fieldDetails = bundle.getField();

        binding.tvFieldDetailsName.setText(fieldDetails.getFieldName());
        String totalArea = getString(R.string.total_field) + " " + fieldDetails.getTotalArea() + " Ha";
        binding.tvFieldDetailsTotalArea.setText(totalArea);
        String processedArea = getString(R.string.processed_field) + " " + fieldDetails.getProcessedArea() + " Ha";
        binding.tvFieldDetailsProcessedArea.setText(processedArea);
        String date = fieldDetails.getDate();
        String time = fieldDetails.getTime();
        String dateAndTime = getString(R.string.registration_date) + " " + date + " - " + time;
        binding.tvFieldDetailsRegistrationDate.setText(dateAndTime);

        binding.btnBackFieldDetails.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnUpdateFieldDetails.setOnClickListener(view -> {
            FieldDetailsFragmentDirections.ActionFieldDetailsFragmentToFieldUpdateFragment direction = FieldDetailsFragmentDirections.actionFieldDetailsFragmentToFieldUpdateFragment(fieldDetails);
            Navigation.findNavController(view).navigate(direction);
        });

        binding.btnShowFieldLocation.setOnClickListener(view -> {
            viewModelMain.selectField(fieldDetails);
            Navigation.findNavController(view).navigate(R.id.fieldLocationOnMapFragment);
        });

        return binding.getRoot();
    }
}