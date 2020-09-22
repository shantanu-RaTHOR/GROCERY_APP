package com.example.groceryapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.groceryapp.Model.GroceryM;
import com.example.groceryapp.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class databasehandler extends SQLiteOpenHelper
{
    Context context;
    public databasehandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
       String create_table="CREATE TABLE "+Constants.TABLE_NAME+"("+Constants.kEY_ID+" INTEGER PRIMARY KEY, "+Constants.KEY_GROCERY_ITEM+" TEXT, "+
               Constants.KEY_QTY+" TEXT, "+Constants.KEY_DATE+" LONG);";
       db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addGrocery(GroceryM grocery)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constants.KEY_QTY,grocery.getQuantity());
        values.put(Constants.KEY_DATE,grocery.getDate());
        db.insert(Constants.TABLE_NAME,null,values);

       // Log.d("savded to d","saved");

    }

    public GroceryM getGrocery(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String c[]={Constants.kEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY,Constants.KEY_DATE};
        Cursor cursor=db.query(Constants.TABLE_NAME,c,Constants.kEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null);
         GroceryM groceryM=new GroceryM();
         if(cursor!=null)
         {
             cursor.moveToFirst();
             groceryM.setId((cursor.getString(cursor.getColumnIndex(Constants.kEY_ID))));
             groceryM.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
             groceryM.setDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE)));
             groceryM.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY)));

         }

         return  groceryM;

    }

    public List<GroceryM> getAllGrocery()
    {
       SQLiteDatabase db=this.getWritableDatabase();
       List<GroceryM> list=new ArrayList<GroceryM>();
        String c[]={Constants.kEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY,Constants.KEY_DATE};
       Cursor cursor=db.query(Constants.TABLE_NAME,c,null,null,null,null,null);
       if(cursor.moveToFirst()) {
           do {
               GroceryM groceryM = new GroceryM();
               groceryM.setId((cursor.getString(cursor.getColumnIndex(Constants.kEY_ID))));
               groceryM.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
               groceryM.setDate(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE)));
               groceryM.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY)));
               list.add(groceryM);
           }
           while (cursor.moveToNext());
       }
       return list;
    }

    public int updateGrocery(GroceryM grocery)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constants.KEY_QTY,grocery.getQuantity());
        values.put(Constants.KEY_DATE,grocery.getDate());
        return db.update(Constants.TABLE_NAME,values,Constants.kEY_ID+"=?",new String[] {String.valueOf(grocery.getId())});

    }
    public  int deleteGrocery(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.delete(Constants.TABLE_NAME,Constants.kEY_ID+"=?",new String[] {String.valueOf(id)});

    }

    public int getGrocerycount()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query(Constants.TABLE_NAME,new String[] {Constants.kEY_ID},null,null,null,null,null);

      return  cursor.getCount();
    }
}
