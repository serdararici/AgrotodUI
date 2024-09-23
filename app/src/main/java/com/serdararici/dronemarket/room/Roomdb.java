package com.serdararici.dronemarket.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.entitiy.Line;
import com.serdararici.dronemarket.data.entitiy.Tool;

@Database(entities = {Field.class, Tool.class, Line.class}, version = 1)
public abstract class Roomdb extends RoomDatabase {
    public abstract FieldsDao getFieldsDao();
    public abstract ToolsDao getToolsDao();
    public abstract LinesDao getLinesDao();
}
