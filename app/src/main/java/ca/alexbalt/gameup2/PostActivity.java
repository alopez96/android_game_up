package ca.alexbalt.gameup2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class PostActivity extends AppCompatActivity {
    private Button postButton;

    public static final int RC_SIGN_IN = 1;     ///request code
    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference, mUsersReference;
    private EditText descEditText;
    private EditText titleText;
    private EditText consoleText;
    private EditText gameText;
    private EditText dateText;
    private EditText idText;
    private String a, b, c, game, date, creator;
    private String uid;
    private String mUserEmail;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private DatabaseReference specificUserRef;

    private User thisUser;
    private ArrayList<String> eventsCreated = new ArrayList<>();
    private ArrayList<String> joinedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postButton = findViewById(R.id.post_to_home);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");


        descEditText = findViewById(R.id.desc_tv);
        titleText = findViewById(R.id.title_tv);
        consoleText = findViewById(R.id.console_tv);
        gameText = findViewById(R.id.game_tv);
        dateText = findViewById(R.id.date_tv);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();
        uid = user.getUid();
        creator = mUsername;

        mUsersReference = mFirebaseDatabase.getReference().child("users");
        specificUserRef = mUsersReference.child(uid);

        //get the list of events already in firebase
        specificUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisUser = dataSnapshot.getValue(User.class);
                eventsCreated = thisUser.eventsCreated;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostActivity();
            }
        });

    }


    public void openPostActivity() {
        a = descEditText.getText().toString();
        b = titleText.getText().toString();
        c = consoleText.getText().toString();
        game = gameText.getText().toString();
        date = dateText.getText().toString();
        String time = "1:00";
        //joinedList.add("");

        if(!TextUtils.isEmpty(b)){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("events");
            String key = myRef.push().getKey();
            eventsCreated.add(key);
            specificUserRef.child("eventsCreated").setValue(eventsCreated);
            event Event = new event(b, c, game, date, time, a, key, creator, joinedList);
            myRef.child(key).setValue(Event);
            Toast.makeText(this, "event added",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, EventActivity.class);
            i.putExtra("data", key);  // pass your values and retrieve them in the other Activity using data
            startActivity(i);


        }else{
            Toast.makeText(this, "enter a title",Toast.LENGTH_SHORT).show();
        }
    }

}