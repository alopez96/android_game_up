package ca.alexbalt.gameup2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;

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
    private EditText timeText;
    private EditText idText;
    private String a, b, c, game, date, creator, time;
    private String uid;
    private String mUserEmail;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private DatabaseReference specificUserRef;

    private User thisUser;
    private ArrayList<String> eventsCreated = new ArrayList<>();
    private ArrayList<String> joinedList = new ArrayList<>();

    private static final String TAG = "PostActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView tv,tv2;
    Calendar currentTime;
    int hour;
    int minute;
    String format;

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
        dateText = findViewById(R.id.tvDate);

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

        tv = (TextView) findViewById(R.id.time_button);
        tv2 = (TextView) findViewById(R.id.time);
        currentTime = Calendar.getInstance();
        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);
        selectedTimeFormat(hour);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        PostActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }

        };

        tv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PostActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        tv.setText( hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

    }

    public void selectedTimeFormat(int hour){
        if(hour == 0){
            hour += 12;
            format = "AM";
        }else if(hour == 12){
            format = "PM";
        }else if(hour > 12){
            hour -= 12;
            format = "PM";
        }else{
            format = "AM";
        }
    }


    public void openPostActivity() {
        a = descEditText.getText().toString();
        b = titleText.getText().toString();
        c = consoleText.getText().toString();
        game = gameText.getText().toString();
        date = dateText.getText().toString();
        time = tv.getText().toString();


        if(!TextUtils.isEmpty(b)){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("events");
            String key = myRef.push().getKey();
            eventsCreated.add(b);
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