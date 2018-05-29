package com.someapp.vishnu.myeggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String timeString;
    TextView timeStringView;
    SeekBar timeControl;
    MediaPlayer mediaPlayer;
    CountDownTimer myCount;

    int timeInSeconds;
    boolean isStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeInSeconds = 0;

        timeControl = findViewById(R.id.seekBar);

        timeControl.setMax(600);

        timeStringView = findViewById(R.id.textView);
        timeString = timeStringView.getText().toString();

        Toast.makeText(this,timeString, Toast.LENGTH_SHORT).show();

        timeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                timeInSeconds = progress;

                if((timeInSeconds % 60) < 10) {
                    timeString = timeInSeconds/60 + ":0" + timeInSeconds%60;
                } else {
                    timeString = timeInSeconds/60 + ":" + timeInSeconds%60;
                }

                timeStringView.setText(timeString);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

                myCount = new CountDownTimer( timeInSeconds*1000,
                        1000) {

                    public void onTick(long millisecondsUntilDone) {

                        if(!isStop) return;

                        timeInSeconds -= 1;

                        seekBar.setProgress(timeInSeconds);

                        if((seekBar.getProgress() % 60) < 10) {
                            timeString = seekBar.getProgress()/60 + ":0" + seekBar.getProgress()%60;
                        } else {
                            timeString = seekBar.getProgress()/60 + ":" + seekBar.getProgress()%60;
                        }

                        timeStringView.setText(timeString);

                    }

                    public void onFinish() {

                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);

                        mediaPlayer.start();

                        timeStringView.setText("0:00");

                        Toast.makeText(getApplicationContext(),
                                "Hooray!", Toast.LENGTH_SHORT).show();

                    }

                }.start();

            }
        });

    }

    public void onClick(View view) {

        Button button = findViewById(R.id.button);

        if(!isStop) {

            isStop = true;
            button.setText("Stop!");
            timeControl.setEnabled(false);

        } else {

            isStop = false;
            button.setText("Go!");
            timeControl.setEnabled(true);
            myCount.cancel();

        }

    }
}
