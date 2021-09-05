package com.thundercandy.epq;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

public class PomodoroActivity extends DrawerActivity {

    ImageView timerCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        setToolbarTitle("Pomodoro");
        timerCircle = findViewById(R.id.timerCircle);

        int strokeWidth = 6;
        int halfStrokeWidth = strokeWidth / 2;


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
