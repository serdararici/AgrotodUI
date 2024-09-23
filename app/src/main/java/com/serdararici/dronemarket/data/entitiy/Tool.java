package com.serdararici.dronemarket.data.entitiy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "tools")
public class Tool implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "toolId")
    @NotNull
    private int toolId;
    @ColumnInfo(name = "toolName")
    @NotNull
    private String toolName;
    @ColumnInfo(name = "toolWidth")
    @NotNull
    private double toolWidth;
    @ColumnInfo(name = "toolType")
    @NotNull
    private String toolType;
    @ColumnInfo(name = "date")
    @NotNull
    private String date;
    @ColumnInfo(name = "time")
    @NotNull
    private String time;


    public Tool() {

    }

    public Tool(@NotNull int toolId, @NotNull String toolName, @NotNull double toolWidth, @NotNull String toolType, @NotNull String date, @NotNull String time) {
        this.toolId = toolId;
        this.toolName = toolName;
        this.toolWidth = toolWidth;
        this.toolType = toolType;
        this.date = date;
        this.time = time;
    }

    @NotNull
    public int getToolId() {
        return toolId;
    }

    public void setToolId(@NotNull int toolId) {
        this.toolId = toolId;
    }

    public @NotNull String getToolName() {
        return toolName;
    }

    public void setToolName(@NotNull String toolName) {
        this.toolName = toolName;
    }

    @NotNull
    public double getToolWidth() {
        return toolWidth;
    }

    public void setToolWidth(@NotNull double toolWidth) {
        this.toolWidth = toolWidth;
    }

    public @NotNull String getToolType() {
        return toolType;
    }

    public void setToolType(@NotNull String toolType) {
        this.toolType = toolType;
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
}
