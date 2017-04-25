package es.games.lazarov.connect4;

import java.util.Random;

public class C4Game {
    public static enum Val {
        EMPTY,
        P1,
        P2
    }

    public class Checker{
        private Val val = Val.EMPTY;
        public Val v() { return val; }
        private void set(Val player) { val = player; }
    }

    public static int w = 7;
    public static int h = 6;

    private Random  rand = new Random();

    private Checker[][] tablero = new Checker[h][w];

    public C4Game(){
        for(Checker[] c: tablero)
            for(int i = 0; i < w; i++)
                c[i] = new Checker();
    }

    public C4Game(String str){
        this();
        for(int j = 0; j < h; j++)
        for(int i = 0; i < w; i++)
            get(i, j).set(Val.values()[str.charAt(j * w + i) - '0']);
    }

    public Checker get(int i, int j){
        return  tablero[j][i];
    }

    public boolean isAvailable(int i, int j){
        if(!get(i, j).v().equals(Val.EMPTY)) {
            return  false;
        }
        if(++j < h && get(i, j).v().equals(Val.EMPTY)) {
            return  false;
        }
        return true;
    }

    public boolean playCPU(Val player)
    {
        int column = rand.nextInt(this.w);

        for(;; column++, column %= this.w) {
            for (int j = this.h - 1; j >= 0; j--) {
                if (!isAvailable(column, j))
                    continue;
                get(column, j).set(player);
                return true;
            }
        }
    }

    public boolean play(Val player, int i, int j){
        if(player.equals(Val.EMPTY) || !isAvailable(i, j))
            return false;

        get(i, j).set(player);
        return  true;
    }

    public int fourInLineStart(int dt)
    {
        if(dt < 0)
            return -1 * (4 - 1) * dt;

        return 0;
    }

    /* Note that the range is [start-end) */
    public int fourInLineEnd(int dt, int size)
    {
        if(dt > 0)
            return size - (4 - 1) * dt;

        return size;
    }

    public boolean fourInVec(int xdt, int ydt, Val player)
    {
        int x = fourInLineStart(xdt);
        int w = fourInLineEnd(xdt, this.w);
        int y = fourInLineStart(ydt);
        int h = fourInLineEnd(ydt, this.h);

        for(int j = y; j < h; j++)
        for(int i = x; i < w; i++) {
            boolean r = true;
            for(int c = 0; c < 4; c++) {
                if (!get(i + c * xdt, j + c * ydt).v().equals(player)) {
                    r = false;
                    break;
                }
            }
            if(r)
                return true;
        }
        return false;
    }

    public boolean isOver()
    {
        if(fourInVec(0, 1, Val.P1) || fourInVec(1, 0, Val.P1))
            return true;

        if(fourInVec(1, 1, Val.P1) || fourInVec(1, -1, Val.P1))
            return true;

        if(fourInVec(0, 1, Val.P2) || fourInVec(1, 0, Val.P2))
            return true;

        if(fourInVec(1, 1, Val.P2) || fourInVec(1, -1, Val.P2))
            return true;

        for(int j = 0; j < h; j++)
        for(int i = 0; i < w; i++)
            if(get(i, j).v().equals(Val.EMPTY))
                return false;

        return true;
    }

    public C4Game.Val winner(){
        if(fourInVec(0, 1, Val.P1) || fourInVec(1, 0, Val.P1))
            return Val.P1;

        if(fourInVec(1, 1, Val.P1) || fourInVec(1, -1, Val.P1))
            return Val.P1;

        if(fourInVec(0, 1, Val.P2) || fourInVec(1, 0, Val.P2))
            return Val.P2;

        if(fourInVec(1, 1, Val.P2) || fourInVec(1, -1, Val.P2))
            return Val.P2;

        return Val.EMPTY;
    }

    public String gridToString()
    {
        String str = "";

        for(int j = 0; j < h; j++)
        for(int i = 0; i < w; i++)
            str += get(i, j).v().ordinal();

        return str;
    }
}