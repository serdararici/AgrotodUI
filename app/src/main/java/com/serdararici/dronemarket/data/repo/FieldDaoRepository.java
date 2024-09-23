package com.serdararici.dronemarket.data.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;

import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.room.FieldsDao;
import com.serdararici.dronemarket.room.Roomdb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FieldDaoRepository {
    public MutableLiveData<List<Field>> fieldList = new MutableLiveData<>();
    public MutableLiveData<Field> selectedField = new MutableLiveData<>();
    private FieldsDao fdao;

    public FieldDaoRepository(FieldsDao fdao){
        this.fdao = fdao;
    }

    public void selectField(Field field) {
        selectedField.setValue(field);
    }

    // daha sonra kayıt işlemi yapılacak
    public void addNewField(String newFieldName, double newFieldArea){
        String date = getDate();
        String time = getTime();
        double[] coordinates = getRandomCoordinates();
        double latitude = coordinates[0];
        double longitude = coordinates[1];
        Field newField = new Field(0,newFieldName,newFieldArea,0,date,time,latitude,longitude);
        fdao.addNewField(newField).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Database Error", e.toString());
                    }
                });

        Log.e("**Yeni Alan Eklendi**", "\nTarla Ismi: " + newFieldName + " Tarla Alani: " + newFieldArea + " Ha" +
                "\nTarih: " + date + " Saat: " + time + " Konum: " + latitude + " - " + longitude);
    }

    public void getFields(){

        fdao.getFields().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Field>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onSuccess(List<Field> fields) {
                        //veritabanından gelen veriler liste halinde gönderiliyor.
                        fieldList.setValue(fields);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("Database Error - getFields", e.toString());
                    }
                });


        /*ArrayList<Field> fieldArrayList = new ArrayList<>();
        Field f1 = new Field(1,"ALAN 1","27.08.2024","16.40",20.4, 12.5);
        Field f2 = new Field(2,"ALAN 2","27.08.2024","16.40",20.4, 12.5);
        Field f3 = new Field(3,"ALAN 3","27.08.2024","16.40",20.4, 12.5);
        Field f4 = new Field(4,"ALAN 4","27.08.2024","16.40",20.4, 12.5);
        Field f5 = new Field(5,"ALAN 5","27.08.2024","16.40",20.4, 12.5);

        fieldArrayList.add(f1);
        fieldArrayList.add(f2);
        fieldArrayList.add(f3);
        fieldArrayList.add(f4);
        fieldArrayList.add(f5);

        fieldList.setValue(fieldArrayList);

         */
    }

    public void deleteField(int fieldId){
        Log.e("Field Delete", String.valueOf(fieldId));

        Field deleteField = new Field(fieldId,"",0,0,"","",0,0);
        fdao.deleteField(deleteField).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {
                        getFields();
                    }
                    @Override
                    public void onError(Throwable e) {}
                });
    }

    public void updateField(int fieldId, String fieldName, double fieldArea, double processedArea, String date, String time, double latitude, double longitude) {
        Field updateField = new Field(fieldId,fieldName,fieldArea,processedArea,date,time,latitude,longitude);
        fdao.updateField(updateField).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {}
                });

        Log.e("Field Update ", fieldId + fieldName + fieldArea);
    }


    //tarihi almak için
    public String getDate(){
        // Şu anki tarih ve saati almak için
        Calendar calendar = Calendar.getInstance();

        // Tarih formatı: 27.08.2024
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());
        //Log.e("Tarih", formattedDate);
        return formattedDate;
    }
    //saati almak için
    public String getTime(){
        // Şu anki tarih ve saati almak için
        Calendar calendar = Calendar.getInstance();

        // Saat formatı: 16.40
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
        String formattedTime = timeFormat.format(calendar.getTime());
        //Log.e("Saat", formattedTime);
        return formattedTime;
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
}
