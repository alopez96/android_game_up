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

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";

    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference;
    private DatabaseReference mReference;
    private String a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
/*
        ListView list = findViewById(R.id.event_post);
        TextView text = findViewById(R.id.nothing_tv);
        text.setVisibility(View.INVISIBLE);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");
        final DatabaseReference event1 = mEventsReference.child("event2");
        mEventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a = String.valueOf(dataSnapshot.child("event1").child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(EventActivity.this, "onCreate a: " + a, Toast.LENGTH_SHORT).show();
        DataSnapshot dataSnapshot = null;
        int aSize = 5;
        String[] listItems = new String[aSize];
        for(int i = 0; i < aSize; i++){
            if(a == null)
                listItems[i] = mEventsReference.child("event" + i).child("name").getKey();
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


                //startActivity(detailIntent);
            }
        });*/
    }

    @Override
    public void onStart(){
        super.onStart();
        ListView list = findViewById(R.id.event_post);
        TextView text = findViewById(R.id.nothing_tv);
        text.setVisibility(View.INVISIBLE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference().child("events");
        mEventsReference = mFirebaseDatabase.getReference().child("events");
        final String[] b = new String[5];
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    b[0] = String.valueOf(snapshot.child("creator").getValue(String.class));
                    Log.i(TAG, String.valueOf(snapshot.child("creator").getValue(String.class)));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mEventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a = String.valueOf(dataSnapshot.child("event3").child("title").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(EventActivity.this, "onStart a: " + a, Toast.LENGTH_SHORT).show();
        Toast.makeText(EventActivity.this, "onStart b: " + b[0], Toast.LENGTH_SHORT).show();


        int aSize = 5;
        String[] listItems = new String[aSize];
        for(int i = 0; i < aSize; i++){
            if(a == null)
                listItems[i] = mEventsReference.child("event").child("title").getKey();
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


                //startActivity(detailIntent);
            }
        });

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



    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop(){
        super.onStop();
    }

}
