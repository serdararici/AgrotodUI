package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.databinding.FragmentNewCurvedABLineBinding;
import com.serdararici.dronemarket.ui.viewmodel.NewCurvedABLineViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewCurvedABLineFragment extends Fragment {
    FragmentNewCurvedABLineBinding binding;
    private NewCurvedABLineViewModel viewModelNewCurvedABLine;

    double randomLineDegree = 0.0;
    String lineName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelNewCurvedABLine = new ViewModelProvider(this).get(NewCurvedABLineViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewCurvedABLineBinding.inflate(inflater,container,false);

        binding.btnCurvedLineA.setEnabled(true);
        binding.btnCurvedLineB.setEnabled(false);
        binding.btnConfirmNewCurvedABLine.setEnabled(false);
        binding.btnConfirmNewCurvedABLine.setAlpha(0.5f);

        binding.btnCurvedLineA.setOnClickListener(view -> {
            binding.btnCurvedLineA.setEnabled(false);
            binding.btnCurvedLineB.setEnabled(true);
        });

        binding.btnCurvedLineB.setOnClickListener(view -> {
            viewModelNewCurvedABLine.calculateBearing();
            viewModelNewCurvedABLine.lineDegree.observe(getViewLifecycleOwner(), lineDegree -> {
                if (lineDegree != null) {
                    randomLineDegree = lineDegree;
                    lineName = degreeText(randomLineDegree);
                    binding.tvCurvedABLineResult.setText(lineName);
                    Log.e("lineDegree", String.valueOf(lineDegree));
                    binding.btnConfirmNewCurvedABLine.setEnabled(true);
                    binding.btnConfirmNewCurvedABLine.setAlpha(1);
                }
            });
        });

        binding.btnConfirmNewCurvedABLine.setOnClickListener(view -> {
            lineName = degreeText(randomLineDegree);
            viewModelNewCurvedABLine.addNewABLine(lineName, "curved");
            Navigation.findNavController(view).popBackStack();
            Toast.makeText(requireContext(), getString(R.string.newCurvedABLineMessage), Toast.LENGTH_LONG).show();
        });

        binding.btnBackNewCurvedABLine.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();
    }


    public String degreeText(double degree){
        Log.e("degree", String.valueOf(degree));
        String formattedDegree = formatDouble(degree);
        if((0 <= degree && degree <= 22) || (338 <= degree && degree < 360) ) {
            return "Cu " + formattedDegree + "° " + getString(R.string.north);
        } else if(23 <= degree && degree <= 67){
            return "Cu " + formattedDegree + "° " + getString(R.string.northEast);
        } else if(68 <= degree && degree <= 111){
            return "Cu " + formattedDegree + "° " + getString(R.string.east);
        } else if(112 <= degree && degree <= 157){
            return "Cu " + formattedDegree + "° " + getString(R.string.southEast);
        } else if(158 <= degree && degree <= 202){
            return "Cu " + formattedDegree + "° " + getString(R.string.south);
        } else if(203 <= degree && degree <= 247){
            return "Cu " + formattedDegree + "° " + getString(R.string.southWest);
        } else if(248 <= degree && degree <= 292){
            return "Cu " + formattedDegree + "° " + getString(R.string.west);
        } else if(293 <= degree && degree <= 337){
            return "Cu " + formattedDegree + "° " + getString(R.string.northWest);
        } else return "incorrect";
    }

    public String formatDouble(double value) {
        // Eğer sayı tam sayıysa (ondalık kısmı 0 ise)
        if (value == (long) value) {
            // Tam sayı kısmı olarak döndür
            return Long.toString((long) value);
        } else {
            // Ondalık kısmı bir basamak olarak döndür
            double decimalValue = Math.round(value * 10.0) / 10.0; // Bir basamaklı ondalık için
            String decimalString = Double.toString(decimalValue);

            // Eğer ondalık kısmı 0 ise sadece tam sayıyı döndür
            if (decimalString.endsWith(".0")) {
                return decimalString.substring(0, decimalString.length() - 2); // Son iki karakteri kes
            } else {
                return decimalString; // Tam değeri döndür
            }
        }
    }

}