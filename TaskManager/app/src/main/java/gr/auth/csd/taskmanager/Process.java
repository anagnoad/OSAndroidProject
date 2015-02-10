package gr.auth.csd.taskmanager;

import android.app.ActivityManager;
import android.os.Debug;

/**
 * Class that holds information about the processes that are active on the device.
 * Created by Steve Laskaridis on 1/5/2015.
 */
public class Process {
    /**
     * An instance of the ActivityManager's RunningAppProcessInfo class, that holds information
     * about the process.
     */
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

    public String toString() {
        return getProcessName()+" ("+getPid()+")";
    }

    public String toLongString(){
        StringBuilder toBeReturned = new StringBuilder();
        toBeReturned.append("Process Name: ");
        toBeReturned.append(getProcessName());
        toBeReturned.append("\n");
        toBeReturned.append("pid: ");
        toBeReturned.append(getPid());
        toBeReturned.append("\n");

        toBeReturned.append("Memory stats:\n");
        toBeReturned.append(" - Total private clean: ");
        toBeReturned.append(getMemoryInfo().getTotalPrivateClean());
        toBeReturned.append("kB\n");
        toBeReturned.append(" - Total private dirty: ");
        toBeReturned.append(getMemoryInfo().getTotalPrivateDirty());
        toBeReturned.append("kB\n");
        toBeReturned.append(" - Total PSS: ");
        toBeReturned.append(getMemoryInfo().getTotalPss());
        toBeReturned.append("kB\n");
        toBeReturned.append(" - Total Shared clean: ");
        toBeReturned.append(getMemoryInfo().getTotalSharedClean());
        toBeReturned.append("kB\n");
        toBeReturned.append(" - Total Shared dirty: ");
        toBeReturned.append(getMemoryInfo().getTotalSharedDirty());
        toBeReturned.append("kB\n");
        toBeReturned.append(" - Total Swappable PSS: ");
        toBeReturned.append(getMemoryInfo().getTotalSwappablePss());
        toBeReturned.append("kB\n");

        return toBeReturned.toString();
    }

}
