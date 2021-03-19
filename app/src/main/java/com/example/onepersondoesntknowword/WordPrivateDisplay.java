package com.example.onepersondoesntknowword;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordPrivateDisplay extends AppCompatActivity {

    public static final String EXTRA_NUMBER_OF_PEOPLE = "com.example.onePersonDoesntKnowWord.wordPrivateDisplay.numberOfPeople";
    public static final String EXTRA_IGNORANT_PERSON_NUMBER = "com.example.onePersonDoesntKnowWord.wordPrivateDisplay.ignorantPerson";
    public static final String EXTRA_WORD_UNDER_TEST = "com.example.onePersonDoesntKnowWord.wordPrivateDisplay.wordToDraw";
    public static final String EXTRA_CATEGORY_OF_WORD_UNDER_TEST = "com.example.onePersonDoesntKnowWord.wordPrivateDisplay.categoryOfWord";
    public static final String EXTRA_NOBODY_KNOWS_WORD = "com.example.onePersonDoesntKnowWord.wordPrivateDisplay.nobodyKnowsWord";
    public static final String EXTRA_EVERYBODY_KNOWS_WORD = "com.example.onePersonDoesntKnowWord.wordPrivateDisplay.everybodyKnowsWord";

    private int personNumber;

    private int numberOfPeople;
    private int ignorantPersonNumber;
    private String wordToDraw;

    private Button mShowWordButton;
    private TextView mCategoryTextView;
    private TextView mWordTextView;

    private boolean nobodyKnowsTheWord;
    private boolean everybodyKnowsTheWord;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_private_display);

        mCategoryTextView = (TextView) findViewById(R.id.categoryReminderTextView);
        mWordTextView = (TextView) findViewById(R.id.privateWordTextView);

        mShowWordButton = (Button) findViewById(R.id.privateWordButton);
        mShowWordButton.setOnTouchListener(this::textViewModifier);

    }

    @Override
    public void onStart() {
        super.onStart();

        personNumber = 0;

        numberOfPeople = getIntent().getIntExtra(EXTRA_NUMBER_OF_PEOPLE, 0);
        ignorantPersonNumber = getIntent().getIntExtra(EXTRA_IGNORANT_PERSON_NUMBER, 0);

        wordToDraw = getIntent().getStringExtra(EXTRA_WORD_UNDER_TEST);

        everybodyKnowsTheWord = getIntent().getBooleanExtra(EXTRA_EVERYBODY_KNOWS_WORD, false);
        nobodyKnowsTheWord = getIntent().getBooleanExtra(EXTRA_NOBODY_KNOWS_WORD, false);

        mCategoryTextView.setText(getIntent().getStringExtra(EXTRA_CATEGORY_OF_WORD_UNDER_TEST));
    }

    public boolean textViewModifier(View _view, MotionEvent _event) {

        switch (_event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (nobodyKnowsTheWord == false && everybodyKnowsTheWord == false) {
                    if (personNumber < numberOfPeople) {

                        if (personNumber == ignorantPersonNumber) {
                            mWordTextView.setText(R.string.private_display_ignorance_string);
                        } else {
                            mWordTextView.setText(wordToDraw);
                        }
                    }
                }
                else if (nobodyKnowsTheWord == true && everybodyKnowsTheWord == false) {
                    mWordTextView.setText(R.string.private_display_ignorance_string);
                }
                else if (nobodyKnowsTheWord == false && everybodyKnowsTheWord == true) {
                    mWordTextView.setText(wordToDraw);
                }
                else {
                    mWordTextView.setText(R.string.private_display_rushed_change_has_broken);
                }
                return true;
            case MotionEvent.ACTION_UP:
                personNumber++;

                if (personNumber >= numberOfPeople){
                    mWordTextView.setText(R.string.private_display_all_people_shown_string);
                    mShowWordButton.setEnabled(false);
                }
                else {
                    mWordTextView.setText(R.string.initial_blank_text);
                }
                return true;

        }
        return false;

    }
}