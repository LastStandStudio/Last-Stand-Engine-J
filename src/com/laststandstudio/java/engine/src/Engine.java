package com.laststandstudio.java.engine.src;

import net.d4.src.Renderable;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


/**
 * Created by joshu on 8/21/2015.
 */
public class Engine {

    private static Engine ourInstance = new Engine();

    public static Engine getInstance() {
        return ourInstance;
    }

    private EngineState currentEngineState = EngineState.STARTING;

    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    // The window handle
    private long window;

    private ArrayList<Renderable> renderableArrayList;


    private Engine() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        renderableArrayList = new ArrayList<>();
    }

    public long getWindow() {
        return window;
    }

    public GLFWErrorCallback getErrorCallback() {
        return errorCallback;
    }

    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public void init(int WIDTH, int HEIGHT) {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL11.GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });

        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    public EngineState getCurrentEngineState() {
        return currentEngineState;
    }

    public void setCurrentEngineState(EngineState currentEngineState) {
        this.currentEngineState = currentEngineState;
    }

    public void start() {
        //        GL.createCapabilities(); // valid for latest build
        GLContext.createFromCurrent(); // use this line instead with the 3.0.0a build

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 800, 0, 600, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        while (glfwWindowShouldClose(Engine.getInstance().getWindow()) == GL_FALSE) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


            renderableArrayList.forEach(renderable -> renderable.run());

            glfwSwapBuffers(Engine.getInstance().getWindow()); // swap the color buffers
            glfwPollEvents();
        }

    }

    public void addRender(Renderable renderable) {
        renderableArrayList.add(renderable);
    }

    public enum EngineState {
        STARTING, INITIALIZING, RUNNING, PAUSED, STOPPING
    }
}
