package ca.alexbalt.gameup2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";

    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference;
    private DatabaseReference mPostReference;
    private String a, b, key;
    private ValueEventListener mPostListener;

    private TextView test;
    private ArrayList<String> mEvents = new ArrayList<>();
    private ListView eventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        //ListView list = findViewById(R.id.event_post);
        eventList = (ListView)findViewById(R.id.event_post);
        TextView text = findViewById(R.id.nothing_tv);
        text.setVisibility(View.INVISIBLE);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");
/*
        Intent intent = getIntent();
        key = intent.getStringExtra("data");
        if (key == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        Toast.makeText(EventActivity.this, "intent variable: " + key, Toast.LENGTH_SHORT).show();


        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mEvents);
        eventList.setAdapter(adapter);

        mEventsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mEvents.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

    }

    @Override
    public void onStart(){
        super.onStart();
        //ListView list = findViewById(R.id.event_post);
        eventList = findViewById(R.id.event_post);
        TextView text = findViewById(R.id.nothing_tv);
        text.setVisibility(View.INVISIBLE);
        /*
        mEventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a = String.valueOf(dataSnapshot.child(key).child("title").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Toast.makeText(EventActivity.this, "onStart a: " + a, Toast.LENGTH_SHORT).show();
        int aSize = 5;
        String[] listItems = new String[aSize];
        for(int i = 0; i < aSize; i++){
            if(a == null)
                listItems[i] = mEventsReference.child(key).child("title").getKey();
            else
                listItems[i] = a;
        }
        // Show the list view with the each list item an element from listItems
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mEvents);
        eventList.setAdapter(adapter);

        mEventsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mEvents.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    /*
        // Set an OnItemClickListener for each of the list items
        final Context context = this;
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ListData selected = aList.get(position);

                // Create an Intent to reference our new activity, then call startActivity
                // to transition into the new Activity.
                //Intent detailIntent = new Intent(context, SpecificEventActivity.class);

                // pass some key value pairs to the next Activity (via the Intent)
                //detailIntent.putExtra("first", selected.firstText);
                //detailIntent.putExtra("second", selected.secondText);

                //startActivity(detailIntent);
            }
        });*/


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_home){
            Intent homeIntent = new Intent(EventActivity.this, MainActivity.class);
            startActivity(homeIntent);

        }

        if(id == R.id.action_notification){
            Intent notificationIntent = new Intent(EventActivity.this, NotificationActivity.class);
            startActivity(notificationIntent);

        }

        if(id == R.id.action_events){
            Intent eventsIntent = new Intent(EventActivity.this, EventActivity.class);
            startActivity(eventsIntent);
        }

        if(id == R.id.action_messages){
            Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EventActivity.this, MessagesActivity.class);
            if(i == null){
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            }
            else if(i != null){
                startActivity(i);
            }

        }

        if(id == R.id.action_account){
            Intent accountIntent = new Intent(EventActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        }
        return super.onOptionsItemSelected(item);
    }

/*

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_events);

        ListView list = findViewById(R.id.event_post);
        TextView text = findViewById(R.id.nothing_tv);
        text.setVisibility(View.INVISIBLE);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");

        Intent intent = getIntent();
        final String key = intent.getStringExtra("data");
        if (key == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        Toast.makeText(EventActivity.this, "intent variable: " + key, Toast.LENGTH_SHORT).show();

        mEventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a = String.valueOf(dataSnapshot.child(key).child("title").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(EventActivity.this, "onCreate a: " + a, Toast.LENGTH_SHORT).show();
        int aSize = 5;
        String[] listItems = new String[aSize];
        for(int i = 0; i < aSize; i++){
            if(a == null)
                listItems[i] = mEventsReference.child(key).child("title").getKey();
            else
                listItems[i] = a;
        }
        // Show the list view with the each list item an element from listItems
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        // Set an OnItemClickListener for each of the list items
        final Context context = this;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ListData selected = aList.get(position);

                // Create an Intent to reference our new activity, then call startActivity
                // to transition into the new Activity.
                //Intent detailIntent = new Intent(context, SpecificEventActivity.class);

                // pass some key value pairs to the next Activity (via the Intent)
                //detailIntent.putExtra("first", selected.firstText);
                //detailIntent.putExtra("second", selected.secondText);

                //startActivity(detailIntent);
            }
        });
    }


    @Override
    protected void onStop(){
        super.onStop();
        // Remove post value event listener
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }
    }*/

}
