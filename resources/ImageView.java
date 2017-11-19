package com.gungoren.imageprocessing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ImageView extends View
{
    private static final String TAG = ImageView.class.getSimpleName();
    private List<Character> characterList = new ArrayList<>();

    private String text = "A";
    private int size = 1080;
    private int pieceWidth = 100, pieceHeight = 100;
    private Bitmap bitmap;

    public ImageView(Context context) {
        super(context);
        setBackgroundColor(Color.YELLOW);

        for (int i = 0; i < 6; i++) {
            characterList.add((char)(49 + i));
        }

        for (int i = 0; i < 26; i++) {
            characterList.add((char)(65 + i));
        }

        bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Map<Character, Bitmap> map = new HashMap<Character, Bitmap>();
        Random random = new Random();
        Canvas canvas = new Canvas(bitmap);
        for (int i = 0; i < size / pieceWidth; i++){
            for (int j = 0; j < size / pieceHeight; j++) {
                Character character = characterList.get(random.nextInt(32));
                Bitmap bmp = map.get(character);
                if (bmp == null){
                    bmp = test(character.toString());
                    map.put(character, bmp);
                }
                canvas.drawBitmap(bmp, i * pieceWidth, j * pieceHeight, null);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.size = widthMeasureSpec;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private Bitmap test(String text){
        Bitmap pieceBitmap = Bitmap.createBitmap(pieceWidth, pieceHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(pieceBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        final float textSize = 48f;
        // Adjust text size to fill rect
        paint.setTextSize(textSize);
        paint.setTextScaleX(1.0f);
        // ask the paint for the bounding rect if it were to draw this text
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        // get the height that would have been produced
        int h = bounds.bottom - bounds.top;
        // figure out what textSize setting would create that height of text
        float size = (((float)(pieceBitmap.getHeight())/h) * textSize);
        // and set it into the paint
        paint.setTextSize(size);
        // Now set the scale.
        // do calculation with scale of 1.0 (no scale)
        paint.setTextScaleX(1.0f);
        // ask the paint for the bounding rect if it were to draw this text.
        paint.getTextBounds(text, 0, text.length(), bounds);
        // determine the width
        int w = bounds.right - bounds.left;
        // determine how much to scale the width to fit the view
        float xscale = ((float) (pieceBitmap.getWidth())) / w;
        // set the scale for the text paint
        paint.setTextScaleX(xscale);

        canvas.drawText(text, 0, pieceBitmap.getHeight(), paint);
        return pieceBitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
