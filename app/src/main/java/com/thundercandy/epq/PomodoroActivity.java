package com.thundercandy.epq;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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

    CountDownTimer timer;

    ImageView btnQuickSettings, timerCircle;
    TextView txtTimeRemaining;
    Button btnStartPomodoro;

    public static final int strokeWidth = 18;
    public static final int timerSize = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        setToolbarTitle("Pomodoro");
        timerCircle = findViewById(R.id.timerCircle);
        txtTimeRemaining = findViewById(R.id.txtTimeRemaining);
        btnStartPomodoro = findViewById(R.id.btnStartPomodoro);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);     //TODO: make the ripple shape circular instead of square

        // Default values
        resetTimerUI();

        btnQuickSettings.setOnClickListener(v -> {
            findViewById(R.id.btnSettings).performClick();
        });

        btnStartPomodoro.setOnClickListener(v -> {
            startPomodoro();
        });
    }

    public void startPomodoro() {
        changeTimerUIVisibility(true);

        long duration = Utils.getPomodoroLength(this);
        long interval = Utils.getTimerInterval(this, duration);

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
                timer = null;
                resetTimerUI();

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                //TODO: show break option or next pomodoro
            }
        };
        timer.start();
    }

    public void startBreak() {
        changeTimerUIVisibility(true);

        long duration = Utils.getBreakLength(this);
        long interval = Utils.getTimerInterval(this, duration);

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
                timer = null;
                resetTimerUI();

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                //TODO: show next pomodoro
            }
        };
        timer.start();
    }

    private void updateProgressCircle(float prg) {
        int halfStrokeWidth = strokeWidth / 2;

        final Bitmap bitmap = Bitmap.createBitmap(timerSize + strokeWidth, timerSize + strokeWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float startingAngle = -90f;
        float endingAngle = prg * 360f;

        RectF rectF = new RectF(halfStrokeWidth, halfStrokeWidth, timerSize + halfStrokeWidth, timerSize + halfStrokeWidth);
        Paint paint;
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.timer_color));
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

    public void resetTimerUI() {
        updateProgressCircle(1);
        txtTimeRemaining.setText(Utils.getDurationBreakdown(Utils.getPomodoroLength(this)));
    }
}
