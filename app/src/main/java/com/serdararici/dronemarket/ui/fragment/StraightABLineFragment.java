package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serdararici.dronemarket.MainActivity;
import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Line;
import com.serdararici.dronemarket.databinding.FragmentStraightABLineBinding;
import com.serdararici.dronemarket.ui.adapter.StraightLineAdapter;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;
import com.serdararici.dronemarket.ui.viewmodel.StraightABLineViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StraightABLineFragment extends Fragment {
    FragmentStraightABLineBinding binding;
    private StraightABLineViewModel viewModelStraightABLine;
    private MainViewModel viewModelMain;
    private Line selectedLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelStraightABLine = new ViewModelProvider(this).get(StraightABLineViewModel.class);
        viewModelMain = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStraightABLineBinding.inflate(inflater,container,false);

        binding.rvStraightLineFragment.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModelStraightABLine.straightABLineList.observe(getViewLifecycleOwner(), straightABLineList-> {
            StraightLineAdapter straightLineAdapter = new StraightLineAdapter(requireContext(), straightABLineList,viewModelStraightABLine,viewModelMain);
            binding.rvStraightLineFragment.setAdapter(straightLineAdapter);
        });

        // seçilen kard dinleniyor
        viewModelStraightABLine.selectedStraightLine.observe(getViewLifecycleOwner(), selectedStraightLine-> {
            selectedLine = selectedStraightLine;
        });
        binding.btnSelectStraightABLine.setOnClickListener(view -> {
            viewModelMain.selectLine(selectedLine);
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).closeDrawer();
            }
        });


        binding.btnNewStraightABLine.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.newStraightABLineFragment);
        });

        binding.btnBackStraightABLine.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModelStraightABLine.getStraightABLine();
    }
}