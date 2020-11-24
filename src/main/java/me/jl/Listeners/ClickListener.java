package me.jl.Listeners;

import me.jl.Utils.AutoClicker;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import java.awt.event.MouseEvent;

public class ClickListener implements NativeMouseInputListener {

    private String inputType;

    public volatile static boolean mouseDown;
    public volatile static int doubleRelease;

    public ClickListener() {



    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {



    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

        AutoClicker getKeybind = new AutoClicker();

        if (nativeMouseEvent.getButton() == MouseEvent.BUTTON1 && getKeybind.keyBind == true) {

            mouseDown = true;
            doubleRelease++;

            if (doubleRelease < 0) {

                mouseDown = false;

            }

            //System.out.println("MouseDown");

        }

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

        AutoClicker getKeybind = new AutoClicker();

        if (nativeMouseEvent.getButton() == MouseEvent.BUTTON1 && getKeybind.keyBind == true) {

            mouseDown = false;
            doubleRelease--;

            if (doubleRelease < 0) {

                mouseDown = false;

            }

            //System.out.println("MouseUp");

        }

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }

}
