package ca.alexbalt.gameup2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity {
    private Button submitButton;
    private Button cancelButton;

    private EditText titleText, consoleText, gameText, dateText, timeText, bodyText;
    private String mUsername, mUserEmail, mTitle, mConsole, mGame, mDate, mTime, mBody, eventkey;
    private String stitle, sconsole,sgame, sdate, stime, sbody;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsReference;
    private DatabaseReference specificEventRef;
    private User thisUser;
    private event Event;
    private ArrayList<String> eventsCreated = new ArrayList<>();
    private ArrayList<String> joinedList = new ArrayList<>();

    private static final String TAG = "PostActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView timebtn;
    Calendar currentTime;
    int hour;
    int minute;
    String format;


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
        timebtn = findViewById(R.id.time_bttn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();

        currentTime = Calendar.getInstance();
        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        selectedTimeFormat(hour);

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        EditEventActivity.this,

                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,

                        mDateSetListener,

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
                dateText.setText(date);
            }

        };

        timebtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        timeText.setText( hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        Intent intent = getIntent();
        eventkey = intent.getStringExtra("key");
        stitle = intent.getStringExtra("title");
        sconsole = intent.getStringExtra("console");
        sgame = intent.getStringExtra("game");
        sdate = intent.getStringExtra("date");
        stime = intent.getStringExtra("time");
        sbody = intent.getStringExtra("body");

        titleText.setText(stitle, TextView.BufferType.EDITABLE);
        consoleText.setText(sconsole, TextView.BufferType.EDITABLE);
        gameText.setText(sgame, TextView.BufferType.EDITABLE);
        dateText.setText(sdate, TextView.BufferType.EDITABLE);
        timeText.setText(stime, TextView.BufferType.EDITABLE);
        bodyText.setText(sbody, TextView.BufferType.EDITABLE);


        if (eventkey == null) {
            throw new IllegalArgumentException("Must pass EVENT KEY VALUE");
        }
        specificEventRef = mEventsReference.child(eventkey);
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

