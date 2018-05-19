package io.github.shaquu.memory.game.utils;

import javafx.scene.image.Image;

import java.io.File;

public class ResourceManager {

    public File get(String path, String fileName, String fileExtension){
        return new File(getClass().getResource("/" + path + fileName + "." + fileExtension).getFile());
    }

    public ConfigFile getConfig(String configName){
        return new ConfigFile(get("screen/", configName, "config"));
    }

    public Image getImage(String imageName){
        return new Image("/image/" + imageName + ".png");
    }

}
