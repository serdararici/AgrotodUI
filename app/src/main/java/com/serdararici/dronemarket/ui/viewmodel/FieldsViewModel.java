package com.serdararici.dronemarket.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.repo.FieldDaoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FieldsViewModel extends ViewModel {
    public FieldDaoRepository frepo;
    public MutableLiveData<List<Field>> fieldList;

    @Inject
    public FieldsViewModel(FieldDaoRepository frepo){
        this.frepo = frepo;
        getFields();
        fieldList = frepo.fieldList;
    }

    public void getFields(){
        frepo.getFields();
    }

    public void deleteField(int fieldId){
        frepo.deleteField(fieldId);
    }
}
