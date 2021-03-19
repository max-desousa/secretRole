package com.example.onepersondoesntknowword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryOpenDisplay extends AppCompatActivity {

    public static final String EXTRA_NUMBER_OF_PEOPLE = "com.example.onePersonDoesntKnowWord.categoryOpenDisplay.numberOfPeople";

    private int numberOfPersonThatDoesNotGetToKnowWord;
    private int selectedWordIndex;

    private TextView mCategoryOfWord;
    private Button mFinalStartRoundButton;
    private Switch mEverybodyKnowsWordSwitch;
    private Switch mNobodyKnowsWordSwitch;

    private final List<WordCategoryPair> wordList = new ArrayList<>();

    private int numberOfPeople;

    public static final String TAG = "act_catLifeCycle";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_open_display);
        Log.d(TAG, "onCreate() called");

        readWordPairList();


        numberOfPeople = getIntent().getIntExtra(EXTRA_NUMBER_OF_PEOPLE, 0);


        mCategoryOfWord = (TextView)findViewById(R.id.categoryTextView);
        mFinalStartRoundButton = (Button)findViewById(R.id.finalStartRoundButton);
        mEverybodyKnowsWordSwitch = (Switch)findViewById(R.id.switch_chanceEveryoneKnows);
        mNobodyKnowsWordSwitch = (Switch)findViewById(R.id.switch_chanceNooneKnows);

        TextView mNumberOfPeople = (TextView) findViewById(R.id.numberOfInputPeopleTextView);
        mNumberOfPeople.setText(String.valueOf(numberOfPeople));

        mFinalStartRoundButton = (Button)findViewById(R.id.finalStartRoundButton);

        mFinalStartRoundButton.setOnClickListener(v -> startRound() );
    }

    private void readWordPairList() {
        InputStream is = getResources().openRawResource(R.raw.wordslist);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line;

        try {

            while ( (line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                WordCategoryPair wordPair = new WordCategoryPair();
                wordPair.setCategory(tokens[0]);
                wordPair.setWord(tokens[1]);
                wordList.add(wordPair);
            }

        } catch (IOException ioe) {
            mFinalStartRoundButton.setEnabled(false);
            Toast.makeText(CategoryOpenDisplay.this, "Error reading data. Please restart app or screen", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");

        Random random = new Random();
        numberOfPersonThatDoesNotGetToKnowWord = random.nextInt(numberOfPeople);

        selectedWordIndex = random.nextInt(wordList.size() + 1);

        mCategoryOfWord.setText(wordList.get(selectedWordIndex).getCategory());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() calle");
    }

    private void startRound() {

        Random random = new Random();

        boolean everybodyKnows = false;
        boolean nobodyKnows = false;
        int decidingDigit = random.nextInt(10);

        if ((decidingDigit == 0) && (mEverybodyKnowsWordSwitch.isChecked())) {
            everybodyKnows = true;
        }

        if ((decidingDigit == 1) && (mNobodyKnowsWordSwitch.isChecked())) {
            nobodyKnows = true;
        }



        Intent i = new Intent(CategoryOpenDisplay.this, WordPrivateDisplay.class);
        i.putExtra(WordPrivateDisplay.EXTRA_NUMBER_OF_PEOPLE, numberOfPeople);
        i.putExtra(WordPrivateDisplay.EXTRA_IGNORANT_PERSON_NUMBER, numberOfPersonThatDoesNotGetToKnowWord);
        i.putExtra(WordPrivateDisplay.EXTRA_WORD_UNDER_TEST, wordList.get(selectedWordIndex).getWord());
        i.putExtra(WordPrivateDisplay.EXTRA_CATEGORY_OF_WORD_UNDER_TEST, wordList.get(selectedWordIndex).getCategory());
        i.putExtra(WordPrivateDisplay.EXTRA_EVERYBODY_KNOWS_WORD, everybodyKnows);
        i.putExtra(WordPrivateDisplay.EXTRA_NOBODY_KNOWS_WORD, nobodyKnows);
        startActivity(i);
    }

}