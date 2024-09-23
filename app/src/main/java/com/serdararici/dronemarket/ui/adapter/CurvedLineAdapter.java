package com.serdararici.dronemarket.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Line;
import com.serdararici.dronemarket.databinding.CardLineBinding;
import com.serdararici.dronemarket.ui.viewmodel.CurvedABLineViewModel;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;

import java.util.List;

public class CurvedLineAdapter extends RecyclerView.Adapter<CurvedLineAdapter.CardCurvedLineHolder>{
    private Context mContext;
    private List<Line> curvedLineList;
    private CurvedABLineViewModel viewModelCurvedABLine;
    private MainViewModel viewModelMain;

    private int selectedPosition = -1; // Seçili kartın pozisyonu

    public CurvedLineAdapter(Context mContext, List<Line> curvedLineList, CurvedABLineViewModel viewModelCurvedABLine, MainViewModel viewModelMain) {
        this.mContext = mContext;
        this.curvedLineList = curvedLineList;
        this.viewModelCurvedABLine = viewModelCurvedABLine;
        this.viewModelMain = viewModelMain;
    }

    public class CardCurvedLineHolder extends RecyclerView.ViewHolder {
        private CardLineBinding binding;

        public CardCurvedLineHolder(CardLineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    @NonNull
    @Override
    public CardCurvedLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardLineBinding binding = CardLineBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new CardCurvedLineHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardCurvedLineHolder holder, int position) {
        Line line = curvedLineList.get(position);
        holder.binding.tvCvLineResult.setText(line.getLineName());

        // Seçim durumuna göre arka plan rengi ayarla
        if (position == selectedPosition) {
            holder.binding.cvLine.setCardBackgroundColor(mContext.getColor(R.color.my_light_primary)); // Seçili rengini ayarla
            holder.binding.tvCvLineResult.setTextColor(mContext.getColor(R.color.white));
            holder.binding.ivClearLine.setImageResource(R.drawable.baseline_clear_24_white);
        } else {
            holder.binding.cvLine.setCardBackgroundColor(mContext.getColor(R.color.white)); // Normal rengini ayarla
            holder.binding.tvCvLineResult.setTextColor(mContext.getColor(R.color.black));
            holder.binding.ivClearLine.setImageResource(R.drawable.baseline_clear_24_black);
        }

        holder.binding.ivClearLine.setOnClickListener(view -> {
            viewModelCurvedABLine.deleteCurvedABLine(line.getLineId());
            Toast.makeText(mContext,line.getLineName() + " " + mContext.getString(R.string.lineDeleted), Toast.LENGTH_LONG).show();
        });

        holder.binding.cvLine.setOnClickListener(view -> {
            selectedPosition = holder.getAdapterPosition(); // Yeni seçili pozisyonu güncelle
            viewModelCurvedABLine.selectCurvedLine(line);
            //viewModelMain.selectLine(line);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return curvedLineList.size();
    }
}
