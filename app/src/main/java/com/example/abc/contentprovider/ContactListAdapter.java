package com.example.abc.contentprovider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MainActivity.Android_Contact> response;

    public ContactListAdapter(Context context, ArrayList<MainActivity.Android_Contact> response){
        this.context=context;
        this.response=response;
    }

    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_contact_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ViewHolder viewHolder, int position) {

        viewHolder.text1.setText(""+response.get(position).android_contact_Name);
        viewHolder.text2.setText(""+response.get(position).android_contact_TelefonNr);
    }

    @Override
    public int getItemCount() {
        return response.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.main_layout)
        LinearLayout main_layout;
        @Bind(R.id.text1)
        TextView text1;
        @Bind(R.id.text2)
        TextView text2;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}