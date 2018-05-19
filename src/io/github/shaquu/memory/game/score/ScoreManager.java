/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.score;

import io.github.shaquu.memory.game.utils.GameLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreManager implements Serializable {

    private static HashMap<String, Score> scoreHashMap;

    public ScoreManager(){
        load();
    }

    public void add(Score score){
        if(scoreHashMap.containsKey(score.uniq())){
            Score tempScore = scoreHashMap.get(score.uniq());
            if(tempScore.time > score.time){
                scoreHashMap.put(score.uniq(), score);
            }
        } else {
            scoreHashMap.put(score.uniq(), score);
        }
        save();
    }

    public void save(){
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream("scores",false);
            out = new ObjectOutputStream(fos);
            out.writeObject(scoreHashMap);
            out.close();
            GameLogger.log("Score saved");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void load(){
        FileInputStream fos;
        try {
            fos = new FileInputStream("scores");
            ObjectInputStream oos = new ObjectInputStream(fos);
            //noinspection unchecked
            scoreHashMap = (HashMap<String, Score>) oos.readObject();
            fos.close();
        } catch (IOException | ClassNotFoundException ex) {
            scoreHashMap = new HashMap<>();
        }

        GameLogger.log("Loaded scores : " + scoreHashMap.size());
    }

    public List<Score> getOrderd(){
        List<Score> scores = new ArrayList<>(scoreHashMap.values());

        scores.sort((a, b) -> {
            int sizeA = a.rows * a.cols;
            int sizeB = b.rows * b.cols;
            if (sizeA > sizeB) {
                return -1;
            } else if (sizeA == sizeB) {
                if (a.time < b.time) {
                    return -1;
                }
            }
            return 1;
        });

        return scores;
    }
}
