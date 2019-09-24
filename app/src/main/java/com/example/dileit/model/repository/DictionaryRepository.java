package com.example.dileit.model.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dileit.model.Dictionary;
import com.example.dileit.model.database.DatabaseAccess;
import com.example.dileit.viewModel.DictionaryInterface;

import java.util.ArrayList;
import java.util.List;

public class DictionaryRepository {

    private static final String TAG = DictionaryRepository.class.getSimpleName()+"debugging";
    private DatabaseAccess databaseAccess;
    private DictionaryInterface mInterface;

    public DictionaryRepository(Application application,DictionaryInterface anInterface){
        databaseAccess = DatabaseAccess.getINSTANCE(application);
        mInterface = anInterface;
    }

    public void getAllEngWords(){
        new AsyncGetAllEngWords(databaseAccess,mInterface).execute();
    }

    private class AsyncGetAllEngWords extends AsyncTask<Void,Void,List<Dictionary>>{
        DatabaseAccess databaseAccess;
        DictionaryInterface mInterface;
        public AsyncGetAllEngWords(DatabaseAccess databaseAccess,DictionaryInterface anInterface) {
            this.databaseAccess = databaseAccess;
            mInterface = anInterface;
        }

        @Override
        protected List<Dictionary> doInBackground(Void... voids) {
            List<Dictionary> wordsList = new ArrayList<>();
            Cursor cursor = databaseAccess.getDatabase().rawQuery("SELECT word,def FROM dictionary",new String[]{});
            while (cursor.moveToNext()){
                String word = cursor.getString(0);
                String def = cursor.getString(1);
                Dictionary dictionary = new Dictionary(word,def);
                wordsList.add(dictionary);
            }
            Log.d(TAG, String.valueOf(wordsList.size()));
            cursor.close();
            return wordsList;
        }

        @Override
        protected void onPostExecute(List<Dictionary> dictionary) {
            super.onPostExecute(dictionary);
            mInterface.allEngWords(dictionary);
            databaseAccess.closeDatabase();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseAccess.openDatabase();
        }
    }
}
