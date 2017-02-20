package com.maksym.android.words.learner;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.maksym.android.words.learner.data.Data;
import com.maksym.android.words.learner.utils.Time;
import com.maksym.android.words.learner.utils.Views;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private volatile AtomicBoolean speaking = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton playButton = (ImageButton) findViewById(R.id.playwordbutton);
        playButton.setEnabled(false);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(speaking.compareAndSet(false, true)) {
                    playButton.setEnabled(false);
                    playButton.setClickable(false);
                    TextView exampleTextView = (TextView) findViewById(R.id.exampleTextView);
                    play(Data.currentWord());
                    Time.sleep(2000);
                    play(exampleTextView.getText().toString());
                    Time.sleep(3000);
                    renderNextWord();
                    speaking.set(false);
                    playButton.setEnabled(true);
                    playButton.setClickable(true);
                } else {
                    Log.d("DEBUG", "Skipping play");
                }
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
        });/*

        final TextView wordTextView = (TextView) findViewById(R.id.wordTextView);
        wordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SentenceActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });*/

        renderNextWord();
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
            Time.sleep(100);
        }
        textToSpeech.speak(textToPlay, TextToSpeech.QUEUE_FLUSH, null, "textToPlay");
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
