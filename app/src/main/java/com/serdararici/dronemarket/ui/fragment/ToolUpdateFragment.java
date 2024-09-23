package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.databinding.FragmentToolUpdateBinding;
import com.serdararici.dronemarket.ui.viewmodel.FieldUpdateViewModel;
import com.serdararici.dronemarket.ui.viewmodel.ToolUpdateViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ToolUpdateFragment extends Fragment {
    FragmentToolUpdateBinding binding;
    private ToolUpdateViewModel viewModelToolUpdate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelToolUpdate = new ViewModelProvider(this).get(ToolUpdateViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentToolUpdateBinding.inflate(inflater,container,false);

        ToolUpdateFragmentArgs bundle = ToolUpdateFragmentArgs.fromBundle(getArguments());
        Tool toolUpdate = bundle.getTool();

        binding.etToolUpdateToolName.setText(toolUpdate.getToolName());
        binding.etToolUpdateToolType.setText(toolUpdate.getToolType());
        binding.etToolUpdateToolWidth.setText(String.valueOf(toolUpdate.getToolWidth()));

        binding.btnBackToolUpdate.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnConfirmToolUpdate.setOnClickListener(view -> {
            // onayladıktan sonra güncelleme yapılsın ve tools sayfasına gitsin

            String toolName = binding.etToolUpdateToolName.getText().toString();
            double toolWidth = Double.parseDouble((binding.etToolUpdateToolWidth).getText().toString());
            String toolType = binding.etToolUpdateToolType.getText().toString();
            viewModelToolUpdate.updateTool(toolUpdate.getToolId(), toolName, toolWidth, toolType, toolUpdate.getDate(), toolUpdate.getTime());

            Navigation.findNavController(view).popBackStack();
            Toast.makeText(requireContext(), getString(R.string.updateToolMessage), Toast.LENGTH_LONG).show();
        });

        return binding.getRoot();
    }
}