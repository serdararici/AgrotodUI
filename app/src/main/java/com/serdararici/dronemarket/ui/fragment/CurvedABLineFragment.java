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
import com.serdararici.dronemarket.databinding.FragmentCurvedABLineBinding;
import com.serdararici.dronemarket.ui.adapter.CurvedLineAdapter;
import com.serdararici.dronemarket.ui.viewmodel.CurvedABLineViewModel;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CurvedABLineFragment extends Fragment {
    FragmentCurvedABLineBinding binding;
    private CurvedABLineViewModel viewModelCurvedABLine;
    private MainViewModel viewModelMain;

    private Line selectedLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelCurvedABLine = new ViewModelProvider(this).get(CurvedABLineViewModel.class);
        viewModelMain = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCurvedABLineBinding.inflate(inflater,container,false);

        binding.rvCurvedLineFragment.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModelCurvedABLine.curvedABLineList.observe(getViewLifecycleOwner(), curvedABLineList-> {
            CurvedLineAdapter curvedLineAdapter = new CurvedLineAdapter(requireContext(), curvedABLineList,viewModelCurvedABLine,viewModelMain);
            binding.rvCurvedLineFragment.setAdapter(curvedLineAdapter);
        });

        // seçilen kard dinleniyor
        viewModelCurvedABLine.selectedCurvedLine.observe(getViewLifecycleOwner(), selectedStraightLine-> {
            selectedLine = selectedStraightLine;
        });
        binding.btnSelectCurvedABLine.setOnClickListener(view -> {
            viewModelMain.selectLine(selectedLine);
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).closeDrawer();
            }
        });


        binding.btnNewCurvedABLine.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.newCurvedABLineFragment);
        });

        binding.btnBackCurvedABLine.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModelCurvedABLine.getCurvedABLine();
    }
}