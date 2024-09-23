package com.serdararici.dronemarket.di;

import android.content.Context;

import androidx.room.Room;

import com.serdararici.dronemarket.data.repo.FieldDaoRepository;
import com.serdararici.dronemarket.data.repo.LineDaoRepository;
import com.serdararici.dronemarket.data.repo.ToolDaoRepository;
import com.serdararici.dronemarket.room.FieldsDao;
import com.serdararici.dronemarket.room.LinesDao;
import com.serdararici.dronemarket.room.Roomdb;
import com.serdararici.dronemarket.room.ToolsDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public FieldDaoRepository provideFieldDaoRepository(FieldsDao fdao){
        return new FieldDaoRepository(fdao);
    }

    @Provides
    @Singleton
    public FieldsDao provideFieldsDao(@ApplicationContext Context context){
        Roomdb db = Room.databaseBuilder(context, Roomdb.class, "dronMarket.sqlite")
                .createFromAsset("dronMarket.sqlite").build();
        return db.getFieldsDao();
    }


    @Provides
    @Singleton
    public ToolDaoRepository provideToolDaoRepository(ToolsDao tdao){
        return new ToolDaoRepository(tdao);
    }

    @Provides
    @Singleton
    public ToolsDao provideToolsDao(@ApplicationContext Context context){
        Roomdb db = Room.databaseBuilder(context, Roomdb.class, "dronMarket.sqlite")
                .createFromAsset("dronMarket.sqlite").build();
        return db.getToolsDao();
    }

    @Provides
    @Singleton
    public LineDaoRepository provideLineDaoRepository(LinesDao ldao) {
        return new LineDaoRepository(ldao);
    }

    @Provides
    @Singleton
    public LinesDao provideLinesDao(@ApplicationContext Context context) {
        Roomdb db = Room.databaseBuilder(context, Roomdb.class, "dronMarket.sqlite")
                .createFromAsset("dronMarket.sqlite").build();
        return db.getLinesDao();
    }


}
