package io.github.shaquu.memory.game.utils;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardImageManager {

    private LinkedHashMap<String, String> imageNameList;

    public CardImageManager() throws URISyntaxException, IOException {
        imageNameList = new LinkedHashMap<>();

        URI imageFolder = getClass().getResource("/image/card/").toURI();
        if(new File(imageFolder).isDirectory()){
            Files.walk(Paths.get(imageFolder))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        File file = path.toFile();
                        String fileName = file.getName();

                        Pattern p = Pattern.compile("\\d{3}-(.*?)-card.png");
                        Matcher m = p.matcher(fileName);

                        if (m.find())
                        {
                            String[] data = m.group(1).split("-");
                            String name = "";
                            for(String d : data){
                                name += Character.toUpperCase(d.charAt(0)) + d.substring(1) + " ";
                            }
                            name = name.substring(0, name.length() - 1);
                            imageNameList.put(name, fileName);
                        }
                    });
        }
    }

    public String getNameAt(int index){
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
