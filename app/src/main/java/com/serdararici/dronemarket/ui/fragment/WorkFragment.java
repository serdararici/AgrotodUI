package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serdararici.dronemarket.MainActivity;
import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.databinding.FragmentWorkBinding;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkFragment extends Fragment {
    FragmentWorkBinding binding;
    MainViewModel viewModelMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelMain = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkBinding.inflate(inflater,container,false);

        viewModelMain.selectedField.observe(getViewLifecycleOwner(), field -> {
            if(field != null) {
                String fieldName = getString(R.string.fieldNameInfo) + " " + field.getFieldName();
                String fieldTotalArea = getString(R.string.fieldTotalAreaInfo) +" "+ field.getTotalArea() + " Ha";
                String fieldProcessedArea = getString(R.string.fieldProcessedAreaInfo) +" "+ field.getProcessedArea() + " Ha";

                binding.tvFieldInfoName.setText(fieldName);
                binding.tvFieldInfoTotalArea.setText(fieldTotalArea);
                binding.tvFieldInfoProcessedArea.setText(fieldProcessedArea);
            } else {
                String fieldName = getString(R.string.field_name);
                String fieldTotalArea = "0.0 Ha";
                String fieldProcessedArea = "0.0 Ha";

                binding.tvFieldInfoName.setText(fieldName);
                binding.tvFieldInfoTotalArea.setText(fieldTotalArea);
                binding.tvFieldInfoProcessedArea.setText(fieldProcessedArea);
            }



        });

        viewModelMain.selectedTool.observe(getViewLifecycleOwner(), tool -> {
            if(tool!= null) {
                String toolName = getString(R.string.toolNameInfo) + " " + tool.getToolName();
                String toolType = getString(R.string.toolTypeInfo) + " " + tool.getToolType();
                String toolWidth = getString(R.string.toolWidthInfo) + " " + tool.getToolWidth() + " CM";

                binding.tvToolInfoName.setText(toolName);
                binding.tvToolInfoToolType.setText(toolType);
                binding.tvToolInfoToolWidth.setText(toolWidth);
            } else {
                String toolName = getString(R.string.toolNameInfo);
                String toolType = getString(R.string.toolTypeInfo);
                String toolWidth = "0 CM";

                binding.tvToolInfoName.setText(toolName);
                binding.tvToolInfoToolType.setText(toolType);
                binding.tvToolInfoToolWidth.setText(toolWidth);
            }

        });

        binding.btnWorkStart.setOnClickListener(view -> {
            viewModelMain.isWorkSelect.setValue(true);
            // MainActivity'deki closeDrawer metodunu çağır
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).closeDrawer();
            }
            Navigation.findNavController(view).navigate(R.id.toolsFragment);
        });

        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnCancel.setOnClickListener(view -> {
            //Gönderilen verileri sıfırla
            viewModelMain.selectField(null);
            viewModelMain.selectTool(null);
            // MainActivity'deki closeDrawer metodunu çağır
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).closeDrawer();
            }
            Navigation.findNavController(view).navigate(R.id.toolsFragment);
        });

        return binding.getRoot();
    }
}