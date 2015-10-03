package com.laststandstudio.java.engine.src.images;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

/**
 * Created by joshu on 8/25/2015.
 */
public class TextureManager {

    private static HashMap<String, Texture> textureMap = new HashMap<>();

    public static void registerTexture(String key, URI path) throws IOException {
        textureMap.put(key, new Texture(path));
    }
}
