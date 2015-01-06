package gr.auth.csd.taskmanager;

import android.app.ActivityManager;
import android.os.Debug;

/**
 * Created by Steve Laskaridis on 1/5/2015.
 */
public class Process {
    private ActivityManager.RunningAppProcessInfo theProcess;
    private Debug.MemoryInfo memoryInfo;

    public Process(ActivityManager.RunningAppProcessInfo theProcess, Debug.MemoryInfo memoryInfo) {
        this.theProcess = theProcess;
        this.memoryInfo = memoryInfo;
    }

    public Process(ActivityManager.RunningAppProcessInfo theProcess) {
        this.theProcess = theProcess;
    }

    public Debug.MemoryInfo getMemoryInfo() {
        return memoryInfo;
    }

    public int getPid(){
        return theProcess.pid;
    }

    public String getProcessName() {
        return theProcess.processName;
    }

    public String toString(){
        return getPid() + " - " + getProcessName();
    }

}
