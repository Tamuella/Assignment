package com.example.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DBNAME =" Login2.db";
    public static final String DBNAME2 =" Products.db";
    private static final String TABLE_USER = "users";
    private static final String TABLE_PRODUCT = "products";
    private static final String TABLE_CART = "cart";

    public DatabaseHandler(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users (username TEXT primary key , password TEXT, email TEXT)");
        MyDB.execSQL("create table products (ProductID TEXT primary key, ProductName TEXT, ProductQuantity TEXT, ProductPrice TEXT, ImageDrawable INTERGER)");
        MyDB.execSQL("create table cart (ProductID TEXT primary key, ProductName TEXT, ProductQuantity TEXT, ProductPrice TEXT, ImageDrawable INTERGER)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop TABLE if exists users");
        MyDB.execSQL("drop TABLE if exists products");
        MyDB.execSQL("drop TABLE if exists cart");
    }

    // we have created a new method for reading all the products.
    public ArrayList<Product> readProducts() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);

        // on below line we are creating a new array list.
        ArrayList<Product> productModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorProducts.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                productModalArrayList.add(new Product(cursorProducts.getString(0),
                        cursorProducts.getString(1),
                        cursorProducts.getString(2),
                        cursorProducts.getString(3),
                        cursorProducts.getInt(4)
                        ));
            } while (cursorProducts.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorProducts.close();
        return productModalArrayList;
    }

    // we have created a new method for reading all the cart.
    public ArrayList<Product> readCart() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCart = db.rawQuery("SELECT * FROM " + TABLE_CART, null);

        // on below line we are creating a new array list.
        ArrayList<Product> productModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCart.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                productModalArrayList.add(new Product(cursorCart.getString(0),
                        cursorCart.getString(1),
                        cursorCart.getString(2),
                        cursorCart.getString(3),
                        cursorCart.getInt(4)
                        ));
            } while (cursorCart.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCart.close();
        return productModalArrayList;
    }

    public boolean insertUserData(String username, String password, String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        long result = MyDB.insert("users", null, contentValues );
                if(result==1) return false;
                else return true;
    }

    public boolean checkUsername (String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * from users where username = ? ", new String[]{username});
                if (cursor.getCount() > 0 )
                    return true;
                else
                    return false;
    }

    public boolean checkUsernamePassword (String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * from users where username = ? and  password =?", new String[]{username,password});
        if (cursor.getCount() > 0 )
            return true;
        else
            return false;
    }

    public boolean checkUsernameEmail (String username, String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * from users where username = ? and  email =?", new String[]{username,email});
        if (cursor.getCount() > 0 )
            return true;
        else
            return false;
    }

    public void updateUserDB(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", password);
        MyDB.update(TABLE_USER, values, "username=?", new String[]{username});
        MyDB.close();
    }

    public boolean insertProductData(String productID, String productName, String productQuantity, String productPrice, int ImageDrawable){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("productID", productID);
        contentValues.put("productName", productName);
        contentValues.put("productQuantity", productQuantity);
        contentValues.put("productPrice", productPrice);
        contentValues.put("ImageDrawable", ImageDrawable);
        long result = MyDB.insert("products", null, contentValues );
        if(result==1) return false;
        else return true;
    }

    public boolean insertCartData(String productID, String productName, String productQuantity, String productPrice, int ImageDrawable){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("productID", productID);
        contentValues.put("productName", productName);
        contentValues.put("productQuantity", productQuantity);
        contentValues.put("productPrice", productPrice);
        contentValues.put("ImageDrawable", ImageDrawable);
        long result = MyDB.insert("cart", null, contentValues );
        if(result==1) return false;
        else return true;
    }

    public void updateProductDB(String productID, String productQuantity) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productID", productQuantity);
        MyDB.update(TABLE_PRODUCT, values, "productID=?", new String[]{productID});
        MyDB.close();
    }
}
