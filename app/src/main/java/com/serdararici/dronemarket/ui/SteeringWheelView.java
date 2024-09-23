package com.serdararici.dronemarket.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SteeringWheelView extends View {

    private Paint paint;
    private float strokeWidth = 20f; // Çizgi kalınlığı
    private float centerStrokeWidth = 25f; // Merkez çizgisi kalınlığı

    public SteeringWheelView(Context context) {
        super(context);
        init();
    }

    public SteeringWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SteeringWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK); // Direksiyon rengi
        paint.setStyle(Paint.Style.STROKE); // Çizgi şekli
        paint.setStrokeCap(Paint.Cap.ROUND); // Yuvarlatılmış uçlar
        paint.setAntiAlias(true); // Kenar yumuşatma
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        float radius = Math.min(width, height) / 2f - 20;  // Çemberin yarıçapı

        // Çizgi kalınlığını ayarla
        paint.setStrokeWidth(strokeWidth);

        // Direksiyonun çemberini çiz
        canvas.drawCircle(width / 2f, height / 2f, radius, paint);

        // Direksiyonun merkez çizgilerini çiz
        paint.setStrokeWidth(centerStrokeWidth); // Merkez çizgileri için daha kalın

        // Dikey çizgi
        canvas.drawLine(width / 2f, height / 2f - radius, width / 2f, height / 2f + radius, paint);

        // Yatay çizgi
        canvas.drawLine(width / 2f - radius, height / 2f, width / 2f + radius, height / 2f, paint);

        // Direksiyonun merkezine küçük bir daire ekleyin
        paint.setStrokeWidth(5f); // Merkez noktası için ince çizgi
        paint.setStyle(Paint.Style.FILL); // Dolu daire
        canvas.drawCircle(width / 2f, height / 2f, 20f, paint);
    }
}
