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
public class CurvedABLineViewModel extends ViewModel {
    public LineDaoRepository lrepo;
    public MutableLiveData<List<Line>> curvedABLineList = new MutableLiveData<>();
    public MutableLiveData<Line> selectedCurvedLine;

    @Inject
    public CurvedABLineViewModel(LineDaoRepository lrepo) {
        this.lrepo = lrepo;
        getCurvedABLine();
        curvedABLineList = lrepo.curvedABLineList;
        selectedCurvedLine = lrepo.selectedCurvedLine;
    }


    public void getCurvedABLine() {
        lrepo.getCurvedABLine();
    }

    public void deleteCurvedABLine (int lineId) {
        lrepo.deleteCurvedABLine(lineId);
    }

    public void selectCurvedLine(Line line) {
        lrepo.selectCurvedLine(line);
    }
}
