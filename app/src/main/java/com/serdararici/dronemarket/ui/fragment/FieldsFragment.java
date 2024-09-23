package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

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
import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.databinding.FragmentFieldsBinding;
import com.serdararici.dronemarket.ui.adapter.FieldAdapter;
import com.serdararici.dronemarket.ui.viewmodel.FieldsViewModel;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FieldsFragment extends Fragment {
    private FieldsViewModel viewModelFields;
    private MainViewModel viewModelMain;
    private FragmentFieldsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelFields = new ViewModelProvider(this).get(FieldsViewModel.class);
        viewModelMain = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModelFields.getFields();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFieldsBinding.inflate(inflater,container,false);


        binding.rvFieldFragment.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModelFields.fieldList.observe(getViewLifecycleOwner(), fields -> {
            FieldAdapter fieldAdapter = new FieldAdapter(requireContext(),fields,viewModelFields,viewModelMain);
            binding.rvFieldFragment.setAdapter(fieldAdapter);
        });



        binding.btnNew.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.newFieldFragment);
        });
        binding.btnBack.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });





        return binding.getRoot();
    }
}