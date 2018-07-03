package com.example.ipcservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ipcclient.Book;
import com.example.ipcclient.IBookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int index;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取的是一个代理对象 proxy
            iBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private IBookManager iBookManager;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        bindService();
    }

    private void initView() {
        Button btn = findViewById(R.id.btn);
        txt = findViewById(R.id.txt);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        txt.setText("");
        Book book = new Book(String.valueOf(index), "java 基础");
        index++;
        try {
            iBookManager.addBook(book);
            List<Book> bookList = iBookManager.getBookList();
            for (int i = 0;i < bookList.size();i++){
                txt.append(bookList.get(i).toString() + "---");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.ipcclient",
                "com.example.ipcclient.IBookservice"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }


}
