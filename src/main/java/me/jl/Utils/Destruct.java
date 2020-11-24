package me.jl.Utils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import javafx.application.Platform;
import me.jl.Main;
import org.jnativehook.GlobalScreen;

import java.io.File;
import java.net.URISyntaxException;

public class Destruct {

    public void Destroy() {

        try {

            //WinNT.HANDLE process = Kernel32.INSTANCE.GetModuleHandle("JNativeHook-2.1.0.dll");
            //Pointer pointer = process.getPointer();
            //Kernel32.INSTANCE.UnmapViewOfFile(pointer);
            GlobalScreen.unregisterNativeHook();
            WinNT.HANDLE handle = Kernel32.INSTANCE.GetCurrentProcess();
            Kernel32.INSTANCE.CloseHandle(handle);
            deleteThree();
            deleteOne();
            deleteTwo("jnativehook");
            deleteTwo("jna");
            deleteTwo("javanativeaccess");
            String filePath = getFile();
            System.out.println(filePath);
            Runtime.getRuntime().exec("cmd /c cd C:\\Windows\\System32 && ping localhost -n 3 > nul && del " + filePath);
            System.gc();
            System.exit(0);

        } catch (Throwable e) {

            e.printStackTrace();

        }

    }

    private void deleteOne() throws URISyntaxException {
        final File recent = new File(String.valueOf(System.getenv("APPDATA")) + "/Microsoft/Windows/Recent");
        final File executableDir = new File(AutoClicker.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
        if (!recent.exists() || !executableDir.exists()) {
            return;
        }
        File[] listFiles;
        for (int length = (listFiles = recent.listFiles()).length, i = 0; i < length; ++i) {
            final File b = listFiles[i];
            File[] listFiles2;
            for (int length2 = (listFiles2 = executableDir.listFiles()).length, j = 0; j < length2; ++j) {
                final File a = listFiles2[j];
                if (b.getName().startsWith(a.getName())) {
                    b.delete();
                }
            }
            if (b.getName().startsWith(executableDir.getName())) {
                b.delete();
            }
        }
    }

    private void deleteTwo(String name) {
        final File temp = new File(System.getenv("TEMP"));
        if (!temp.exists() || !temp.isDirectory()) {
            return;
        }
        File[] listFiles;
        for (int length = (listFiles = temp.listFiles()).length, i = 0; i < length; ++i) {
            final File a = listFiles[i];
            if (a.getName().toLowerCase().startsWith(name)) {
                a.delete();
            }
        }
    }

    private void deleteThree() {

        File prePath = new File("C:\\Windows\\Prefetch\\");

        for (File file : prePath.listFiles()) {

            String name = file.toString().toLowerCase();

            if (name.contains("java")) file.delete();

        }

    }

    private String getFile() {

        File path = new File("").getAbsoluteFile();
        String name = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
        String filePath = "\"" + path.toString() + "\\" + name + "\"";

        return filePath;

    }

}
