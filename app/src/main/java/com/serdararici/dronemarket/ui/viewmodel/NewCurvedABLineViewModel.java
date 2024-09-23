package com.serdararici.dronemarket.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.repo.LineDaoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewCurvedABLineViewModel extends ViewModel {
    public LineDaoRepository lrepo;
    public MutableLiveData<Double> lineDegree = new MutableLiveData<>();

    @Inject
    public NewCurvedABLineViewModel(LineDaoRepository lrepo) {
        this.lrepo = lrepo;
        calculateBearing();
        lineDegree = lrepo.lineDegree;
    }

    public void addNewABLine(String lineName, String lineType){
        lrepo.addNewABLine(lineName,lineType);
    }

    public void calculateBearing() {
        lrepo.calculateBearing();
    }
}
