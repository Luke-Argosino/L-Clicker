package me.jl.Listeners;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {

    public volatile static boolean toggled = false;

    public volatile static String keybind = "P";

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals(keybind)) {

            toggled ^= true;

            //System.out.println("Keybind " + keybind + " " + toggled);

        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    public void updateKey(String key) {

        keybind = key;

    }

}
