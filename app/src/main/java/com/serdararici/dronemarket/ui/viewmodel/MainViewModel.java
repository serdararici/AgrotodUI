package com.serdararici.dronemarket.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.entitiy.Line;
import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.data.repo.FieldDaoRepository;
import com.serdararici.dronemarket.data.repo.LineDaoRepository;
import com.serdararici.dronemarket.data.repo.ToolDaoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    public FieldDaoRepository frepo;
    public ToolDaoRepository trepo;
    public LineDaoRepository lrepo;
    public MutableLiveData<Field> selectedField;
    public MutableLiveData<Tool> selectedTool;
    public MutableLiveData<Line> selectedLine;
    public MutableLiveData<Boolean> isWorkSelect = new MutableLiveData<>();
    public MutableLiveData<Boolean> isWorkStart = new MutableLiveData<>();

    @Inject
    public MainViewModel(FieldDaoRepository frepo, ToolDaoRepository trepo, LineDaoRepository lrepo){
        this.frepo = frepo;
        this.trepo = trepo;
        this.lrepo = lrepo;
        selectedField = frepo.selectedField;
        selectedTool = trepo.selectedTool;
        selectedLine = lrepo.selectedLine;
    }

    public void selectField(Field field){
        frepo.selectField(field);
    }

    public void selectTool(Tool tool) {trepo.selectTool(tool);}

    public void selectLine(Line line) {
        lrepo.selectLine(line);
    }

}
