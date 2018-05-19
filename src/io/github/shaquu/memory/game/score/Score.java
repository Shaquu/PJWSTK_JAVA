/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

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

    String uniq() {
        return uniqName;
    }
}
