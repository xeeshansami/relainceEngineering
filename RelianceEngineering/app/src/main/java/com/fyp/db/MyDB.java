package com.fyp.db;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDB {

    protected static final String TAG = "DataAdapter";
    public final static String EMP_TABLE = "Table1AUS_2019_Table1AUS"; // name of table
    public final static String LineNo = "LineNo";
    public final static String BookPage = "BookPage";
    public final static String NominalComposition = "NominalComposition";
    public final static String ProductForm = "ProductForm";
    public final static String SpecNo = "SpecNo";
    public final static String TypeGrade = "TypeGrade";
    public final static String UNS = "UNS";
    public final static String ClassTemper = "ClassTemper";
    public final static String SizeThickness = "SizeThickness";
    public final static String PNo = "PNo";
    public final static String GroupNo = "GroupNo";
    public final static String MinTensileStrength = "MinTensileStrength";
    public final static String MinYieldStrength = "MinYieldStrength";
    public final static String ConstructionI = "ConstructionI";
    public final static String ConstructionIII = "ConstructionIII";
    public final static String ConstructionVIII = "ConstructionVIII";
    public final static String ConstructionXII = "ConstructionXII";
    public final static String InternalPressure = "InternalPressure";
    public final static String Notes = "Notes";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private MyDatabaseHelper mDbHelper;

    public MyDB(Context context) {
        this.mContext = context;
        mDbHelper = new MyDatabaseHelper(mContext);
    }

    public MyDB createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public MyDB open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Cursor getTestData() {
        try {

            String sql = "SELECT * FROM myTable";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getValues(String col, String uns) {
        Cursor mCursor = mDb.rawQuery("SELECT * FROM Table1AUS_2019_Table1AUS WHERE " + col + " = '" + uns + "' ORDER BY "+col+" ASC" , null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getAllValues(String values) {
        Cursor mCursor = mDb.rawQuery("SELECT * FROM Table1AUS_2019_Table1AUS WHERE LineNo = '" + values + "'", null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getValueCol(String lineNo) {
        Cursor mCursor = mDb.rawQuery("SELECT * FROM Table1AUS_2019_Table1AUS WHERE LineNo = '" + lineNo + "'", null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getValueCol2(String col, String lineNo) {
        Cursor mCursor = mDb.rawQuery("SELECT " + col + " FROM Table1AUS_2019_Table1AUS WHERE LineNo = '" + lineNo + "'", null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getSpecNo() {
        Cursor mCursor = mDb.rawQuery("SELECT DISTINCT SpecNo FROM Table1AUS_2019_Table1AUS Order by SpecNo", null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getLineNo() {
        Cursor mCursor = mDb.rawQuery("SELECT DISTINCT LineNo FROM Table1AUS_2019_Table1AUS ORDER BY LineNo", null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getProductName() {
        Cursor mCursor = mDb.rawQuery("SELECT DISTINCT ProductForm FROM Table1AUS_2019_Table1AUS ORDER BY ProductForm", null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getUNS() {
        Cursor mCursor = mDb.rawQuery("SELECT DISTINCT UNS FROM Table1AUS_2019_Table1AUS ORDER BY UNS", null);
        return mCursor; // iterate to get each value.
    }

    public Cursor getPNo() {
        Cursor mCursor = mDb.rawQuery("SELECT DISTINCT PNo FROM Table1AUS_2019_Table1AUS ORDER BY PNo", null);
        return mCursor; // iterate to get each value.
    }

//    public Cursor selectRecords() {
//
//        String[] cols = new String[]{
//                LineNo
//                , BookPage
//                , NominalComposition
//                , ProductForm
//                , SpecNo
//                , TypeGrade
//                , UNS
//                , ClassTemper
//                , SizeThickness
//                , PNo
//                , GroupNo
//                , MinTensileStrength
//                , MinYieldStrength
//                , ConstructionI
//                , ConstructionIII
//                , ConstructionVIII
//                , ConstructionXII
//                , InternalPressure
//                , Notes};
//        Cursor mCursor = mDb.query(false, EMP_TABLE, cols, null
//                , null, null, null, null, null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor; // iterate to get each value.
//    }
}