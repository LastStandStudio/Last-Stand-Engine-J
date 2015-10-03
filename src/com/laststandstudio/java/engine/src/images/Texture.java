package com.laststandstudio.java.engine.src.images;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    public final int target = GL_TEXTURE_2D;
    public final int id;
    public final int width;
    public final int height;

    public static final int LINEAR = GL_LINEAR;
    public static final int NEAREST = GL_NEAREST;

    public static final int CLAMP = GL_CLAMP;
    public static final int CLAMP_TO_EDGE = GL12.GL_CLAMP_TO_EDGE;
    public static final int REPEAT = GL_REPEAT;

    public Texture(URI pngRef) throws IOException {
        this(pngRef, GL_NEAREST);
    }

    public Texture(URI pngRef, int filter) throws IOException {
        this(pngRef, filter, GL12.GL_CLAMP_TO_EDGE);
    }

    public Texture(URI pngRef, int filter, int wrap) throws IOException {
        InputStream input = null;
        try {
            //get an InputStream from our URL
            input = pngRef.toURL().openStream();

            //initialize the decoder
            PNGDecoder dec = new PNGDecoder(input);

            //set up image dimensions
            width = dec.getWidth();
            height = dec.getHeight();

            //we are using RGBA, i.e. 4 components or "bytes per pixel"
            final int bpp = 4;

            //create a new byte buffer which will hold our pixel data
            ByteBuffer buf = BufferUtils.createByteBuffer(bpp * width * height);

            //decode the image into the byte buffer, in RGBA format
            dec.decode(buf, width * bpp, PNGDecoder.Format.RGBA);

            //flip the buffer into "read mode" for OpenGL
            buf.flip();

            //enable textures and generate an ID
            glEnable(target);
            id = glGenTextures();

            //bind texture
            bind();

            //setup unpack mode
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            //setup parameters
            glTexParameteri(target, GL_TEXTURE_MIN_FILTER, filter);
            glTexParameteri(target, GL_TEXTURE_MAG_FILTER, filter);
            glTexParameteri(target, GL_TEXTURE_WRAP_S, wrap);
            glTexParameteri(target, GL_TEXTURE_WRAP_T, wrap);

            //pass RGBA data to OpenGL
            glTexImage2D(target, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void bind() {
        glBindTexture(target, id);
    }
}