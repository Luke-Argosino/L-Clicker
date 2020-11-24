package me.jl.Utils;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

public class User32 {

    static {
        Native.register(NativeLibrary.getInstance("User32", W32APIOptions.UNICODE_OPTIONS));
    }

    public static native void mouse_event(long dwFlags, int dx, int dy, int dwData, long dwExtraInfo);

}
