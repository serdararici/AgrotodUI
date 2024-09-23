package com.serdararici.dronemarket.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.serdararici.dronemarket.data.entitiy.Line;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface LinesDao {
    @Query("SELECT * FROM lines WHERE lineType = 'straight'")
    Single<List<Line>> getStraightLines();

    @Query("SELECT * FROM lines WHERE lineType = 'curved'")
    Single<List<Line>> getCurvedLines();

    @Insert
    Completable addNewLine(Line line);

    @Delete
    Completable deleteLine(Line line);
}
