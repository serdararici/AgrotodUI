package com.serdararici.dronemarket.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.repo.FieldDaoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FieldUpdateViewModel extends ViewModel {
    public FieldDaoRepository frepo;

    @Inject
    public FieldUpdateViewModel(FieldDaoRepository frepo) {
        this.frepo = frepo;
    }

    public void updateField(int fieldId, String fieldName, double fieldArea, double processedArea, String date, String time, double latitude, double longitude) {
        frepo.updateField(fieldId, fieldName, fieldArea, processedArea, date, time,latitude,longitude);
    }
}
