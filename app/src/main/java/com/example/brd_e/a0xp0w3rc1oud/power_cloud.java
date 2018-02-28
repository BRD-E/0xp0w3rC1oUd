package com.example.brd_e.a0xp0w3rc1oud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

import java.util.Set;

public class power_cloud extends AppCompatActivity {

    private TextView voiceInput;
    private TextView speakButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Hashtable<String, Integer> words= new Hashtable<String, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_cloud);

        TextView test = new TextView(this);
        test.setText("why");
        test.setGravity(Gravity.TOP);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(500,500,0,0);
        test.setLayoutParams(params);
        layout.addView(test);
        voiceInput = (TextView) findViewById(R.id.voiceInput);
        speakButton = (TextView) findViewById(R.id.btnSpeak);

        speakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });

    }

    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Listening...");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String [] latestWords = result.get(0).split(" ");
                    for(int w = 0; w < latestWords.length; w += 1)
                    {
                        String latestWord = latestWords[w];
                        if (words.get(latestWord) == null)
                        {
                            words.put(latestWords[w], new Integer(0));
                        }
                        words.put(latestWords[w], words.get(latestWord) + 1);


                    }
                    voiceInput.setText(stats());
                    if (words.get("finished") == null)
                    {
                        askSpeechInput();
                    }



                }
                break;
            }

        }
    }
    public String stats()
    {
        String result = "";
        Set<String> keys = words.keySet();
        for(String key: keys){
            result += key +": "+words.get(key) + "\n";
        }
        return result;
    }
}
