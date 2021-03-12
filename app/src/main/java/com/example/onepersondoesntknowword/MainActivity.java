package com.example.onepersondoesntknowword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private Button mStartRoundButton;
    private EditText mNumberOfPeople;

    private int numberOfPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartRoundButton = (Button)findViewById(R.id.StartRoundButton);
        mNumberOfPeople = (EditText) findViewById(R.id.NumberOfPeopleEditText);

        mStartRoundButton.setEnabled(false);

        mNumberOfPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateNumberOfPeople();
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateNumberOfPeople();
            }
        });


        mStartRoundButton.setOnClickListener(v -> StartRound() );

    }

    private void validateNumberOfPeople() {

        //first check that mNumberOfPeople
        String numberOfPeopleString = mNumberOfPeople.getText().toString();

        try {
            numberOfPeople = Integer.parseInt(numberOfPeopleString);

            mStartRoundButton.setEnabled(numberOfPeople > 2);

        } catch (NumberFormatException nfe) {
            mStartRoundButton.setEnabled(false);
        }

    }

    private void StartRound() {

        Intent i = new Intent(MainActivity.this, CategoryOpenDisplay.class);
        i.putExtra(CategoryOpenDisplay.EXTRA_NUMBER_OF_PEOPLE, numberOfPeople);
        startActivity(i);

    }
}