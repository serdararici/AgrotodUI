package com.serdararici.dronemarket.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.databinding.FragmentFieldUpdateBinding;
import com.serdararici.dronemarket.ui.viewmodel.FieldUpdateViewModel;
import com.serdararici.dronemarket.ui.viewmodel.FieldsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FieldUpdateFragment extends Fragment {
    private FieldUpdateViewModel viewModelFieldUpdate;
    FragmentFieldUpdateBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelFieldUpdate = new ViewModelProvider(this).get(FieldUpdateViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFieldUpdateBinding.inflate(inflater,container,false);

        FieldUpdateFragmentArgs bundle = FieldUpdateFragmentArgs.fromBundle(getArguments());
        Field fieldUpdate = bundle.getField();

        binding.etFieldUpdateFieldName.setText(fieldUpdate.getFieldName());
        binding.etFieldUpdateFieldArea.setText(String.valueOf(fieldUpdate.getTotalArea()));

        binding.btnBackFieldUpdate.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btnConfirmFieldUpdate.setOnClickListener(view -> {
            String updateName = binding.etFieldUpdateFieldName.getText().toString();
            double updateArea = Double.parseDouble((binding.etFieldUpdateFieldArea).getText().toString());
            viewModelFieldUpdate.updateField(fieldUpdate.getFieldId(), updateName, updateArea, fieldUpdate.getProcessedArea(), fieldUpdate.getDate(), fieldUpdate.getTime(), fieldUpdate.getLatitude(), fieldUpdate.getLongitude());

            Navigation.findNavController(view).popBackStack();
            Toast.makeText(requireContext(), getString(R.string.updateFieldMessage), Toast.LENGTH_LONG).show();

        });

        return binding.getRoot();
    }
}