package com.example.dileit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.dileit.model.Word;
import com.example.dileit.model.database.DatabaseAccess;
import com.example.dileit.model.database.DatabaseOpenHelper;
import com.example.dileit.viewmodel.DictionaryViewModel;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseAccess = DatabaseAccess.getINSTANCE(getApplication());

        new MyAsync().execute();
    }

    class MyAsync extends AsyncTask<Void, Integer, Void> {

        public MyAsync() {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            databaseAccess.openDatabase();
            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary", null);
            int c = 0;
            while (cursor.moveToNext()) {
                String word = cursor.getString(0);
                char[] chars = word.toCharArray();
                int[] ints = new int[chars.length];
                for (int i = 0; i < chars.length; i++) {
                    ints[i] = chars[i];
                }
                List<Integer> integers = new ArrayList<>();
                for (int anInt : ints) {
                    integers.add(anInt);
                }
                for (int i = 0; i < integers.size(); i++) {
                    if (integers.get(i) == 8204) {
                        if (i == chars.length - 1) {
                            integers.remove(i);
                        } else {
                            if (i+1<integers.size()){
                                if (integers.get(i + 1) == 32) {
                                    integers.remove(i);
                                }
                            }

                        }
                    }
                }
                char[] finalChars = new char[integers.size()];
                int[] finalInts = new int[integers.size()];

                for (int i = 0; i < integers.size(); i++) {
                    finalInts[i] = integers.get(i);
                    finalChars[i] = (char) finalInts[i];
                }

                StringBuilder finalWord = new StringBuilder();

                for (char finalChar : finalChars) {
                    finalWord.append(finalChar);
                }
                Log.d("hi", "doInBackground:" + finalWord);
                ContentValues contentValues = new ContentValues();
                String h = finalWord.toString();
                contentValues.put("word" , h);
                databaseAccess.getDatabase().update(DatabaseOpenHelper.DICTIONARY_ENG_TO_PERSION_TABLE_NAME ,contentValues , "word= ?" ,new String[]{word});
            }
            cursor.close();
            databaseAccess.closeDatabase();
            return null;
        }
    }
}
