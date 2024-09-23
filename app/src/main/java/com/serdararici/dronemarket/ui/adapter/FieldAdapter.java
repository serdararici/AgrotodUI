package com.serdararici.dronemarket.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.databinding.CardFieldBinding;
import com.serdararici.dronemarket.ui.fragment.FieldsFragmentDirections;
import com.serdararici.dronemarket.ui.viewmodel.FieldsViewModel;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;

import java.util.List;

public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.CardFieldHolder>{
    private Context mContext;
    private List<Field> fieldList;
    private FieldsViewModel viewModelField;
    private MainViewModel viewModelMain;


    public FieldAdapter(Context mContext, List<Field> fieldList, FieldsViewModel viewModelField, MainViewModel viewModelMain) {
        this.mContext = mContext;
        this.fieldList = fieldList;
        this.viewModelField = viewModelField;
        this.viewModelMain = viewModelMain;
    }

    public class CardFieldHolder extends RecyclerView.ViewHolder {
        private CardFieldBinding binding;

        public CardFieldHolder(CardFieldBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CardFieldHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardFieldBinding binding = CardFieldBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new CardFieldHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardFieldHolder holder, int position) {
        Field field = fieldList.get(position);
        holder.binding.fieldCardFieldName.setText(field.getFieldName());
        String date = field.getDate();
        String time = field.getTime();
        String dateAndTime = date + " - " + time;
        String totalArea = mContext.getString(R.string.total_field) + " " + field.getTotalArea() + " Ha";
        String processedArea = mContext.getString(R.string.processed_field) + " " + field.getProcessedArea() + " Ha";
        holder.binding.fieldCardDateAndTime.setText(dateAndTime);
        holder.binding.cardFieldTotalArea.setText(totalArea);
        holder.binding.cardFieldProcessedArea.setText(processedArea);


        holder.binding.btnSelect.setOnClickListener(view -> {
            //Navigation.findNavController(view).navigate(R.id.toolsFragment);
            viewModelMain.selectField(field);
            Navigation.findNavController(view).navigate(R.id.workFragment);
        });
        holder.binding.btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle(R.string.deleteConfirm)
                    .setMessage(R.string.deleteMessage)
                    .setPositiveButton(R.string.delete, (dialog, which) -> {
                        // Silme işlemi onaylandı, silme işlemini yap
                        viewModelField.deleteField(field.getFieldId());
                    })
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        // Silme işlemi iptal edildi, dialog kapanacak
                        dialog.dismiss();
                    })
                    .show();
        });

        holder.binding.btnDetail.setOnClickListener(view -> {
            FieldsFragmentDirections.ActionFieldsFragmentToFieldDetailsFragment direction = FieldsFragmentDirections.actionFieldsFragmentToFieldDetailsFragment(field);
            Navigation.findNavController(view).navigate(direction);
        });
    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }
}
