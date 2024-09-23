package com.serdararici.dronemarket.ui.fragment;

import android.content.Context;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.databinding.FragmentNewFieldBinding;
import com.serdararici.dronemarket.ui.viewmodel.FieldsViewModel;
import com.serdararici.dronemarket.ui.viewmodel.NewFieldViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class NewFieldFragment extends Fragment {
    private NewFieldViewModel viewModelNewField;
    private FragmentNewFieldBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelNewField = new ViewModelProvider(this).get(NewFieldViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewFieldBinding.inflate(inflater, container,false);


        binding.btnBackNewField.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        binding.btnConfirmNewField.setOnClickListener(view -> {
            String newFieldName = binding.etNewFieldName.getText().toString();
            double newFieldArea = Double.parseDouble((binding.etNewFieldArea).getText().toString());

            viewModelNewField.addNewField(newFieldName,newFieldArea);
            //confirm(newFieldName,newFieldArea);
            Navigation.findNavController(view).popBackStack();
            Toast.makeText(requireContext(), getString(R.string.addedNewField), Toast.LENGTH_LONG).show();

        });

        // klavye ile alakalı işlemler
        binding.etNewFieldName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                    binding.etNewFieldArea.requestFocus();
                    return true;
                }
                return false;
            }
        });
        binding.etNewFieldArea.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
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