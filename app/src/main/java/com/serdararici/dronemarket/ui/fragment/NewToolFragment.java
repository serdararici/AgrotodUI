package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.databinding.FragmentNewToolBinding;
import com.serdararici.dronemarket.ui.viewmodel.FieldsViewModel;
import com.serdararici.dronemarket.ui.viewmodel.NewToolViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewToolFragment extends Fragment {
    private FragmentNewToolBinding binding;
    private NewToolViewModel viewModelNewTool;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelNewTool = new ViewModelProvider(this).get(NewToolViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewToolBinding.inflate(inflater,container,false);

        binding.btnBackNewTool.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        binding.btnConfirmNewTool.setOnClickListener(view -> {
            String newToolName = binding.etNewToolName.getText().toString();
            String newToolType = binding.etNewToolType.getText().toString();
            double newTooldWidth;
            try {
                newTooldWidth = Double.parseDouble(binding.etNewToolWidth.getText().toString().trim());
            } catch (NumberFormatException e) {
                // Hata durumunda ne yapılacağını belirle
                newTooldWidth = 0.0; // Örneğin, varsayılan bir değer atayabilirsin
                e.printStackTrace();
            }

            viewModelNewTool.addNewTool(newToolName,newToolType,newTooldWidth);
            Navigation.findNavController(view).popBackStack();
            Toast.makeText(requireContext(), getString(R.string.addedNewTool), Toast.LENGTH_LONG).show();

        });

        // İlk TextInputEditText'e odaklanıldığında klavye onay butonuna basıldığında ikinciye geçiş yapılıyor
        binding.etNewToolName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Log.d("KlavyeKontrolSorunusuz", "Editor Action: " + actionId + "Event KeyCode: " + event.getKeyCode()+ event);
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    binding.etNewToolType.requestFocus();
                    //Log.d("KlavyeKontrolSorunusuz", "Editor Action: " + actionId + "Event KeyCode: " + event.getKeyCode()+ event);
                    /*
                    // Klavyeyi gizle
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                     */
                    return true;
                }
                return false;
            }
        });
// İkinci TextInputEditText'e odaklanıldığında klavye onay butonuna basıldığında üçüncüye geçiş yapılıyor
        binding.etNewToolType.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    binding.etNewToolWidth.requestFocus();
                    return true;
                }
                return false;
            }
        });
// Üçüncü TextInputEditText'e odaklanıldığında klavye onay butonuna basıldığında işlemi bitiriliyor
        binding.etNewToolWidth.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    // Klavyeyi gizle
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

        return binding.getRoot();
    }

}