package io.github.shaquu.memory.game.score;

import java.io.Serializable;

public class Score implements Serializable {

    static final long serialVersionUID = -7588980448693010399L;

    public String playerName;
    public int rows;
    public int cols;
    public long time;

    private String uniqName;

    public Score(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
    }

    public void update(long time){
        this.time = time;
    }

    public void update(String playerName){
        this.playerName = playerName;
    }

    public void finish(){
        this.uniqName = playerName + ":" + rows + ":" + cols;
    }

    public String uniq() {
        return uniqName;
    }
}
