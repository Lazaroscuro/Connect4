package es.games.lazarov.connect4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    C4Game  game = new C4Game();
    Random  rand = new Random();
    /*C4Game.Val  player = C4Game.Val.P1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect4board);
        for(int j = 0; j < C4Game.h; j++)
            for(int i = 0; i < C4Game.w; i++) {
                ImageButton b = (ImageButton) findViewById(getID(i, j));
                b.setOnClickListener(this);
            }
    }

    @Override
    public void onClick(View v){
        if(game.isOver()) {
            reset();
            updateUI();
            return;
        }

        int id = v.getId();
        if(!game.play(C4Game.Val.P1, getColumn(id), getRow(id))) {
            Toast.makeText(this, getResources().getString(R.string.wrong_movement), Toast.LENGTH_SHORT).show();
            return;
        }

        if(game.isOver()) {
            updateUI();
            over();
            return;
        }

        game.playCPU(C4Game.Val.P2);
        updateUI();

        if(game.isOver())
            over();
/*
        if(player.equals(C4Game.Val.P1)){
            player = C4Game.Val.P2;
        }else{
            player = C4Game.Val.P1;
        }
*/
    }

    public void reset(){
        game = new C4Game();
    }

    public void over(){
        C4Game.Val  wins = game.winner();

        if(wins == C4Game.Val.EMPTY){
            int tie_msg;
            tie_msg = (rand.nextInt(3) != 0)?
                    R.string.tie_game:
                    R.string.joshua; /* Easter egg */

            Toast.makeText(this, getResources().getString(tie_msg), Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this
                , String.format(getResources().getString(R.string.winner_player), (wins.equals(C4Game.Val.P1))? 1 : 2)
                , Toast.LENGTH_SHORT).show();
    }

    public void updateUI(){
        for(int j = 0; j < C4Game.h; j++)
            for(int i = 0; i < C4Game.w; i++) {
                ImageButton b = (ImageButton) findViewById(getID(i, j));

                if(game.get(i, j).v().equals(C4Game.Val.P1)){
                    b.setImageResource(R.drawable.c4_button_yellow);
                }else if(game.get(i, j).v().equals(C4Game.Val.P2)){
                    b.setImageResource(R.drawable.c4_button_red);
                }else{
                    b.setImageResource(R.drawable.c4_button);
                }
            }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString("Grid", game.gridToString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString("Grid");
        game = new C4Game(grid);
        updateUI();
    }

    public int getRow(int id){
        switch(id){
            case R.id.checker_0_0:
            case R.id.checker_0_1:
            case R.id.checker_0_2:
            case R.id.checker_0_3:
            case R.id.checker_0_4:
            case R.id.checker_0_5:
            case R.id.checker_0_6:
                return 0;
            case R.id.checker_1_0:
            case R.id.checker_1_1:
            case R.id.checker_1_2:
            case R.id.checker_1_3:
            case R.id.checker_1_4:
            case R.id.checker_1_5:
            case R.id.checker_1_6:
                return 1;
            case R.id.checker_2_0:
            case R.id.checker_2_1:
            case R.id.checker_2_2:
            case R.id.checker_2_3:
            case R.id.checker_2_4:
            case R.id.checker_2_5:
            case R.id.checker_2_6:
                return 2;
            case R.id.checker_3_0:
            case R.id.checker_3_1:
            case R.id.checker_3_2:
            case R.id.checker_3_3:
            case R.id.checker_3_4:
            case R.id.checker_3_5:
            case R.id.checker_3_6:
                return 3;
            case R.id.checker_4_0:
            case R.id.checker_4_1:
            case R.id.checker_4_2:
            case R.id.checker_4_3:
            case R.id.checker_4_4:
            case R.id.checker_4_5:
            case R.id.checker_4_6:
                return 4;
        }
        return  5;
    }

    public int getColumn(int id){
        switch(id){
            case R.id.checker_0_0:
            case R.id.checker_1_0:
            case R.id.checker_2_0:
            case R.id.checker_3_0:
            case R.id.checker_4_0:
            case R.id.checker_5_0:
                return 0;
            case R.id.checker_0_1:
            case R.id.checker_1_1:
            case R.id.checker_2_1:
            case R.id.checker_3_1:
            case R.id.checker_4_1:
            case R.id.checker_5_1:
                return 1;
            case R.id.checker_0_2:
            case R.id.checker_1_2:
            case R.id.checker_2_2:
            case R.id.checker_3_2:
            case R.id.checker_4_2:
            case R.id.checker_5_2:
                return 2;
            case R.id.checker_0_3:
            case R.id.checker_1_3:
            case R.id.checker_2_3:
            case R.id.checker_3_3:
            case R.id.checker_4_3:
            case R.id.checker_5_3:
                return 3;
            case R.id.checker_0_4:
            case R.id.checker_1_4:
            case R.id.checker_2_4:
            case R.id.checker_3_4:
            case R.id.checker_4_4:
            case R.id.checker_5_4:
                return 4;
            case R.id.checker_0_5:
            case R.id.checker_1_5:
            case R.id.checker_2_5:
            case R.id.checker_3_5:
            case R.id.checker_4_5:
            case R.id.checker_5_5:
                return 5;
        }
        return  6;
    }

    public int getID(int i, int j){
        switch(j){
            case 0:
                switch(i){
                    case 0: return R.id.checker_0_0;
                    case 1: return R.id.checker_0_1;
                    case 2: return R.id.checker_0_2;
                    case 3: return R.id.checker_0_3;
                    case 4: return R.id.checker_0_4;
                    case 5: return R.id.checker_0_5;
                    case 6: return R.id.checker_0_6;
                }
            case 1:
                switch(i){
                    case 0: return R.id.checker_1_0;
                    case 1: return R.id.checker_1_1;
                    case 2: return R.id.checker_1_2;
                    case 3: return R.id.checker_1_3;
                    case 4: return R.id.checker_1_4;
                    case 5: return R.id.checker_1_5;
                    case 6: return R.id.checker_1_6;
                }
            case 2:
                switch(i){
                    case 0: return R.id.checker_2_0;
                    case 1: return R.id.checker_2_1;
                    case 2: return R.id.checker_2_2;
                    case 3: return R.id.checker_2_3;
                    case 4: return R.id.checker_2_4;
                    case 5: return R.id.checker_2_5;
                    case 6: return R.id.checker_2_6;
                }
            case 3:
                switch(i){
                    case 0: return R.id.checker_3_0;
                    case 1: return R.id.checker_3_1;
                    case 2: return R.id.checker_3_2;
                    case 3: return R.id.checker_3_3;
                    case 4: return R.id.checker_3_4;
                    case 5: return R.id.checker_3_5;
                    case 6: return R.id.checker_3_6;
                }
            case 4:
                switch(i){
                    case 0: return R.id.checker_4_0;
                    case 1: return R.id.checker_4_1;
                    case 2: return R.id.checker_4_2;
                    case 3: return R.id.checker_4_3;
                    case 4: return R.id.checker_4_4;
                    case 5: return R.id.checker_4_5;
                    case 6: return R.id.checker_4_6;
                }
            case 5:
                switch(i){
                    case 0: return R.id.checker_5_0;
                    case 1: return R.id.checker_5_1;
                    case 2: return R.id.checker_5_2;
                    case 3: return R.id.checker_5_3;
                    case 4: return R.id.checker_5_4;
                    case 5: return R.id.checker_5_5;
                    case 6: return R.id.checker_5_6;
                }
        }
        return 0;
    }
}
