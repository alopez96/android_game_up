package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class PostActivity extends AppCompatActivity {
    private Button postButton;

    public static final int RC_SIGN_IN = 1;     ///request code
    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference;
    private EditText descEditText;
    private String a, b, c;
    private String mUserEmail;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postButton = findViewById(R.id.post_to_home);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostActivity();
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");

        descEditText = findViewById(R.id.desc_tv);

        event eventName = new event(a,b,c);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();
    }


    public void openPostActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        a = descEditText.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("events");

        DatabaseReference eventRef = database.getReference("events");
        eventRef.child("event3").child("description").setValue(a);
        eventRef.child("event3").child("creator").setValue(mUsername);
        eventRef.child("event3").child("name").setValue("ganging");
        eventRef.child("event3").child("attendees").child("user1").setValue("true");
        eventRef.child("event3").child("attendees").child("user2").setValue("false");
    }

}