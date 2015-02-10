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

    /**
     * An instance of the Debug's MemoryInfo class, that holds information about the memory that
     * each process has allocated.
     */
    private Debug.MemoryInfo memoryInfo;

    /**
     * Ctor that needs both this class's fields as parameters.
     * @param theProcess
     * @param memoryInfo
     */
    public Process(ActivityManager.RunningAppProcessInfo theProcess, Debug.MemoryInfo memoryInfo) {
        this.theProcess = theProcess;
        this.memoryInfo = memoryInfo;
    }

    /**
     * Ctor that needs only the RunningAppProcessInfo field as a parameter.
     * @param theProcess
     */
    public Process(ActivityManager.RunningAppProcessInfo theProcess) {
        this.theProcess = theProcess;
    }

    /**
     * Getter for the MemoryInfo of the process.
     * @return the memory info of the process.
     */
    public Debug.MemoryInfo getMemoryInfo() {
        return memoryInfo;
    }

    /**
     * Abstraction, in order to get just the PID of the process (held in the RunningAppProcessInfo field)
     * @return the pid of the process.
     */
    public int getPid(){
        return theProcess.pid;
    }
    /**
     * Abstraction, in order to get just the name of the process (held in the RunningAppProcessInfo field)
     * @return the name of the process.
     */
    public String getProcessName() {
        return theProcess.processName;
    }

    public String toString() {
        return getProcessName()+" ("+getPid()+")";
    }

    /**
     * Method that returns a more verbose version of the serialized "Process" instance.
     * @return
     */
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
