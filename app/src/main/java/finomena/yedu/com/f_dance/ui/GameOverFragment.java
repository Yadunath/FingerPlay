package finomena.yedu.com.f_dance.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import finomena.yedu.com.f_dance.R;
import finomena.yedu.com.f_dance.constants.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameOverFragment extends Fragment implements View.OnClickListener {

    TextView mTextView;
    Button mButton;
    private int noOfTiles;

    public GameOverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_over, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView=(TextView)view.findViewById(R.id.textView3);
        mButton=(Button)view.findViewById(R.id.button);
        mButton.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle=getArguments();
        noOfTiles=bundle.getInt(Constants.PUTEXTRA_TILES,3);
        String winner=bundle.getString("WINNER");
        mTextView.setText(winner+ " Wins");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button:
                GameFragment gameFragment=new GameFragment();
                Bundle bundle=new Bundle();
                bundle.putInt(Constants.PUTEXTRA_TILES,noOfTiles);
                gameFragment.setArguments(bundle);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_game,gameFragment).commit();
                break;
        }
    }
}
