package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.UserInformation;

public class UserDBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Eloquent.db";
    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String COLUMN_FIRST_NAME = "FirstName";
    public static final String COLUMN_LAST_NAME = "LastName";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_BIRTH_DATE = "BirthDate";
    public static final String COLUMN_PASSWORD = "Password";


    public UserDBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTable = "CREATE TABLE " + USERS_TABLE + " (" + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_EMAIL + " TEXT primary key, " + COLUMN_BIRTH_DATE + " TEXT, " + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(creatTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+USERS_TABLE);
        onCreate(db);

    }

    public  boolean AddUser(UserInformation userInformation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME,userInformation.getFName());
        contentValues.put(COLUMN_LAST_NAME,userInformation.getLName());
        contentValues.put(COLUMN_EMAIL,userInformation.getEmail());
        contentValues.put(COLUMN_BIRTH_DATE,userInformation.getBirthDate());
        contentValues.put(COLUMN_PASSWORD,userInformation.getPassword());

        long insert = db.insert(USERS_TABLE, null, contentValues);
        // if insert fails
        if (insert == -1){return false;}
        // if insert success
        else{return true;}
    }

    //check email
    public Boolean checkEmail(String Email){
        SQLiteDatabase EloquentDB = this.getWritableDatabase();
        Cursor cursor = EloquentDB.rawQuery("Select * from USERS_TABLE where Email = ?", new String[]{Email});
        // if user exist
        if(cursor.getCount() > 0){
            return true;}
        else{
            return false;}
    }

    // check email and password for login
    public Boolean checkEmailAndPassword(String Email, String Password){
        SQLiteDatabase EloquentDB = this.getWritableDatabase();
        Cursor cursor = EloquentDB.rawQuery("Select * from USERS_TABLE where Email = ? and Password = ?",new String[] {Email, Password});

        if(cursor.getCount() > 0) {
            return true;
        }
        else{
            return false;
        }
    }

    public List<UserInformation> getAllInfo(){

        List<UserInformation> returList = new ArrayList<>();

        //get data from the database
        String Query = "SELECT * FROM "+USERS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            do {
                String userFname = cursor.getString(0);
                String userLname = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String userBdate = cursor.getString(3);
                String userPassword = cursor.getString(4);

                UserInformation NewuserInformation = new UserInformation(userFname,userLname,userEmail,userBdate,userPassword);

                returList.add(NewuserInformation);
            }while (cursor.moveToNext());


        }
        else{

        }

        cursor.close();
        db.close();

        return returList;
    }

}
