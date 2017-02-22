package finomena.yedu.com.f_dance.ui;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import finomena.yedu.com.f_dance.R;
import finomena.yedu.com.f_dance.constants.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnTouchListener,View.OnLongClickListener {

    private String TAG=GameFragment.class.getName();
    private TableLayout mTableLayout;
    private TextView mTextViewUser;
    private int noofTiles=3;
    private ArrayList<Integer> playerOneList=new ArrayList<>();
    private ArrayList<Integer> playerTwoList=new ArrayList<>();
    private int higlightedButtonId;
    private int playerId=0;
    ArrayList<Integer> list;
    private MediaPlayer mediaPlayer;
    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTableLayout=(TableLayout)view.findViewById(R.id.game_layout);
        mTextViewUser=(TextView)view.findViewById(R.id.textView);
        mTextViewUser.setText(" PLAYER ONE - LONG PRESS  BUTTON ");
        Bundle bundle=getArguments();
        noofTiles=bundle.getInt(Constants.PUTEXTRA_TILES);
        list = new ArrayList<>();
        for (int i = 0; i < noofTiles*noofTiles; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.bgmusic);
        mediaPlayer.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int buttonId=0;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int deviceWidth=displaymetrics.widthPixels-300;
        int deviceHeight=displaymetrics.heightPixels/2;
        int buttonWidth=deviceWidth/(noofTiles)-20;
        int buttonHeight=deviceHeight/(noofTiles)-30;
        for (int i = 0; i <noofTiles ; i++) {
            TableRow tableRow=new TableRow(getActivity());
            for (int j = 0; j <noofTiles ; j++) {
                Button button=new Button(getActivity());
                button.setId(buttonId);
                button.setOnTouchListener(this);
                button.setOnLongClickListener(this);
                tableRow.addView(button);
                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        buttonWidth,buttonHeight);
                params.setMargins(20, 20, 20, 30);
                button.setLayoutParams(params);
                if (j<noofTiles-1)
                {
                    buttonId++;
                }
            }
            mTableLayout.addView(tableRow);
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (ViewGroup.LayoutParams.WRAP_CONTENT,buttonHeight);

            int leftMargin=10;
            int topMargin=25;
            int rightMargin=10;
            int bottomMargin=25;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            buttonId++;
        }
        highlightButton();
    }
    @Override
    public boolean onLongClick(View view) {
        final int buttonId=view.getId();
        if (buttonId==higlightedButtonId)
        {
            if (playerId==0)
            {
                playerOneList.add(buttonId);
                playerId=1;
                mTextViewUser.setText(" PLAYER TWO - LONG PRESS  BUTTON ");
                mTextViewUser.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPlayerTwo));
            }
            else
            {
                playerTwoList.add(buttonId);
                playerId=0;
                mTextViewUser.setText(" PLAYER ONE - LONG PRESS  BUTTON ");
                mTextViewUser.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPlayerOne));

            }
            highlightButton();
        }
        else {
            if (playerId == 0) {
                gameOver("Player Two");
            } else {
                gameOver("Player One");

            }
        }
        return true;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int action = motionEvent.getAction();

        final int buttonId=view.getId();
        switch (action) {
            case MotionEvent.ACTION_UP:

                Log.e(TAG,""+playerOneList+" "+buttonId+playerTwoList);
                if (playerOneList.contains(buttonId))
                {
                    gameOver("Player Two");
                }
                else
                {
                    gameOver("Player One");
                }
                break;
        }
        return false;
    }


    public void gameOver(String winnerName)
    {
        mediaPlayer.stop();
        Bundle bundle=new Bundle();
        bundle.putString("WINNER",""+winnerName);
        bundle.putInt(Constants.PUTEXTRA_TILES,noofTiles);
        GameOverFragment gameOverFragment=new GameOverFragment();
        gameOverFragment.setArguments(bundle);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_game,gameOverFragment).commit();
    }
    /*
    * highlight button
    * */
    public void highlightButton()
    {
        higlightedButtonId=generateRandomNumber();
        Button button =(Button) getActivity().findViewById(higlightedButtonId);
        if (playerId==0)
        {
            button.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPlayerOne));
        }
        else
        {
            button.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPlayerTwo));

        }

    }
    /*
    * generate random number between two numbers
    * */
    public int generateRandomNumber()
    {
        int result=list.get(0);
        if (list.size()>1)
        {
            list.remove(0);
        }
        return result;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
    }
}
