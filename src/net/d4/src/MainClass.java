package net.d4.src;

import com.laststandstudio.java.engine.src.Engine;
import com.laststandstudio.java.engine.src.RenderUtils;
import com.laststandstudio.java.engine.src.images.Texture;
import com.laststandstudio.java.engine.src.world.World;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by joshu on 8/21/2015.
 */
public class MainClass {

    static Texture texture;
    static int i = 0, j = 0;

    MainClass() {

        try {
            Engine.getInstance().init(1200, 900);

            World world = new World();
            world.renderWorld();
            try {
                texture = new Texture(new File("Z:\\xampp\\htdocs\\0.png").toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Engine.getInstance().addRender(8, () -> {
                glBegin(GL_QUADS);
                {
                    RenderUtils.setColor(new Color(127,127,255));
                    glVertex2f(400, 400);
                    glVertex2f(400 + 200, 400);
                    glVertex2f(400 + 200, 400 + 200);
                    glVertex2f(400, 400 + 200);
                }
                glEnd();
            });

            Engine.getInstance().addRender(3, () -> {
                    RenderUtils.renderSprite(texture, 10, 10, texture.width, texture.height);
            });
            RenderUtils.setClearColor(Color.BLACK);
            Engine.getInstance().start();

            glfwDestroyWindow(Engine.getInstance().getWindow());
            Engine.getInstance().getKeyCallback().release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            Engine.getInstance().getErrorCallback().release();
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println(System.getProperty("java.library.path"));
        new MainClass();
    }

}
