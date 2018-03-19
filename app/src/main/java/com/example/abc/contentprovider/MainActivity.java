package com.example.abc.contentprovider;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button;
    LinearLayout layout_recycler_view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    ContactListAdapter mAdapter;

    ArrayList<Android_Contact> arrayList_Android_Contacts = new ArrayList<Android_Contact>();

    public class Android_Contact {

        public String android_contact_Name = "";
        public String android_contact_TelefonNr = "";
        public int android_contact_ID = 0;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.list_recycler_view);

        fp_get_Android_Contacts();

    }

    public void clickme(View view){
        displayContacts();
    }

    public void fp_get_Android_Contacts() {

        Cursor cursor_Android_Contacts = null;
        ContentResolver contentResolver = getContentResolver();
        try {
            cursor_Android_Contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        } catch (Exception ex) {
            Log.e("Error on contact", ex.getMessage());
        }

        if (cursor_Android_Contacts.getCount() > 0) {

            Toast.makeText(this, ""+cursor_Android_Contacts.moveToNext(), Toast.LENGTH_SHORT).show();
            while (cursor_Android_Contacts.moveToNext()) {

                Android_Contact android_contact = new Android_Contact();
                String contact_id = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts._ID));
                String contact_display_name = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                android_contact.android_contact_Name = contact_display_name;


                int hasPhoneNumber = Integer.parseInt(cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            , null
                            , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                            , new String[]{contact_id}
                            , null);

                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        android_contact.android_contact_TelefonNr = phoneNumber;

                    }
                    phoneCursor.close();
                }

                arrayList_Android_Contacts.add(android_contact);
            }
            //System.out.println("pcccccc "+arrayList_Android_Contacts.size());
        }

    }

    private void displayContacts(){
    /*    for(int i=0;i<arrayList_Android_Contacts.size();i++){
            System.out.println("ppName"+arrayList_Android_Contacts.get(i).android_contact_Name);
            System.out.println("ppNumber"+arrayList_Android_Contacts.get(i).android_contact_TelefonNr);

        }*/
      /*  Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "pcpatidar.4488@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Contacts List");
        StringBuilder sb = new StringBuilder();
        for (Android_Contact s : arrayList_Android_Contacts) {
            sb.append(s.android_contact_TelefonNr);
            sb.append("\n");
        }
        intent.putExtra(Intent.EXTRA_TEXT, sb.toString());

        startActivity(Intent.createChooser(intent, "pcpatidar.4488@gmail.com"));*/

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ContactListAdapter(getApplicationContext(),arrayList_Android_Contacts);
        //Notify Adapter
        // mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

    }
}

