/*
 * Copyright (c) 2018 Tadeusz Wyrzykowski (tadev3@gmail.com)
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 */

package io.github.shaquu.memory.game.utils;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardImageManager {

    private LinkedHashMap<String, String> imageNameList;

    public CardImageManager() throws URISyntaxException, IOException {
        imageNameList = new LinkedHashMap<>();

        URI uri = CardImageManager.class.getResource("/image/card/").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            myPath = fileSystem.getPath("/resources");
        } else {
            myPath = Paths.get(uri);
        }

        Files.walk(myPath, 1)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    File file = path.toFile();
                    String fileName = file.getName();

                    Pattern p = Pattern.compile("\\d{3}-(.*?)-card.png");
                    Matcher m = p.matcher(fileName);

                    if (m.find()) {
                        String[] data = m.group(1).split("-");
                        StringBuilder name = new StringBuilder();
                        for (String d : data) {
                            name.append(Character.toUpperCase(d.charAt(0))).append(d.substring(1)).append(" ");
                        }
                        name = new StringBuilder(name.substring(0, name.length() - 1));
                        imageNameList.put(name.toString(), fileName);
                    }
                });

        GameLogger.log("Loaded images : " + imageNameList.size());
    }

    String getNameAt(int index) {
        int find = 0;
        for(String name : imageNameList.keySet()){
            if(find == index){
                return name;
            } else find++;
        }
        return null;
    }

    public Image get(String name){
        String fileName = imageNameList.get(name);
        return new Image("/image/card/" + fileName);
    }
}
