package com.serdararici.dronemarket.data.entitiy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "fields")
public class Field implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "fieldId")
    @NotNull
    private int fieldId;

    @ColumnInfo(name = "fieldName")
    @NotNull
    private String fieldName;

    @ColumnInfo(name = "totalArea")
    @NotNull
    private double totalArea;

    @ColumnInfo(name = "processedArea")
    @NotNull
    private double processedArea;

    @ColumnInfo(name = "date")
    @NotNull
    private String date;

    @ColumnInfo(name = "time")
    @NotNull
    private String time;

    @ColumnInfo(name = "latitude")
    @NotNull
    private double latitude;

    @ColumnInfo(name = "longitude")
    @NotNull
    private double longitude;

    // Boş constructor
    public Field() {}

    // Tüm alanları içeren constructor
    public Field(@NotNull int fieldId, @NotNull String fieldName, @NotNull double totalArea,
                 @NotNull double processedArea, @NotNull String date, @NotNull String time,
                 @NotNull double latitude, @NotNull double longitude) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.totalArea = totalArea;
        this.processedArea = processedArea;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter ve Setter methodları
    @NotNull
    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(@NotNull int fieldId) {
        this.fieldId = fieldId;
    }

    public @NotNull String getFieldName() {
        return fieldName;
    }

    public void setFieldName(@NotNull String fieldName) {
        this.fieldName = fieldName;
    }

    @NotNull
    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(@NotNull double totalArea) {
        this.totalArea = totalArea;
    }

    @NotNull
    public double getProcessedArea() {
        return processedArea;
    }

    public void setProcessedArea(@NotNull double processedArea) {
        this.processedArea = processedArea;
    }

    public @NotNull String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }

    public @NotNull String getTime() {
        return time;
    }

    public void setTime(@NotNull String time) {
        this.time = time;
    }

    @NotNull
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NotNull double latitude) {
        this.latitude = latitude;
    }

    @NotNull
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NotNull double longitude) {
        this.longitude = longitude;
    }
}
