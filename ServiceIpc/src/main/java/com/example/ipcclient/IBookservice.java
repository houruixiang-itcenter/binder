package com.example.ipcclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houruixiang on 2018/1/10.
 */

public class IBookservice extends Service{

    private ArrayList<Book> books;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        books = new ArrayList<>();
        return binder;
    }

    //Stub
    IBinder binder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            if (books.size() == 0){
                return null;
            }
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            books.add(book);
        }
    };
}

