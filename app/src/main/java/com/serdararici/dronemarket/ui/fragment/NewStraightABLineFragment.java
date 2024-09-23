package com.serdararici.dronemarket.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.databinding.FragmentNewStraightABLineBinding;
import com.serdararici.dronemarket.databinding.FragmentStraightABLineBinding;
import com.serdararici.dronemarket.ui.viewmodel.NewFieldViewModel;
import com.serdararici.dronemarket.ui.viewmodel.NewStraightABLineViewModel;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewStraightABLineFragment extends Fragment {
    FragmentNewStraightABLineBinding binding;
    private NewStraightABLineViewModel viewModelNewStraightABLine;

    double randomLineDegree = 0.0;
    String lineName;
    boolean wrongInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelNewStraightABLine = new ViewModelProvider(this).get(NewStraightABLineViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewStraightABLineBinding.inflate(inflater,container,false);


        binding.btnStraightLineA.setEnabled(true);
        binding.btnStraightLineB.setEnabled(false);
        binding.btnConfirmNewStraightABLine.setEnabled(false);
        binding.btnConfirmNewStraightABLine.setAlpha(0.5f);

        binding.btnStraightLineA.setOnClickListener(view -> {
            binding.btnStraightLineA.setEnabled(false);
            binding.btnStraightLineB.setEnabled(true);
        });


        binding.btnStraightLineB.setOnClickListener(view -> {
            viewModelNewStraightABLine.calculateBearing();
            viewModelNewStraightABLine.lineDegree.observe(getViewLifecycleOwner(), lineDegree -> {
                if (lineDegree != null) {
                    randomLineDegree = lineDegree;
                    lineName = degreeText(randomLineDegree);
                    binding.tvNewStraightABLineResult.setText(lineName);
                    Log.e("lineDegree", String.valueOf(lineDegree));
                    binding.btnConfirmNewStraightABLine.setEnabled(true);
                    binding.btnConfirmNewStraightABLine.setAlpha(1);
                }
            });
        });


        binding.btnConfirmNewStraightABLine.setOnClickListener(view -> {
            lineName = degreeText(randomLineDegree);
            viewModelNewStraightABLine.addNewABLine(lineName, "straight");
            Navigation.findNavController(view).popBackStack();
            Toast.makeText(requireContext(), getString(R.string.newStraightABLineMessage), Toast.LENGTH_LONG).show();
        });

        binding.btnBackNewStraightABLine.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        /* old version

        binding.etNewStraightLineDegree.setEnabled(false);

        binding.btnStraightLineA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etNewStraightLineDegree.setEnabled(true);
                binding.btnStraightLineA.setBackgroundColor(Color.GRAY);  // Aktif olduğunda beyaz arka plan
            }
        });



        binding.textInputNewStraightLineDegree.setOnClickListener(view -> {
            binding.etNewStraightLineDegree.setHint("0");
        });
        binding.etNewStraightLineDegree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String textChanged = charSequence.toString();

                if (!textChanged.isEmpty()) {
                    double degree = Double.parseDouble(textChanged);
                    binding.tvNewStraightABLineResult.setText(degreeText(degree));
                    lineName = degreeText(degree);
                    if (Objects.equals(degreeText(degree), "incorrect")) {
                        String message = getString(R.string.mistake) + " - " + getString(R.string.enterValidValue);
                        binding.tvNewStraightABLineResult.setText(message);
                    }
                } else {
                    // Eğer metin boşsa varsayılan olarak 0.0 kullan
                    binding.tvNewStraightABLineResult.setText(degreeText(0.0));
                    lineName = degreeText(0.0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.btnConfirmNewStraightABLine.setOnClickListener(view -> {
            if (checkAll()){
                if(!Objects.equals(lineName, "incorrect")) {
                    Log.e("lineDireciton", lineName);
                    System.out.println(lineName);
                } else {
                    Toast.makeText(requireContext(),getString(R.string.enterValidValue), Toast.LENGTH_LONG).show();
                }
            }

        });
        binding.btnBackNewStraightABLine.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

         */



        return binding.getRoot();
    }



    public String degreeText(double degree){
        String formattedDegree = formatDouble(degree);
        if((0 <= degree && degree <= 22) || (338 <= degree && degree < 360) ) {
            return "AB " + formattedDegree + "° " + getString(R.string.north);
        } else if(23 <= degree && degree <= 67){
            return "AB " + formattedDegree + "° " + getString(R.string.northEast);
        } else if(68 <= degree && degree <= 111){
            return "AB " + formattedDegree + "° " + getString(R.string.east);
        } else if(112 <= degree && degree <= 157){
            return "AB " + formattedDegree + "° " + getString(R.string.southEast);
        } else if(158 <= degree && degree <= 202){
            return "AB " + formattedDegree + "° " + getString(R.string.south);
        } else if(203 <= degree && degree <= 247){
            return "AB " + formattedDegree + "° " + getString(R.string.southWest);
        } else if(248 <= degree && degree <= 292){
            return "AB " + formattedDegree + "° " + getString(R.string.west);
        } else if(293 <= degree && degree <= 337){
            return "AB " + formattedDegree + "° " + getString(R.string.northWest);
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

    //Bu şekilde string.format kullanıldığında dil ayarlarına göre yanlış sonuç verebiliyor
    /*
    public String formatDouble(double value) {
        // Eğer sayı tam sayıysa (ondalık kısmı 0 ise)
        if (value == (long) value) {
            // Tam sayı kısmı olarak formatla
            return String.format("%d", (long) value);
        } else {
            // Ondalık kısmı bir basamak olarak formatla
            double decimalValue = Double.parseDouble(String.format("%.1f", value));
            // Eğer ondalık kısım 6.012563 şeklinde olursa 6.0 olarak değilde 6 olarak yazılacak
            if (decimalValue == (long) decimalValue) {
                // Tam sayı kısmı olarak formatla
                return String.format("%d", (long) value);
            } else {
                return String.format("%.1f", value);
            }
        }
    }

     */

}