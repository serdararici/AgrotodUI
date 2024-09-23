package com.serdararici.dronemarket.data.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.entitiy.Tool;
import com.serdararici.dronemarket.room.FieldsDao;
import com.serdararici.dronemarket.room.ToolsDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToolDaoRepository {
    public MutableLiveData<List<Tool>> toolList = new MutableLiveData<>();
    public MutableLiveData<Tool> selectedTool = new MutableLiveData<>();
    private ToolsDao tdao;

    public ToolDaoRepository(ToolsDao tdao){
        this.tdao = tdao;
    }

    public void selectTool(Tool tool) {
        selectedTool.setValue(tool);
    }

    public void getTools(){

        tdao.getTools().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Tool>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onSuccess(List<Tool> tools) {
                        //veritabanından gelen veriler liste halinde gönderiliyor.
                        toolList.setValue(tools);
                    }
                    @Override
                    public void onError(Throwable e) {}
                });



        /*ArrayList<Tool> toolArrayList = new ArrayList<>();
        Tool t1 = new Tool(1,"ALET 1",400,"27.08.2024","16.40","EKİM");
        Tool t2 = new Tool(2,"ALET 2",200,"27.08.2024","16.40","ARA İŞÇİLİK");
        Tool t3 = new Tool(3,"ALET 3",400,"27.08.2024","16.40","EKİM");

        toolArrayList.add(t1);
        toolArrayList.add(t2);
        toolArrayList.add(t3);

        toolList.setValue(toolArrayList);

         */
    }

    // daha sonra kayıt işlemi yapılacak
    public void addNewTool(String newToolName, String newToolType, double newToolWidth){
        String date = getDate();
        String time = getTime();

        Tool newTool = new Tool(0,newToolName,newToolWidth,newToolType,date,time);
        tdao.addNewTool(newTool).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {}
                });

        String toolWidth = newToolWidth + "CM";
        Log.e("**Yeni Alet Eklendi**", "\nAlet Ismi: " + newToolName + " Alet Turu: " + newToolType +
                " Alet Genislik: " + toolWidth +
                "\nTarih: " + date + " Saat: " + time);
    }

    public void  deleteTool(int toolId){
        Log.e("Tool Delete", String.valueOf(toolId));

        Tool deleteTool = new Tool(toolId,"",0,"","","");
        tdao.deleteTool(deleteTool).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {
                        getTools();
                    }
                    @Override
                    public void onError(Throwable e) {}
                });
    }

    public void updateTool(int toolId,String toolName, double toolWidth, String toolType, String date, String time) {
        Tool updateTool = new Tool(toolId,toolName,toolWidth,toolType,date,time);
        tdao.updateTool(updateTool).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {}
                });
        Log.e("Tool Update ", toolId + toolName + toolWidth +toolType);

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
}
