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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class PostActivity extends AppCompatActivity {
    private Button postButton;

    public static final int RC_SIGN_IN = 1;     ///request code
    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference;
    private EditText descEditText;
    private EditText titleText;
    private EditText consoleText;
    private EditText gameText;
    private EditText dateText;
    private EditText idText;
    private String a, b, c, game, date;
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
        titleText = findViewById(R.id.title_tv);
        consoleText = findViewById(R.id.console_tv);
        gameText = findViewById(R.id.game_tv);
        dateText = findViewById(R.id.date_tv);

        //event eventName = new event(a,b,c);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();

    }


    public void openPostActivity() {
        a = descEditText.getText().toString();
        b = titleText.getText().toString();
        c = consoleText.getText().toString();
        game = gameText.getText().toString();
        date = dateText.getText().toString();
        if(!TextUtils.isEmpty(b)){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("events");

            DatabaseReference eventRef = database.getReference("events");

            String key = myRef.push().getKey();
            event Event = new event(b, c, game, date, a, key);
            myRef.child(key).setValue(Event);
            Toast.makeText(this, "event added",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, EventActivity.class);
            Intent intent = new Intent(PostActivity.this, MainActivity.class);
            Toast.makeText(PostActivity.this,"post key "+ key,Toast.LENGTH_LONG).show();
            i.putExtra("data", key);  // pass your values and retrieve them in the other Activity using keyName
            startActivity(intent);

        }else{
            Toast.makeText(this, "enter a title",Toast.LENGTH_SHORT).show();
        }
    }

}