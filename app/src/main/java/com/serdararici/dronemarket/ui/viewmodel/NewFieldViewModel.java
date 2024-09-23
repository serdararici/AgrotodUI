package com.serdararici.dronemarket.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.repo.FieldDaoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewFieldViewModel extends ViewModel {
    public FieldDaoRepository frepo;

    @Inject
    public NewFieldViewModel(FieldDaoRepository frepo) {
        this.frepo = frepo;
    }

    public void addNewField(String newFieldName, double newFieldArea){
        frepo.addNewField(newFieldName, newFieldArea);
    }
}
