package com.serdararici.dronemarket.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serdararici.dronemarket.data.entitiy.Line;
import com.serdararici.dronemarket.data.repo.LineDaoRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class StraightABLineViewModel  extends ViewModel {
    public LineDaoRepository lrepo;
    public MutableLiveData<List<Line>> straightABLineList = new MutableLiveData<>();
    public MutableLiveData<Line> selectedStraightLine;

    @Inject
    public StraightABLineViewModel(LineDaoRepository lrepo) {
        this.lrepo = lrepo;
        getStraightABLine();
        straightABLineList = lrepo.straightABLineList;
        selectedStraightLine = lrepo.selectedStraightLine;
    }

    public void getStraightABLine() {
        lrepo.getStraightABLine();
    }

    public void deleteStraightABLine (int lineId) {
        lrepo.deleteStraightABLine(lineId);
    }

    public void selectStraightLine(Line line) {
        lrepo.selectStraightLine(line);
    }
}
