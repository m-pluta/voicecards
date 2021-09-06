package com.thundercandy.epq;

import android.content.SharedPreferences;
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

    public static final int POMODORO = 0;
    public static final int BREAK = 1;

    private SharedPreferences sharedPreferences;
    public static final String KEY_BREAKS = "KEY_BREAKS";

    CountDownTimer timer;

    ImageView btnQuickSettings, timerCircle;
    TextView txtTimeRemaining;
    Button btnStartPomodoro, btnStopPomodoro, btnStartBreak, btnStopBreak;

    public static final int strokeWidth = 18;
    public static final int timerSize = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        setToolbarTitle("Pomodoro");
        timerCircle = findViewById(R.id.timerCircle);
        txtTimeRemaining = findViewById(R.id.txtTimeRemaining);
        btnStartPomodoro = findViewById(R.id.btnStartPomodoro);
        btnStopPomodoro = findViewById(R.id.btnStopPomodoro);
        btnStartBreak = findViewById(R.id.btnStartBreak);
        btnStopBreak = findViewById(R.id.btnStopBreak);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);

//        initBackgroundStorage();

        // Default values
        resetTimerUI(POMODORO);

        btnQuickSettings.setOnClickListener(v -> {
            findViewById(R.id.btnSettings).performClick();
        });

        btnStartPomodoro.setOnClickListener(v -> {
            startPomodoro();
        });
        btnStopPomodoro.setOnClickListener(v -> {
            stopPomodoro();
        });
        btnStartBreak.setOnClickListener(v -> {
            startBreak();
        });
        btnStopBreak.setOnClickListener(v -> {
            stopBreak();
        });

    }

//    private void initBackgroundStorage() {
//        sharedPreferences = this.getSharedPreferences("DB", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        if (sharedPreferences.getInt(KEY_BREAKS, -1) == -1) {
//            editor.putInt(KEY_BREAKS, 0);
//            editor.apply();
//        }
//    }

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
                resetTimerUI(BREAK);
                btnStopPomodoro.setVisibility(View.GONE);
                btnStartBreak.setVisibility(View.VISIBLE);

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                //TODO: show break option or next pomodoro
            }
        };
        timer.start();
        btnStartPomodoro.setVisibility(View.GONE);
        btnStopPomodoro.setVisibility(View.VISIBLE);
    }

    private void stopPomodoro() {
        timer.cancel();
        timer = null;
        btnStopPomodoro.setVisibility(View.GONE);
        btnStartBreak.setVisibility(View.VISIBLE);
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
                resetTimerUI(POMODORO);
                btnStopBreak.setVisibility(View.GONE);
                btnStartPomodoro.setVisibility(View.VISIBLE);

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
                //TODO: show next pomodoro
            }
        };
        timer.start();
        btnStartBreak.setVisibility(View.GONE);
        btnStopBreak.setVisibility(View.VISIBLE);
    }

    private void stopBreak() {
        timer.cancel();
        timer = null;
        btnStopBreak.setVisibility(View.GONE);
        btnStartPomodoro.setVisibility(View.VISIBLE);
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

    public void resetTimerUI(int TYPE) {
        updateProgressCircle(1);
        switch (TYPE) {
            case POMODORO:
                txtTimeRemaining.setText(Utils.getDurationBreakdown(Utils.getPomodoroLength(this)));
                break;
            case BREAK:
                txtTimeRemaining.setText(Utils.getDurationBreakdown(Utils.getBreakLength(this)));
                break;
        }

    }
}
