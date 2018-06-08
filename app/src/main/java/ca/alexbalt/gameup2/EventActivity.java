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
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";

    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference, specificEventRef;
    private DatabaseReference mUsersReference, specificUserRef;
    private DatabaseReference mPostReference;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername, key, userkey;
    TextView titleTextView, consoleTextView, gameTextView;
    TextView dateTextView, creatorTextView, descTextView;
    ListView joinedListView;
    List<String> listString;
    private Button joinButton;
    private String userEmail, bio, favGames, uid;
    private ArrayList<String> friends = friends = new ArrayList<>();;
    private ArrayList<String> eventsJoined = new ArrayList<>();;
    private event Event;
    private User thisUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        titleTextView = findViewById(R.id.titleTV);
        consoleTextView = findViewById(R.id.consoleTV);
        gameTextView = findViewById(R.id.gameTV);
        dateTextView = findViewById(R.id.dateTV);
        creatorTextView = findViewById(R.id.creatorTV);
        descTextView = findViewById(R.id.descTV);
        joinedListView = findViewById(R.id.joinedLV);
        listString = new ArrayList<>();
        joinButton = findViewById(R.id.joinB);

        //ListView list = findViewById(R.id.event_post);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        userEmail = user.getEmail();
        bio = "";
        favGames = "";
        uid = user.getUid();

        Intent intent = getIntent();
        key = intent.getStringExtra("data");
        if (key == null) {
            throw new IllegalArgumentException("Must pass EVENT KEY VALUE");
        }
        specificEventRef = mEventsReference.child(key);

        mUsersReference = mFirebaseDatabase.getReference().child("users");
        specificUserRef = mUsersReference.child(uid);

    }

    @Override
    public void onStart(){
        super.onStart();
        specificEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Event = dataSnapshot.getValue(event.class);
                    titleTextView.setText(Event.title);
                    consoleTextView.setText(Event.console);
                    gameTextView.setText(Event.game);
                    dateTextView.setText(Event.date);
                    creatorTextView.setText(Event.creator);
                    descTextView.setText(Event.body);
                    listString = Event.joinedList;

                 ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventActivity.this, android.R.layout.simple_list_item_1, listString);
                joinedListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        specificUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisUser = dataSnapshot.getValue(User.class);
                eventsJoined = thisUser.eventsJoined;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listString.add(mUsername);
                //if user has been added to event, do not add again
                for(int i = 0; i < listString.size(); i++){
                    if(listString.get(i) == mUsername) {
                        listString.remove(i);
                        Toast.makeText(EventActivity.this, "you are already a part of the event " + titleTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }


                //specificUserRef = mUsersReference.child(thisUser.getUid());
                eventsJoined.add(Event.title);
                specificUserRef.child("eventsJoined").setValue(eventsJoined);
                specificEventRef.child("joinedList").setValue(listString);
                //Toast.makeText(EventActivity.this, "you have joined event " + titleTextView.getText().toString(), Toast.LENGTH_SHORT).show();
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

}
