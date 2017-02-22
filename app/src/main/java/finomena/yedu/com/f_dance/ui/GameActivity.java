package finomena.yedu.com.f_dance.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import finomena.yedu.com.f_dance.R;
import finomena.yedu.com.f_dance.constants.Constants;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        Intent intent=getIntent();
        int noOfTiles=intent.getIntExtra(Constants.PUTEXTRA_TILES,3);
        GameFragment gameFragment=new GameFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.PUTEXTRA_TILES,noOfTiles);
        gameFragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_game,gameFragment).commit();
    }
}
