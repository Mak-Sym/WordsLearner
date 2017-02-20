package com.maksym.android.words.learner;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private final Random rnd = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton playButton = (ImageButton) findViewById(R.id.playwordbutton);
        playButton.setEnabled(false);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView wordTextView = (TextView) findViewById(R.id.wordTextView);
                String word = wordTextView.getText().toString();
                play(word);
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

        renderNextWord();
    }

    private void renderNextWord() {
        TextView wordTextView = (TextView) findViewById(R.id.wordTextView);
        int currentWordIndex = words.indexOf(wordTextView.getText().toString());
        int newIndex;
        do {
            newIndex = rnd.nextInt(words.size());
        } while (newIndex != currentWordIndex);
        wordTextView.setText(words.get(newIndex));

        wordTextView = (TextView) findViewById(R.id.capitalWordTextView);
        wordTextView.setText(words.get(newIndex).toUpperCase());
    }

    private void play(String textToPlay) {
        if(!textToSpeech.isSpeaking()) {
            textToSpeech.speak(textToPlay, TextToSpeech.QUEUE_FLUSH, null);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) { }
            renderNextWord();
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

    private static final List<String> words = Arrays.asList("a",
                                            "and",
                                            "be",
                                            "I",
                                            "in",
                                            "is",
                                            "it",
                                            "of",
                                            "that",
                                            "the",
                                            "to",
                                            "was");
}
