package com.thundercandy.epq;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PomodoroActivity extends DrawerActivity {

    private static final int DEFAULT_INTERVAL = 1000;                   // One second
    private static final int DEFAULT_DURATION = 30 * 60 * 1000;         // 30 minutes

    private long duration = DEFAULT_DURATION;
    private long interval = DEFAULT_INTERVAL;

    ImageView timerCircle;
    TextView txtTimeRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        setToolbarTitle("Pomodoro");
        timerCircle = findViewById(R.id.timerCircle);
        txtTimeRemaining = findViewById(R.id.txtTimeRemaining);

        int strokeWidth = 6;
        int halfStrokeWidth = strokeWidth / 2;

        CountDownTimer timer = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeRemaining = Utils.getDurationBreakdown(millisUntilFinished);
                txtTimeRemaining.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                Toast.makeText(PomodoroActivity.this, "Timer finished", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();

        Thread myThread = new Thread() {

            public void run() {

                final Bitmap bitmap = Bitmap.createBitmap(200 + strokeWidth, 200 + strokeWidth, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                int startingDeg = -90;
                int deg = 0;
                RectF rectF = new RectF(halfStrokeWidth, halfStrokeWidth, 200 + halfStrokeWidth, 200 + halfStrokeWidth);
                Paint paint;
                paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(strokeWidth);


                for (int i = 0; i <= 36; i++) {
                    canvas.drawArc(rectF, startingDeg, deg, false, paint);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(() -> timerCircle.setImageBitmap(bitmap));
                    deg += 10;
                }
            }
        };
        myThread.start();

    }

}
