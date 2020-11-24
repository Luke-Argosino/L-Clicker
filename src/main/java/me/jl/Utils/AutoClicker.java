package me.jl.Utils;

import com.sun.jna.platform.win32.User32;
import me.jl.Listeners.ClickListener;
import me.jl.Listeners.KeyListener;
import me.jl.Main;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;

import static java.lang.Thread.sleep;


public class AutoClicker implements Runnable {

    public volatile static Robot rbot;

    public static int button = 1;

    public static double minimumCPS = 8.12378123;
    public static double maximumCPS = 13.1239812;
    public static boolean spikes = false;
    public static double spikeStrength = 0;

    public static int count = 0;

    public volatile static boolean mouseDown;
    public volatile static boolean rightMouseDown;
    public volatile static boolean keyBind;

    @Override
    public void run() {

        LogManager.getLogManager().reset();
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);

        try {

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeMouseListener(new ClickListener());
            GlobalScreen.addNativeKeyListener(new KeyListener());


        } catch (NativeHookException e) {

            e.printStackTrace();

        }

        while (true) {

            ClickListener getMouseDown = new ClickListener();
            KeyListener getKeyBind = new KeyListener();

            this.mouseDown = getMouseDown.mouseDown;
            this.keyBind = getKeyBind.toggled;

            Main getBoolean = new Main();

            if (getBoolean.windowOnly == true && !getWindow().contains("Minecraft")) {

                try {

                    sleep(500);

                } catch (java.lang.InterruptedException e) {

                    e.printStackTrace();

                }

                continue;

            }

            this.mouseDown = getMouseDown.mouseDown;

            if (!(mouseDown == true) || !(keyBind == true)) {

                try {

                    Thread.sleep(250);

                } catch (java.lang.InterruptedException e) { e.printStackTrace(); }

            }

            else if (getMouseDown.doubleRelease == 1) {

                double upDownD = ((Math.random()*15) + 70);
                long upDown = (long) upDownD;

                leftClickUp();

                try {

                    Thread.sleep(upDown);

                } catch (java.lang.InterruptedException e) {  e.printStackTrace(); }

                leftClickDown();

                mouseDown = getMouseDown.mouseDown;

                try {

                    sleep(getClickDelay(minimumCPS, maximumCPS));

                } catch (InterruptedException e) { e.printStackTrace();}

            }

        }

    }

    public static void leftClickUp() {

        if (mouseDown == true) {

            me.jl.Utils.User32 click = new me.jl.Utils.User32();
            click.mouse_event(0x0004 /*up*/, 0, 0, 0, 0);

        }

    }

    public static void leftClickDown() {

        if (mouseDown == true) {

            me.jl.Utils.User32 click = new me.jl.Utils.User32();
            click.mouse_event(0x0002 /*down*/, 0, 0, 0, 0);

        }

    }

    public static long getClickDelay(double minCPS, double maxCPS) {

        double maxMs = 1000 / minCPS;
        double minMs = 1000 / maxCPS;

        //System.out.println("Max " + maxMs);
        //System.out.println("Min " + minMs);

        double difference = maxMs - minMs;

        double randDifference = (Math.random()*difference);

        double randomDouble = minMs + randDifference;

        long delay = (long) randomDouble;

        if (spikes == true) {

            System.out.println("Spikes true");

            count++;

            if (count > 72) {

                count = 1;

            }

            double a = 4.972386 * spikeStrength;
            double b = 5.43267 * spikeStrength;
            double c = 3.45233 * spikeStrength;

            int d = (int) (Math.random()*24) + 1;
            int e = (int) (Math.random()*12) + 1;
            int f = (int) (Math.random()*18) + 1;

            System.out.println("Spike Strength :: " + spikeStrength);

            if (count % d == 0) {

                delay -= (Math.random()*a);
                //System.out.println("Subtracted");

            } else if (count % e == 0) {

                delay += (Math.random()*b);
                //System.out.println("Added");

            } else if (count % f == 0) {

                delay -= (Math.random()*c);
                //System.out.println("Subtracted");

            }

            //System.out.println("Delay :: " + delay);

        }

        delay -= 78;

        while (delay < 0) {

            delay += 5;

        }

        return delay;

    }

    public static void updateMinCPS(double cps) {

        minimumCPS = cps;
        //System.out.println("Min CPS Updated to :: " + minimumCPS);

    }

    public static void updateMaxCPS(double cps) {

        maximumCPS = cps;
        //System.out.println("Max CPS Updated to :: " + maximumCPS);

    }

    public String getWindow() {

        char[] buffer = new char[1024 * 2];
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, 1024);

        return Native.toString(buffer);

    }

}
