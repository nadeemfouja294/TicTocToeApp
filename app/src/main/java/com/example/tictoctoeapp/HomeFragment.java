package com.example.tictoctoeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.github.jinatonic.confetti.CommonConfetti;


public class HomeFragment extends Fragment implements View.OnClickListener {

    Button[] button = new Button[9];
    ConstraintLayout clRoot;
    ImageButton btnReset;
    String[] board = new String[9];
    TextView tvTitle, tvScoreX, tvScoreO;
    String playerStatus = "O";
    float tass;
    int xResult = 0, oResult = 0, clickCount = 0;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        clRoot = view.findViewById(R.id.clRoot);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvScoreO = view.findViewById(R.id.o_ScoreTV);
        tvScoreX = view.findViewById(R.id.x_ScoreTV);
        //set Score on textview
        tvScoreO.setText("" + oResult);
        tvScoreX.setText("" + xResult);


        //Tass
        tass();


        button[0] = view.findViewById(R.id.button1);
        button[1] = view.findViewById(R.id.button2);
        button[2] = view.findViewById(R.id.button3);
        button[3] = view.findViewById(R.id.button4);
        button[4] = view.findViewById(R.id.button5);
        button[5] = view.findViewById(R.id.button6);
        button[6] = view.findViewById(R.id.button7);
        button[7] = view.findViewById(R.id.button8);
        button[8] = view.findViewById(R.id.button9);
        btnReset = view.findViewById(R.id.btnReset);


        for (int i = 0; i < 9; i++)
            button[i].setOnClickListener(this);

        playAgain();


        return view;
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText().toString().equals("")) {
            clickCount++;
            ((Button) v).setText(playerStatus);
            ((Button) v).setTextSize(40);


            for (int a = 0; a < 9; a++) {
                if (v.getId() == button[a].getId()) {
                    board[a] = playerStatus;
                    break;
                }
            }

            if (playerStatus == "X") {
                ((Button) v).setTextColor(Color.parseColor("#4169E1"));
                playerStatus = "O";
            } else {
                ((Button) v).setTextColor(Color.parseColor("#FF69B4"));
                playerStatus = "X";
            }
            tvTitle.setText(playerStatus + "  Turn");

            //Check Winner
            if (clickCount > 2)
                checkWinner();


        }

    }

    private void checkWinner() {
        String line = null;
        for (int a = 0; a < 8; a++) {


            switch (a) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;


            }

            //For X winner
            if (line.equals("XXX"))
                afterWinning("X");


                // For O winner
            else if (line.equals("OOO"))
                afterWinning("O");

            else if (clickCount == 9)
                afterWinning("Match Withdraw");
            else
                continue;
        }
    }

    private void afterWinning(String result) {
        if (result == "Match Withdraw")
            tvTitle.setText(result);
        else {
            playerStatus = result;
            tvTitle.setText(playerStatus + " Wins");
            if (playerStatus == "X")
                tvScoreX.setText(++xResult + "");
            else
                tvScoreO.setText(++oResult + "");
            CommonConfetti.rainingConfetti(clRoot, new int[]{Color.BLACK, Color.RED, Color.GREEN, Color.MAGENTA})
                    .oneShot();

        }
        disabledButtons();

    }

    private void disabledButtons() {
        for (int i = 0; i < 9; i++)
            button[i].setEnabled(false);
    }

    private void tass() {

        tvTitle.setText("Waiting for tass");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tass = (float) Math.random();
                if (tass > .5) playerStatus = "X";
                tvTitle.setText(playerStatus + "  Turn");

            }
        }, 1200);


    }

    private void playAgain() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tass();
                clickCount = 0;
                for (int i = 0; i < 9; i++) {
                    button[i].setEnabled(true);
                    button[i].setText("");
                    board[i] = "";
                }


            }
        });
    }


}