package com.example.tictictoe;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class DrawLineView extends View {

    private Paint paint = new Paint();
    private List<int[]> winningCombinations;
    private int[] boxPositions;
    private int playerTurn;
    private float boardSize;

    public DrawLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawLineView(Context context, List<int[]> winningCombinations, int[] boxPositions, int playerTurn, float boardSize) {
        super(context);
        this.winningCombinations = winningCombinations;
        this.boxPositions = boxPositions;
        this.playerTurn = playerTurn;
        this.boardSize = boardSize;
        init();
    }

    private void init() {

        paint.setShader(new LinearGradient(0, 0, getWidth(), getHeight(),
                Color.parseColor("#FF00FF"),
                Color.parseColor("#00FFFF"),
                Shader.TileMode.CLAMP));

        paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));

        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int[] combination : winningCombinations) {
            if (boxPositions[combination[0]] == playerTurn &&
                    boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {


                float startX = getXFromBoxPosition(combination[0]);
                float startY = getYFromBoxPosition(combination[0]);
                float endX = getXFromBoxPosition(combination[2]);
                float endY = getYFromBoxPosition(combination[2]);


                canvas.drawLine(startX, startY, endX, endY, paint);
                break;
            }
        }
    }

    private float getXFromBoxPosition(int boxPosition) {
        int column = boxPosition % 3;

        return (column * getWidth() / 3) + (getWidth() / 6);
    }

    private float getYFromBoxPosition(int boxPosition) {
        int row = boxPosition / 3;

        return (row * getHeight() / 3) + (getHeight() / 6);
    }
}
