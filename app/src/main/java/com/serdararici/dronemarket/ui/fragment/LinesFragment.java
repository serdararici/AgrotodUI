package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serdararici.dronemarket.MainActivity;
import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.databinding.FragmentLinesBinding;

public class LinesFragment extends Fragment {
    private FragmentLinesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLinesBinding.inflate(inflater,container,false);



        binding.cvStraightLine.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.straightABLineFragment);
        });

        binding.cvCurvedLine.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.curvedABLineFragment);
        });

        binding.btnBackLines.setOnClickListener(view -> {
            // MainActivity'deki closeDrawer metodunu çağır
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).closeDrawer();
            }
        });


        return binding.getRoot();
    }
}