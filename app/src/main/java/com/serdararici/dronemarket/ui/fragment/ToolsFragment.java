package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serdararici.dronemarket.MainActivity;
import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.databinding.FragmentToolsBinding;
import com.serdararici.dronemarket.ui.adapter.ToolAdapter;
import com.serdararici.dronemarket.ui.viewmodel.FieldsViewModel;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;
import com.serdararici.dronemarket.ui.viewmodel.ToolsViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ToolsFragment extends Fragment {
    private FragmentToolsBinding binding;
    private ToolsViewModel viewModelTools;
    private MainViewModel viewModelMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelTools = new ViewModelProvider(this).get(ToolsViewModel.class);
        viewModelMain = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModelTools.getTools();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentToolsBinding.inflate(inflater,container,false);

        binding.rvToolsFragment.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModelTools.toolList.observe(getViewLifecycleOwner(), tools -> {
            ToolAdapter toolAdapter = new ToolAdapter(requireContext(),tools,viewModelTools,viewModelMain);
            binding.rvToolsFragment.setAdapter(toolAdapter);
        });

        binding.btnBackTools.setOnClickListener(view -> {

            // MainActivity'deki closeDrawer metodunu çağır
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).closeDrawer();
            }
        });

        binding.btnNewTools.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.newToolFragment);
        });


        return binding.getRoot();
    }
}