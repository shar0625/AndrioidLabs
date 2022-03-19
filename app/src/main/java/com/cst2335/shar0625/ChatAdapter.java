package com.cst2335.shar0625;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Message> {


    private final Context context;
    private final List<Message> ListMessage;

    public ChatAdapter(Context context,List<Message> message) {
        super(context, -1, message);
        this.context = context;
        this.ListMessage = message;
    }
    @Override
    public int getCount() {
        return ListMessage.size();
    }

    @Override
    public Message getItem(int position) {
        return ListMessage.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        Message message =ListMessage.get(position);
        if(message.isSend==true){
            view = inflater.inflate(R.layout.activity_main_send, parent, false);
            TextView send_messages = (TextView) view.findViewById(R.id.textViewMessage1);
            send_messages.setText(ListMessage.get(position).messages);
        }

        if(message.isSend==false){
            view = inflater.inflate(R.layout.activity_main_receive, parent, false);
            TextView receive_messages = (TextView) view.findViewById(R.id.textViewMessage);
            receive_messages.setText(ListMessage.get(position).messages);
        }

        return view;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
