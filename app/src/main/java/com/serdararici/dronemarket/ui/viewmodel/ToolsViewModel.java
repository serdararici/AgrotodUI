package com.serdararici.dronemarket.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.data.repo.ToolDaoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ToolsViewModel extends ViewModel {
    public ToolDaoRepository trepo;
    public MutableLiveData<List<Tool>> toolList;

    @Inject
    public ToolsViewModel(ToolDaoRepository trepo){
        this.trepo = trepo;
        getTools();
        toolList = trepo.toolList;
    }


    public void getTools(){
        trepo.getTools();
    }

    public void deleteTool(int toolId){
        trepo.deleteTool(toolId);
    }
}
