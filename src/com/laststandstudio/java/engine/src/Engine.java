package com.laststandstudio.java.engine.src;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


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

    private HashMap<Integer, ArrayList<Renderable>> renderableLayerMap;

    private Engine() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        renderableArrayList = new ArrayList<>();
        renderableLayerMap = new HashMap<>();
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

        RenderUtils.getClearColor();
        glEnable(GL_TEXTURE_2D);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1200, 0, 900, -1, 1);
        glMatrixMode(GL_MODELVIEW);

        while (glfwWindowShouldClose(Engine.getInstance().getWindow()) == GL_FALSE) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


            for (int i = 1; i <= 8; i++) {
                try {
                    renderableLayerMap.get(i).forEach(Renderable::run);
                } catch (Exception e) {
                }
            }

            glfwSwapBuffers(Engine.getInstance().getWindow()); // swap the color buffers
            glfwPollEvents();
        }

    }

    public void addRender(Integer integer, Renderable renderable) {
        if (integer > 8) integer = 8;
        if (integer < 1) integer = 1;
        if (!renderableLayerMap.containsKey(integer)) renderableLayerMap.put(integer, new ArrayList<>());
        renderableLayerMap.get(integer).add(renderable);
    }

    public enum EngineState {
        STARTING, INITIALIZING, RUNNING, PAUSED, STOPPING
    }
}
