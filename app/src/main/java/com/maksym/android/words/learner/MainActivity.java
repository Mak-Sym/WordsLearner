package com.maksym.android.words.learner;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.maksym.android.words.learner.data.Data;
import com.maksym.android.words.learner.utils.Views;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int ROUNDS_COUNT = 15;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.martian_gun);

        final ImageButton playButton = (ImageButton) findViewById(R.id.playwordbutton);
        playButton.setEnabled(false);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderNextWord();
                updateCounter(mediaPlayer);
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                    playButton.setEnabled(true);
                } else {
                    Snackbar.make(findViewById(R.id.mainLayout), "Error initializing text to speech library.",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    fallbackToMediaPlayer();
                }
            }
        });

        TextView wordTextView = (TextView) findViewById(R.id.wordTextView);
        wordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(Data.currentWord());
            }
        });

        final TextView counterTextView = (TextView) findViewById(R.id.wordsCounterTextView);
        counterTextView.setText(String.valueOf(ROUNDS_COUNT));
        counterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterTextView.setText(String.valueOf(ROUNDS_COUNT));
            }
        });

        final TextView exampleTextView = (TextView) findViewById(R.id.exampleTextView);
        exampleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(exampleTextView.getText().toString());
            }
        });

        renderNextWord();
    }

    private void updateCounter(MediaPlayer mediaPlayer) {
        TextView textView = (TextView) findViewById(R.id.wordsCounterTextView);
        int value = Integer.parseInt(textView.getText().toString()) - 1;
        if(value == 0) {
            mediaPlayer.start();
            value = ROUNDS_COUNT;
        }
        textView.setText(String.valueOf(value));
    }

    private void renderNextWord() {
        String word = Data.nextWord();

        TextView textView = (TextView) findViewById(R.id.wordTextView);
        textView.setText(word);

        textView = (TextView) findViewById(R.id.capitalWordTextView);
        textView.setText(word.toUpperCase());

        textView = (TextView) findViewById(R.id.exampleTextView);
        textView.setText(Views.highlight(Data.currentWord(), Data.example()));
    }

    private void play(String textToPlay) {
        if(!textToSpeech.isSpeaking()) {
            textToSpeech.speak(textToPlay, TextToSpeech.QUEUE_FLUSH, null, textToPlay);
        }
    }

    private void fallbackToMediaPlayer() {
        // does nothing at the moment
    }

    @Override
    public void onDestroy() {
        if(textToSpeech.isSpeaking()) {
            textToSpeech.stop();
        }
        textToSpeech.shutdown();
        super.onDestroy();
    }
}
