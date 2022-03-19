package com.cst2335.shar0625;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;
public class ChatRoomActivity extends AppCompatActivity {
    private EditText editMessage;
    ChatAdapter chatadapter;
    DatabaseMessages db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        List<Message> messagelist = new ArrayList<>();

        Button buttonSend = findViewById(R.id.button_send);
        Button buttonReceive = findViewById(R.id.button_receive);
        editMessage = findViewById(R.id.message_entered);
        ListView listview = findViewById(R.id.listview);

        chatadapter = new ChatAdapter(ChatRoomActivity.this, messagelist);
        listview.setAdapter(chatadapter);

        db = new DatabaseMessages(this);
        db.getWritableDatabase();
        db.viewData();

        buttonSend.setOnClickListener(v -> {

            String text = editMessage.getText().toString();
//            Message message = new Message();
//            message.messages = text;
//            message.isSend=true;
//            messagelist.add(message);
//            chatadapter.notifyDataSetChanged();
            ContentValues contentValues = new ContentValues();
            db.insertData(text,true);
            editMessage.setText("");
            db.viewData();
        });

        buttonReceive.setOnClickListener(v -> {

            String text = editMessage.getText().toString();
//            Message message = new Message();
//            message.messages = text;
//            message.isSend = false;
//            messagelist.add(message);
//            chatadapter.notifyDataSetChanged();
            db.insertData(text,false);
            editMessage.setText("");
            db.viewData();
        });
        Log.d("ChatRoomActivity","onCreate");


        listview.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
            builder.setTitle("Would you like to delete this")
                    .setMessage("The selected row is: " + position)
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        messagelist.remove(position); //remove the message
                        chatadapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (dialog, which) -> {

                    });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        });


    }
    public void printCursor(Cursor c){

    }
}