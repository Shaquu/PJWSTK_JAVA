/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.utils;

import io.github.shaquu.memory.game.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomImageName {

    private static List<String> randomList;
    private static Random random;

    public RandomImageName(int rows, int cols){
        randomList = new ArrayList<>();
        random = new Random();

        int size = rows * cols / 2;

        for(int i = 0; i < size; i++){
            randomList.add(Main.cardImageManager.getNameAt(i));
        }
        for(int i = 0; i < size; i++){
            randomList.add(Main.cardImageManager.getNameAt(i));
        }
    }

    public static String get(){
        int randomIndex = random.nextInt(randomList.size());
        String name = randomList.get(randomIndex);
        randomList.remove(randomIndex);
        return name;
    }
}
