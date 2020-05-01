package com.example.gmu_campusconstruction_workaround;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Get access to the Routes database
 */
public class RoutesDB_Access {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static RoutesDB_Access instance;
    public static final String TABLE_NAME = "route_table";
    private Cursor res = null;

    /**
     * private constructor so there wont be access from outside the class
     * @param context app context
     */
    private RoutesDB_Access(Context context){
        openHelper = new RoutesDatabaseOH(context);

    }

    /**
     * open the Routes database
     */
    public void open(){
        db = openHelper.getWritableDatabase();
    }

    /**
     * Get a single route from the database
     * @param buildings_name route id
     * @return route
     */
    public String getRoute(String buildings_name){
        res = db.rawQuery("select ROUTE from " + TABLE_NAME + " where BUILDINGS = '"+ buildings_name +"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            String Route = res.getString(0);
            buffer.append(""+Route);
        }
        return buffer.toString();

    }

    /**
     * Get all data from the database
     * @return
     */
    public Cursor getAllData() {
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    /**
     * return the instance of the routes database
     * @param context current RouteDB_Access
     * @return
     */
    public static RoutesDB_Access getInstance(Context context){
        if(instance == null){
            instance = new RoutesDB_Access(context);
        }
        return instance;

    }
}
