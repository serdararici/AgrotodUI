package com.serdararici.dronemarket.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.repo.FieldDaoRepository;
import com.serdararici.dronemarket.data.repo.ToolDaoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ToolUpdateViewModel extends ViewModel {

    public ToolDaoRepository trepo;

    @Inject
    public ToolUpdateViewModel(ToolDaoRepository trepo) {
        this.trepo = trepo;
    }

    public void updateTool(int toolId,String toolName, double toolWidth, String toolType, String date, String time) {
        trepo.updateTool(toolId, toolName, toolWidth, toolType, date, time);
    }
}
