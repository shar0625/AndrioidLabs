package com.cst2335.shar0625;

import static com.cst2335.shar0625.MessageDatabase.TABLE_NAME;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;
public class ChatRoomActivity extends AppCompatActivity {
    private EditText editMessage;
    ChatAdapter chatadapter;
    SQLiteDatabase db;
    MessageDatabase dbOpener;
    List<Message> messagelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

      messagelist = new ArrayList<>();

        Button buttonSend = findViewById(R.id.button_send);
        Button buttonReceive = findViewById(R.id.button_receive);
        editMessage = findViewById(R.id.message_entered);
        ListView listview = findViewById(R.id.listview);

        chatadapter = new ChatAdapter(ChatRoomActivity.this, messagelist);
        listview.setAdapter(chatadapter);

        dbOpener = new MessageDatabase(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {MessageDatabase.COL_ID, MessageDatabase.COL_CHAT,
                MessageDatabase.COL_CHAT_TYPE};
        Cursor results = db.query(false, TABLE_NAME, columns,
                null, null, null, null, null, null);


        printCursor(results);


        int id_Column= results.getColumnIndex(MessageDatabase.COL_ID);
        int chat_Column = results.getColumnIndex(MessageDatabase.COL_CHAT);
        int chatColumnType = results.getColumnIndex(MessageDatabase.COL_CHAT_TYPE);


        results.moveToPosition(-1);
        while(results.moveToNext())
        {
            String message = results.getString(chat_Column);
            Boolean messageType = results.getInt(chatColumnType) == 1;
            long id = results.getLong(id_Column);


        }
        buttonSend.setOnClickListener(v -> {

            String text = editMessage.getText().toString();
            Message message = new Message();
            message.messages = text;
            message.isSent=true;
            messagelist.add(message);
            chatadapter.notifyDataSetChanged();
            ContentValues content = new ContentValues();
            content.put(MessageDatabase.COL_CHAT, text);
            content.put(MessageDatabase.COL_CHAT_TYPE, 1);
            db.insert(TABLE_NAME, null, content);

        });

        buttonReceive.setOnClickListener(v -> {

            String text = editMessage.getText().toString();
            Message message = new Message();
            message.messages = text;
            message.isSent = false;
            messagelist.add(message);
            chatadapter.notifyDataSetChanged();
            ContentValues content = new ContentValues();
            content.put(MessageDatabase.COL_CHAT, text);
            content.put(MessageDatabase.COL_CHAT_TYPE, 1);
            db.insert(TABLE_NAME, null, content);


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 345) {
            if (resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra("db_id", 0);
                deleteMessageId((int) id);
            }
        }
    }

    public void printCursor(Cursor cus) {

        Log.i("The Version of Db :", String.valueOf(MessageDatabase.VERSION_NUM));
        Log.i("Number  of Columns:", String.valueOf(cus.getColumnCount()));

        for (int i = 0; i < cus.getColumnCount(); i++) {
            Log.i("The Column is  " + i, cus.getColumnName(i));
        }



        Log.i("The Result count:", String.valueOf(cus.getCount()));

        int idColumnIndex = cus.getColumnIndex("_id");
        int chatColumnIndex = cus.getColumnIndex("CHAT");
        int chatTypeColumnIndex = cus.getColumnIndex("CHAT_TYPE");
        cus.moveToFirst();
        while (!cus.isAfterLast()) {
            Long id = cus.getLong(idColumnIndex);
            String chat = cus.getString(chatColumnIndex);
            String chatType = cus.getString(chatTypeColumnIndex);

            Log.i("ID: ", String.valueOf(id));
            Log.i("Message: ", chat);
            Log.i("isSent: ", chatType);

            cus.moveToNext();
        }
    }


    public void deleteMessageId(int id)
    {

        dbOpener.deleteEntry(id);
        messagelist.clear();
    }
}