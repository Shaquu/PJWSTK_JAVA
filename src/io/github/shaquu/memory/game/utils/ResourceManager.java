/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.utils;

import javafx.scene.image.Image;

import java.io.File;

public class ResourceManager {

    public File get(String path, String fileName, String fileExtension){
        return new File(getClass().getResource("/" + path + fileName + "." + fileExtension).getFile());
    }

    public Image getImage(String imageName){
        return new Image("/image/" + imageName + ".png");
    }

}
