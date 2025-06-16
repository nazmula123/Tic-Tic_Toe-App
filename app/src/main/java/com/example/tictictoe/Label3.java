package com.example.tictictoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tictictoe.R;

import java.util.ArrayList;
import java.util.List;

public class Label3 extends AppCompatActivity {

    private ImageView img_view, img_view2, img_view3, img_view4, img_view5, img_view6, img_view7, img_view8, img_view9;
    private List<int[]> combinationsList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int playerTurn = 1;
    private  TextView Score;
    private int totalSelectBox = 0;
    private float boardSize;
    DrawLineView drawLineView;
    private CardView boardLayout;
    Button exitId, res;
    private TextView gameEnd,player1,player2;
    int currentLabel=5;
    int incrementPlayerOne=0,incrementPlayerTwo=0;
    private LinearLayout playerOne, playerTwo;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label3);


        img_view = findViewById(R.id.img_01);
        img_view2 = findViewById(R.id.img_02);
        img_view3 = findViewById(R.id.img_03);
        img_view4 = findViewById(R.id.img_04);
        img_view5 = findViewById(R.id.img_05);
        img_view6 = findViewById(R.id.img_06);
        img_view7 = findViewById(R.id.img_07);
        img_view8 = findViewById(R.id.img_08);
        img_view9 = findViewById(R.id.img_09);
        boardLayout=findViewById(R.id.gameLayout);

        playerOne = findViewById(R.id.playerOne);
        playerTwo = findViewById(R.id.playerTwo);

        gameEnd=findViewById(R.id.completeGame);
        player1=findViewById(R.id.Player1);
        player2=findViewById(R.id.Player2);


        combinationsList.add(new int[]{0, 1, 2});
        combinationsList.add(new int[]{3, 4, 5});
        combinationsList.add(new int[]{6, 7, 8});
        combinationsList.add(new int[]{0, 3, 6});
        combinationsList.add(new int[]{1, 4, 7});
        combinationsList.add(new int[]{2, 5, 8});
        combinationsList.add(new int[]{2, 4, 6});
        combinationsList.add(new int[]{0, 4, 8});
        boardLayout.post(() -> boardSize = boardLayout.getWidth());

        setImageClickListener(img_view, 0);
        setImageClickListener(img_view2, 1);
        setImageClickListener(img_view3, 2);
        setImageClickListener(img_view4, 3);
        setImageClickListener(img_view5, 4);
        setImageClickListener(img_view6, 5);
        setImageClickListener(img_view7, 6);
        setImageClickListener(img_view8, 7);
        setImageClickListener(img_view9, 8);
    }

    private void setImageClickListener(ImageView imageView, int boxPosition) {
        imageView.setOnClickListener(v -> {
            if (isBoxSelectable(boxPosition) && playerTurn == 1) { // Only allow player to click when it's their turn
                performActionImage(imageView, boxPosition);
            }
        });
    }

    private void performActionImage(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;
        if (playerTurn == 1) {
            player1Sound();
            imageView.setImageResource(R.drawable.img_11);
            if (checkPlayerWin()) {

                incrementPlayerOne++;
                player1.setText(String.valueOf(incrementPlayerOne));

                showWin();
                showWinningLine();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetGame();
                    }
                }, 500);
            }
            else if (totalSelectBox == 8) {

                showWinningLine();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetGame();
                    }
                }, 500);
            }
            else {
                changePlayerTurn(2);
                totalSelectBox++;

                new Handler().postDelayed(this::performAiMove, 500);
            }
        } else if (playerTurn == 2) {
            player2Sound();
            imageView.setImageResource(R.drawable.cross);
            if (checkPlayerWin()) {

                incrementPlayerTwo++;
                player2.setText(String.valueOf(incrementPlayerTwo));
                showWin();
                showWinningLine();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetGame();
                    }
                }, 500);
            }
            else if (totalSelectBox == 8) {

                showWinningLine();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetGame();
                    }
                }, 500);
            } else {
                changePlayerTurn(1);
                totalSelectBox++;
            }
        }
    }

    private void performAiMove() {
        int aiMove = getBestMove();
        if (aiMove != -1) {
            ImageView aiImageView = getImageViewByBoxId(aiMove);
            if (aiImageView != null) {
                performActionImage(aiImageView, aiMove);
            }
        }
    }
    private void showWin(){
        MediaPlayer player;
        player=MediaPlayer.create(Label3.this,R.raw.win);
        player.start();

    }

    private int getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;

        for (int i = 0; i < boxPositions.length; i++) {
            if (boxPositions[i] == 0) {
                boxPositions[i] = 2;
                int score = minimax(boxPositions, 0, false);
                boxPositions[i] = 0;

                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    private int minimax(int[] board, int depth, boolean isMaximizing) {
        if (checkWin(board, 2)) {
            return 10 - depth;
        }
        if (checkWin(board, 1)) {
            return depth - 10;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 2;
                    int score = minimax(board, depth + 1, false);
                    board[i] = 0; // Undo move
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 1;
                    int score = minimax(board, depth + 1, true);
                    board[i] = 0;
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    private boolean isBoardFull(int[] board) {
        for (int value : board) {
            if (value == 0) return false;
        }
        return true;
    }

    private boolean checkWin(int[] board, int player) {
        for (int[] combination : combinationsList) {
            if (board[combination[0]] == player &&
                    board[combination[1]] == player &&
                    board[combination[2]] == player) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPlayerWin() {
        return checkWin(boxPositions, playerTurn);
    }

    private ImageView getImageViewByBoxId(int boxId) {
        switch (boxId) {
            case 0:
                return img_view;
            case 1:
                return img_view2;
            case 2:
                return img_view3;
            case 3:
                return img_view4;
            case 4:
                return img_view5;
            case 5:
                return img_view6;
            case 6:
                return img_view7;
            case 7:
                return img_view8;
            case 8:
                return img_view9;
            default:
                return null;
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            playerOne.setBackgroundResource(R.drawable.gamebackground);
            playerTwo.setBackgroundResource(R.color.black);
        } else {
            playerTwo.setBackgroundResource(R.drawable.gamebackground);
            playerOne.setBackgroundResource(R.color.black);
        }
    }
    private void player1Sound(){
        MediaPlayer mediaPlayer;
        mediaPlayer=MediaPlayer.create(Label3.this,R.raw.plaer1);
        mediaPlayer.start();
    }
    private void player2Sound(){
        MediaPlayer mediaPlayer;
        mediaPlayer=MediaPlayer.create(Label3.this,R.raw.player2);
        mediaPlayer.start();
    }
    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    private void resetGame() {

        if (drawLineView != null) {
            boardLayout.removeView(drawLineView);
            drawLineView = null;
        }

        currentLabel=currentLabel-1;
        gameEnd.setText(String.valueOf(currentLabel));
        if(currentLabel==0){
            if(incrementPlayerOne>incrementPlayerTwo){
                showDialog("Player Winner");
            }
            else if(incrementPlayerTwo>incrementPlayerOne){
                showDialog("AI  Winner");
            }
            else {
                showDialog("Match Draw!");
            }
        }

        else if(currentLabel<3){
            gameEnd.setTextColor(Color.RED);
        }



        for (int i = 0; i < boxPositions.length; i++) {
            boxPositions[i] = 0;
        }
        totalSelectBox = 0;
        playerTurn = 1;

        img_view.setImageResource(R.color.black);
        img_view2.setImageResource(R.color.black);
        img_view3.setImageResource(R.color.black);
        img_view4.setImageResource(R.color.black);
        img_view5.setImageResource(R.color.black);
        img_view6.setImageResource(R.color.black);
        img_view7.setImageResource(R.color.black);
        img_view8.setImageResource(R.color.black);
        img_view9.setImageResource(R.color.black);

        playerOne.setBackgroundResource(R.color.black);
        playerTwo.setBackgroundResource(R.color.black);
    }

    private void showWinningLine() {
        drawLineView = new DrawLineView(this, combinationsList, boxPositions, playerTurn, boardSize);
        boardLayout.addView(drawLineView);
        drawLineView.invalidate();
    }
    private void showDialog(String message) {
        LayoutInflater inflater = LayoutInflater.from(Label3.this);
        View dialogView = inflater.inflate(R.layout.dailog_box, null);

        exitId = dialogView.findViewById(R.id.exitId);
        res = dialogView.findViewById(R.id.restartId);

        Score=dialogView.findViewById(R.id.scoreId);
        if(incrementPlayerOne==incrementPlayerTwo){
            Score.setText("Match Draw!");
        }
        else if(incrementPlayerOne>incrementPlayerTwo){
            Score.setText(String.valueOf(incrementPlayerOne*25));
        }
        else if(incrementPlayerTwo>incrementPlayerOne){
            Score.setText(String.valueOf(incrementPlayerTwo*25));
        }

        TextView resultText = dialogView.findViewById(R.id.name);
        resultText.setText(message);

        sharedPreferences = getSharedPreferences("ResultDatabase", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ResultName", resultText.getText().toString());
        editor.apply();

        AlertDialog.Builder builder = new AlertDialog.Builder(Label3.this);
        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        res.setOnClickListener(v -> {

            Intent intent=new Intent(Label3.this,Label3.class);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });

        exitId.setOnClickListener(v -> {
            dialog.dismiss();
            finish();

        });
    }
}
