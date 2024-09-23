package com.serdararici.dronemarket.data.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.entitiy.Line;
import com.serdararici.dronemarket.room.LinesDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LineDaoRepository {
    public MutableLiveData<List<Line>> straightABLineList = new MutableLiveData<>();
    public MutableLiveData<List<Line>> curvedABLineList = new MutableLiveData<>();
    public MutableLiveData<Double> lineDegree = new MutableLiveData<>();
    public MutableLiveData<Line> selectedLine = new MutableLiveData<>();
    public MutableLiveData<Line> selectedStraightLine = new MutableLiveData<>();
    public MutableLiveData<Line> selectedCurvedLine = new MutableLiveData<>();

    private LinesDao ldao;

    public LineDaoRepository(LinesDao ldao){
        this.ldao = ldao;
    }

    public void selectLine(Line line) {
        selectedLine.setValue(line);
    }

    public void selectStraightLine(Line line) {
        selectedStraightLine.setValue(line);
    }

    public void selectCurvedLine(Line line) {
        selectedCurvedLine.setValue(line);
    }

    public void addNewABLine (String lineName, String lineType) {
        Line newLine = new Line(0,lineName,lineType);
        ldao.addNewLine(newLine).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Database Error - addNewLine", e.toString());
                    }
                });

        Log.e("newLine", newLine.getLineName() + " " + newLine.getLineType());
    }

    public void getStraightABLine () {
        ldao.getStraightLines().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Line>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onSuccess(List<Line> lines) {
                        straightABLineList.setValue(lines);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Database Error - getStraightABLine", e.toString());
                    }
                });
    }

    public void getCurvedABLine () {
        ldao.getCurvedLines().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Line>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onSuccess(List<Line> lines) {
                        curvedABLineList.setValue(lines);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Database Error - getCurvedABLine", e.toString());
                    }
                });
    }

    public void deleteStraightABLine (int lineId) {
        Line deleteLine = new Line(lineId,"","");
        ldao.deleteLine(deleteLine).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {
                        getStraightABLine();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Database Error - deleteStraightABLine", e.toString());
                    }
                });
        Log.e("deleteStraightLine", String.valueOf(lineId));
    }

    public void deleteCurvedABLine (int lineId) {
        Line deleteLine = new Line(lineId,"","");
        ldao.deleteLine(deleteLine).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {
                        getCurvedABLine();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Database Error - deleteCurvedABLine", e.toString());
                    }
                });

        Log.e("deleteCurvedLine", String.valueOf(lineId));
    }









    /////////////////////////////////////////////////////////////////
    //Random konum verileri üretmek için

    private static final Random RANDOM = new Random();

    // Türkiye sınırları için enlem aralıkları
    //private static final double MIN_LATITUDE = 36.0;
    //private static final double MAX_LATITUDE = 42.0;

    private static final double MIN_LATITUDE = 37.0;
    private static final double MAX_LATITUDE = 40.0;

    // Türkiye sınırları için boylam aralıkları
    //private static final double MIN_LONGITUDE = 26.0;
    //private static final double MAX_LONGITUDE = 45.0;

    private static final double MIN_LONGITUDE = 28.0;
    private static final double MAX_LONGITUDE = 45.0;

    public static double[] getRandomCoordinates() {
        double latitude = MIN_LATITUDE + (MAX_LATITUDE - MIN_LATITUDE) * RANDOM.nextDouble();
        double longitude = MIN_LONGITUDE + (MAX_LONGITUDE - MIN_LONGITUDE) * RANDOM.nextDouble();
        return new double[]{latitude, longitude};
    }
    /////////////////////////////////////////////////////////////////////




    // A ve B koordinatlarına göre hat yönü hesapla
    public void calculateBearing() {

        //Random A ve B koordinatları oluşturuldu.
        double[] coordinatesA = getRandomCoordinates();
        double latitudeA = coordinatesA[0];
        double longitudeA = coordinatesA[1];
        double[] coordinatesB = getRandomCoordinates();
        double latitudeB = coordinatesB[0];
        double longitudeB = coordinatesB[1];

        // Dereceleri radyana çevir
        latitudeA = Math.toRadians(latitudeA);
        longitudeA = Math.toRadians(longitudeA);
        latitudeB = Math.toRadians(latitudeB);
        longitudeB = Math.toRadians(longitudeB);

        double deltaLon = longitudeB - longitudeA;

        double x = Math.sin(deltaLon) * Math.cos(latitudeB);
        double y = Math.cos(latitudeA) * Math.sin(latitudeB) - Math.sin(latitudeA) * Math.cos(latitudeB) * Math.cos(deltaLon);

        double initialBearing = Math.atan2(x, y);

        // Radyanı dereceye çevir
        initialBearing = Math.toDegrees(initialBearing);

        // Açının pozitif olması için 360 derece ekleyip 360'a göre mod alıyoruz
        double bearing = (initialBearing + 360) % 360;

        lineDegree.setValue(bearing);
    }



}
