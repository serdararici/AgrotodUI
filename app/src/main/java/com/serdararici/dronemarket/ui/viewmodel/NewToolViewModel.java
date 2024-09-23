package com.serdararici.dronemarket.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.repo.ToolDaoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewToolViewModel extends ViewModel {
    public ToolDaoRepository trepo;

    @Inject
    public NewToolViewModel(ToolDaoRepository trepo) {
        this.trepo = trepo;
    }

    public void addNewTool(String newToolName, String newToolType, double newToolWidth){
        trepo.addNewTool(newToolName,newToolType,newToolWidth);
    }
}
