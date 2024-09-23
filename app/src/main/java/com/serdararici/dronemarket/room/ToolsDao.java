package com.serdararici.dronemarket.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.serdararici.dronemarket.data.entitiy.Field;
import com.serdararici.dronemarket.data.entitiy.Tool;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ToolsDao {
    @Query("SELECT * FROM tools")
    Single<List<Tool>> getTools();

    @Insert
    Completable addNewTool(Tool tool);

    @Delete
    Completable deleteTool(Tool tool);

    @Update
    Completable updateTool(Tool tool);


}
