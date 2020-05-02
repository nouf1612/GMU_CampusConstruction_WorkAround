package com.example.gmu_campusconstruction_workaround;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Helper class to open Routes database
 */
public class RoutesDatabaseOH extends SQLiteAssetHelper {
    private static final String Routes_Databases = "Routes.db";
    private static final int RoutesDB_version = 1;

    public RoutesDatabaseOH(Context context){
        super(context,Routes_Databases, null, RoutesDB_version);
    }
}
