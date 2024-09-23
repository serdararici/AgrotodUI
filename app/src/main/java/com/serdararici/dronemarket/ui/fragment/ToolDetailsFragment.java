package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.databinding.FragmentToolDetailsBinding;

public class ToolDetailsFragment extends Fragment {
    private FragmentToolDetailsBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentToolDetailsBinding.inflate(inflater,container,false);

        ToolDetailsFragmentArgs bundle = ToolDetailsFragmentArgs.fromBundle(getArguments());
        Tool toolDetails = bundle.getTool();


        binding.tvToolDetailsName.setText(toolDetails.getToolName());
        String toolType = getString(R.string.toolType) + " " + toolDetails.getToolType();
        binding.tvToolDetailsTotalType.setText(toolType);
        String toolWidth = getString(R.string.toolWidth) + " " + toolDetails.getToolWidth() + " CM";
        binding.tvToolDetailsToolWidth.setText(toolWidth);
        String date = toolDetails.getDate();
        String time = toolDetails.getTime();
        String dateAndTime = getString(R.string.registration_date) + " " + date + " - " + time;
        binding.tvToolDetailsRegistrationDate.setText(dateAndTime);

        binding.btnBackToolDetails.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnUpdateToolDetails.setOnClickListener(view -> {
            ToolDetailsFragmentDirections.ActionToolDetailsFragmentToToolUpdateFragment direction = ToolDetailsFragmentDirections.actionToolDetailsFragmentToToolUpdateFragment(toolDetails);
            Navigation.findNavController(view).navigate(direction);
        });


        return binding.getRoot();
    }
}