package com.serdararici.dronemarket.data.entitiy;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.io.Serializable;


@Entity(tableName = "lines")
public class Line implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lineId")
    @NonNull
    private int lineId;
    @ColumnInfo(name = "lineName")
    @NonNull
    private String lineName;
    @ColumnInfo(name = "lineType")
    @NonNull
    private String lineType;

    public Line() {
    }

    public Line(int lineId, @NonNull String lineName, @NonNull String lineType) {
        this.lineId = lineId;
        this.lineName = lineName;
        this.lineType = lineType;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    @NonNull
    public String getLineName() {
        return lineName;
    }

    public void setLineName(@NonNull String lineName) {
        this.lineName = lineName;
    }

    @NonNull
    public String getLineType() {
        return lineType;
    }

    public void setLineType(@NonNull String lineType) {
        this.lineType = lineType;
    }
}
