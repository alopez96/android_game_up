package ca.alexbalt.gameup2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditEventActivity extends AppCompatActivity {
    private Button submitButton;
    private Button cancelButton;

    private EditText titleText, consoleText, gameText, dateText, timeText, bodyText;
    private String mUsername, mUserEmail, mTitle, mConsole, mGame, mDate, mTime, mBody, eventkey;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsReference;
    private DatabaseReference specificEventRef;
    private User thisUser;
    private event Event;
    private ArrayList<String> eventsCreated = new ArrayList<>();
    private ArrayList<String> joinedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        submitButton = findViewById(R.id.buttonSubmit);
        cancelButton = findViewById(R.id.buttonCancel);
        titleText = findViewById(R.id.editTextTitle);
        consoleText = findViewById(R.id.editTextConsole);
        gameText = findViewById(R.id.editTextGame);
        dateText = findViewById(R.id.editTextDate);
        timeText = findViewById(R.id.editTextTime);
        bodyText = findViewById(R.id.editTextBody);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();

        Intent intent = getIntent();
        eventkey = intent.getStringExtra("key");
        if (eventkey == null) {
            throw new IllegalArgumentException("Must pass EVENT KEY VALUE");
        }
        specificEventRef = mEventsReference.child(eventkey);
    }

    @Override
    protected void onStart(){
        super.onStart();
        specificEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event = dataSnapshot.getValue(event.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChanges();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelChanges();
            }
        });
    }



    public void submitChanges(){
        mTitle = titleText.getText().toString();
        mConsole = consoleText.getText().toString();
        mGame = gameText.getText().toString();
        mDate = dateText.getText().toString();
        mTime = timeText.getText().toString();
        mBody = bodyText.getText().toString();

        specificEventRef.child("title").setValue(mTitle);
        specificEventRef.child("console").setValue(mConsole);
        specificEventRef.child("game").setValue(mGame);
        specificEventRef.child("date").setValue(mDate);
        specificEventRef.child("time").setValue(mTime);
        specificEventRef.child("body").setValue(mBody);
        Toast.makeText(this, "account updated",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "changes submitted",Toast.LENGTH_SHORT).show();
        EditEventActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }


    public void cancelChanges(){
        Toast.makeText(this, "update cancelled",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        EditEventActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
    }

