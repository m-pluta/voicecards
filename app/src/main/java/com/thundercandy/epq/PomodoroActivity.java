package com.thundercandy.epq;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PomodoroActivity extends DrawerActivity {

    private static final int DEFAULT_INTERVAL = 1000;                   // One second //TODO: allow smooth intervals

    CountDownTimer timer;

    ImageView timerCircle;
    TextView txtTimeRemaining;
    Button btnStartPomodoro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        setToolbarTitle("Pomodoro");
        timerCircle = findViewById(R.id.timerCircle);
        txtTimeRemaining = findViewById(R.id.txtTimeRemaining);
        btnStartPomodoro = findViewById(R.id.btnStartPomodoro);

        btnStartPomodoro.setOnClickListener(v -> {
            startPomodoro();
        });
    }

    public void startPomodoro() {
        changeTimerUIVisibility(true);

        long duration = Utils.getPomodoroLength(this);
        long interval = DEFAULT_INTERVAL;

        timer = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateProgressCircle((float) millisUntilFinished / duration);
                String timeRemaining = Utils.getDurationBreakdown(millisUntilFinished);
                txtTimeRemaining.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                changeTimerUIVisibility(false);

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                //TODO: show break option or next pomodoro
            }
        };
        timer.start();
    }

    public void startBreak() {
        changeTimerUIVisibility(true);

        long duration = Utils.getBreakLength(this);
        long interval = DEFAULT_INTERVAL;

        timer = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateProgressCircle((float) millisUntilFinished / duration);
                String timeRemaining = Utils.getDurationBreakdown(millisUntilFinished);
                txtTimeRemaining.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                changeTimerUIVisibility(false);

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                //TODO: show next pomodoro
            }
        };
        timer.start();
    }

    private void updateProgressCircle(float prg) {
        int strokeWidth = 6;
        int halfStrokeWidth = strokeWidth / 2;

        final Bitmap bitmap = Bitmap.createBitmap(200 + strokeWidth, 200 + strokeWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float startingAngle = -90f;
        float endingAngle = prg * 360f;

        RectF rectF = new RectF(halfStrokeWidth, halfStrokeWidth, 200 + halfStrokeWidth, 200 + halfStrokeWidth);
        Paint paint;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        canvas.drawArc(rectF, startingAngle, endingAngle, false, paint);
        runOnUiThread(() -> timerCircle.setImageBitmap(bitmap));
    }

    public void changeTimerUIVisibility(boolean vis) {
        FrameLayout timerUI = findViewById(R.id.timerUI);
        if (vis) {
            timerUI.setVisibility(View.VISIBLE);
        } else {
            timerUI.setVisibility(View.INVISIBLE);
        }
    }
}
