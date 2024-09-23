package com.serdararici.dronemarket.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.serdararici.dronemarket.data.entitiy.Field;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface FieldsDao {
    @Query("SELECT * FROM fields")
    Single<List<Field>> getFields();

    @Insert
    Completable addNewField(Field field);

    @Delete
    Completable deleteField(Field field);

    @Update
    Completable updateField(Field field);
}
