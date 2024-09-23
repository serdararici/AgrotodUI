package com.serdararici.dronemarket.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.databinding.CardToolsBinding;
import com.serdararici.dronemarket.ui.fragment.ToolsFragmentDirections;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;
import com.serdararici.dronemarket.ui.viewmodel.ToolsViewModel;

import java.util.List;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.CardToolHolder>{
    private Context mContext;
    private List<Tool> toolList;
    private ToolsViewModel viewModelTools;
    private MainViewModel viewModelMain;

    public ToolAdapter(Context mContext, List<Tool> toolList, ToolsViewModel viewModelTools, MainViewModel viewModelMain) {
        this.mContext = mContext;
        this.toolList = toolList;
        this.viewModelTools = viewModelTools;
        this.viewModelMain = viewModelMain;
    }

    public class CardToolHolder extends RecyclerView.ViewHolder {
        private CardToolsBinding binding;

        public CardToolHolder(CardToolsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CardToolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardToolsBinding binding = CardToolsBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new CardToolHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardToolHolder holder, int position) {
        Tool tool = toolList.get(position);
        holder.binding.toolCardToolName.setText(tool.getToolName());
        String task = mContext.getString(R.string.task) + " " + tool.getToolType();
        holder.binding.cardToolTask.setText(task);
        String date = tool.getDate();
        String time = tool.getTime();
        String dateAndTime = date + " - " + time;
        holder.binding.toolCardDateAndTime.setText(dateAndTime);

        holder.binding.btnDeleteTool.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle(R.string.deleteConfirm)
                    .setMessage(R.string.deleteMessage)
                    .setPositiveButton(R.string.delete, (dialog, which) -> {
                        // Silme işlemi onaylandı, silme işlemini yap
                        viewModelTools.deleteTool(tool.getToolId());
                    })
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        // Silme işlemi iptal edildi, dialog kapanacak
                        dialog.dismiss();
                    })
                    .show();
        });

        holder.binding.btnDetailTool.setOnClickListener(view -> {
            ToolsFragmentDirections.ActionToolsFragmentToToolDetailsFragment direction = ToolsFragmentDirections.actionToolsFragmentToToolDetailsFragment(tool);
            Navigation.findNavController(view).navigate(direction);
        });

        holder.binding.btnSelectTool.setOnClickListener(view -> {
            viewModelMain.selectTool(tool);
            Navigation.findNavController(view).navigate(R.id.fieldsFragment);
        });
    }

    @Override
    public int getItemCount() {
        return toolList.size();
    }
}
