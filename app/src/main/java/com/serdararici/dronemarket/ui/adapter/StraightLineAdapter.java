package com.serdararici.dronemarket.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Line;
import com.serdararici.dronemarket.databinding.CardLineBinding;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;
import com.serdararici.dronemarket.ui.viewmodel.StraightABLineViewModel;

import java.util.List;

public class StraightLineAdapter extends RecyclerView.Adapter<StraightLineAdapter.CardStraightLineHolder>{
    private Context mContext;
    private List<Line> lineList;
    private StraightABLineViewModel viewModelStraightABLine;
    private MainViewModel viewModelMain;

    private int selectedPosition = -1; // Seçili kartın pozisyonu

    public StraightLineAdapter(Context mContext, List<Line> lineList, StraightABLineViewModel viewModelStraightABLine, MainViewModel viewModelMain) {
        this.mContext = mContext;
        this.lineList = lineList;
        this.viewModelStraightABLine = viewModelStraightABLine;
        this.viewModelMain = viewModelMain;
    }

    public class CardStraightLineHolder extends RecyclerView.ViewHolder {
        private CardLineBinding binding;

        public CardStraightLineHolder(CardLineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CardStraightLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardLineBinding binding = CardLineBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new CardStraightLineHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardStraightLineHolder holder, int position) {
        Line line = lineList.get(position);
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
            viewModelStraightABLine.deleteStraightABLine(line.getLineId());
            Toast.makeText(mContext,line.getLineName() + " " + mContext.getString(R.string.lineDeleted), Toast.LENGTH_LONG).show();
        });

        holder.binding.cvLine.setOnClickListener(view -> {
            selectedPosition = holder.getAdapterPosition(); // Yeni seçili pozisyonu güncelle
            viewModelStraightABLine.selectStraightLine(line);
            //viewModelMain.selectLine(line);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return lineList.size();
    }


}
