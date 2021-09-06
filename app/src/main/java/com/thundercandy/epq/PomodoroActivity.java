package com.thundercandy.epq;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PomodoroActivity extends DrawerActivity {

    private static class Timer {
        public static final int POMODORO = 1;
        public static final int BREAK = 2;
        public static final int LONG_BREAK = 3;
    }

    private static class UI_State {
        public static final int POMODORO_START = 1;
        public static final int POMODORO_END = 2;
        public static final int BREAK_START = 3;
        public static final int LONG_BREAK_START = 4;
        public static final int BREAK_END = 5;
    }

    private SharedPreferences sharedPreferences;
    public static final String KEY_BREAKS = "KEY_BREAKS";

    CountDownTimer timer;

    ImageView btnQuickSettings, timerCircle;
    TextView txtTimerType, txtTimeRemaining;
    Button btnStartPomodoro, btnStopPomodoro, btnStartBreak, btnStartLongBreak, btnStopBreak;

    public static final int strokeWidth = 30;
    public static final int timerSize = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        setToolbarTitle("Pomodoro");
        timerCircle = findViewById(R.id.timerCircle);
        txtTimerType = findViewById(R.id.txtTimerType);
        txtTimeRemaining = findViewById(R.id.txtTimeRemaining);
        btnStartPomodoro = findViewById(R.id.btnStartPomodoro);
        btnStopPomodoro = findViewById(R.id.btnStopPomodoro);
        btnStartBreak = findViewById(R.id.btnStartBreak);
        btnStartLongBreak = findViewById(R.id.btnStartLongBreak);
        btnStopBreak = findViewById(R.id.btnStopBreak);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);

        initBackgroundStorage();

        // Default values
        updateUI(UI_State.POMODORO_START);

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
        btnStartLongBreak.setOnClickListener(v -> {
            startLongBreak();
        });
        btnStopBreak.setOnClickListener(v -> {
            stopBreak();
        });

    }

    private void initBackgroundStorage() {
        sharedPreferences = this.getSharedPreferences("DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getInt(KEY_BREAKS, -1) == -1) {
            editor.putInt(KEY_BREAKS, 0);
            editor.apply();
        }
    }

    public void startPomodoro() {

        long duration = Utils.getPomodoroLength(this);
        long interval = Utils.getTimerInterval(this, duration);

        updateUI(UI_State.POMODORO_END);
        timer = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateProgressCircle((float) millisUntilFinished / duration);
                String timeRemaining = Utils.getDurationBreakdown(millisUntilFinished);
                txtTimeRemaining.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                timer = null;
                endOfPomodoro();

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();
    }

    private void stopPomodoro() {
        timer.cancel();
        timer = null;
        endOfPomodoro();
    }

    private void endOfPomodoro() {
        int pastBreaks = sharedPreferences.getInt(KEY_BREAKS, 0);
        pastBreaks++;
        Log.d("pastBreaks", "" + pastBreaks);

        if (pastBreaks >= Utils.getLongBreakAfter(this)) {
            sharedPreferences.edit().putInt(KEY_BREAKS, 0).apply();
            updateUI(UI_State.LONG_BREAK_START);
        } else {
            sharedPreferences.edit().putInt(KEY_BREAKS, pastBreaks).apply();
            updateUI(UI_State.BREAK_START);
        }
    }

    public void startBreak() {

        long duration = Utils.getBreakLength(this);
        long interval = Utils.getTimerInterval(this, duration);

        updateUI(UI_State.BREAK_END);
        timer = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateProgressCircle((float) millisUntilFinished / duration);
                String timeRemaining = Utils.getDurationBreakdown(millisUntilFinished);
                txtTimeRemaining.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                timer = null;
                updateUI(UI_State.POMODORO_START);

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();
    }

    private void startLongBreak() {

        long duration = Utils.getLongBreakLength(this);
        long interval = Utils.getTimerInterval(this, duration);

        updateUI(UI_State.BREAK_END);
        timer = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateProgressCircle((float) millisUntilFinished / duration);
                String timeRemaining = Utils.getDurationBreakdown(millisUntilFinished);
                txtTimeRemaining.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                timer = null;
                updateUI(UI_State.POMODORO_START);

                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();
    }

    private void stopBreak() {
        timer.cancel();
        timer = null;
        updateUI(UI_State.POMODORO_START);
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

    public void resetTimeRemaining(int TYPE) {
        updateProgressCircle(1);
        switch (TYPE) {
            case Timer.BREAK:
                txtTimeRemaining.setText(Utils.getDurationBreakdown(Utils.getBreakLength(this)));
                break;
            case Timer.LONG_BREAK:
                txtTimeRemaining.setText(Utils.getDurationBreakdown(Utils.getLongBreakLength(this)));
                break;
            case Timer.POMODORO:
            default:
                txtTimeRemaining.setText(Utils.getDurationBreakdown(Utils.getPomodoroLength(this)));
                break;
        }
    }

    private void hideAllButtons() {
        btnStartPomodoro.setVisibility(View.GONE);
        btnStopPomodoro.setVisibility(View.GONE);
        btnStartBreak.setVisibility(View.GONE);
        btnStartLongBreak.setVisibility(View.GONE);
        btnStopBreak.setVisibility(View.GONE);
    }

    public void updateUI(int id) {
        hideAllButtons();
        switch (id) {
            case UI_State.POMODORO_START:
                resetTimeRemaining(Timer.POMODORO);
                btnStartPomodoro.setVisibility(View.VISIBLE);
                txtTimerType.setText("Pomodoro");
                break;
            case UI_State.POMODORO_END:
                btnStopPomodoro.setVisibility(View.VISIBLE);
                break;
            case UI_State.BREAK_START:
                resetTimeRemaining(Timer.BREAK);
                btnStartBreak.setVisibility(View.VISIBLE);
                txtTimerType.setText("Break");
                break;
            case UI_State.LONG_BREAK_START:
                resetTimeRemaining(Timer.LONG_BREAK);
                btnStartLongBreak.setVisibility(View.VISIBLE);
                txtTimerType.setText("Long Break");
                break;
            case UI_State.BREAK_END:
                btnStopBreak.setVisibility(View.VISIBLE);
                break;
        }
    }
}
