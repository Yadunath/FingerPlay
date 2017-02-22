package finomena.yedu.com.f_dance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import finomena.yedu.com.f_dance.constants.Constants;
import finomena.yedu.com.f_dance.ui.GameActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mStartGame;
    private EditText mNoOfTilesEdit;
    private int noOfTiles=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartGame=(Button)findViewById(R.id.button2);
        mNoOfTilesEdit=(EditText)findViewById(R.id.editText);
        mStartGame.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button2:
                if (mNoOfTilesEdit.getText().toString()!=null)
                {
                    noOfTiles=Integer.parseInt(mNoOfTilesEdit.getText().toString());
                }

                Intent intent=new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra(Constants.PUTEXTRA_TILES,noOfTiles);
                startActivity(intent);
                break;
        }
    }
}
