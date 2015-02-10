package gr.auth.csd.taskmanager;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Steve Laskaridis on 1/5/2015.
 */
public class SystemQuery {


    /**
     * Method that returns a list of Process's instances, holding information about all process
     * that are currently active in the device.
     * @param applicationContext the application context
     * @return the list of processes.
     */
    public static List<Process> getAllProcesses(Context applicationContext) {
        ArrayList<Process> toBeReturned = new ArrayList<>();
        ActivityManager am = (ActivityManager)applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> allProcesses = am.getRunningAppProcesses();

        int[] pids = new int[allProcesses.size()];
        int counter = 0;
        for (ActivityManager.RunningAppProcessInfo p : allProcesses) {
            pids[counter++] = p.pid;
        }

        Debug.MemoryInfo[] mi = am.getProcessMemoryInfo(pids);

        for(int i=0;i<counter;++i) {
            toBeReturned.add(new Process(allProcesses.get(i), mi[i]));
        }

        return toBeReturned;
    }

    /**
     * Method that returns all device information that is needed:
     * OS Name, Kernel Version, Model Name, UpTime, Network Type, Network State, Battery and Available Memory.
     * This information is stored in a HashMap and returned as is.
     *
     * The DeviceInfoFragment is needed as some of the information needed is not available instantly, and
     * a listener is required in order to "receive" the information.
     * @param applicationContext the application context
     * @param fragment the fragment of DeviceInfo
     * @return
     */
    public static HashMap<String,String> getSystemMetrics(Context applicationContext, final DeviceInfoFragment fragment) {
        HashMap<String, String> toBeReturned = new HashMap<>();
        String osName = Build.VERSION.RELEASE;//System.getProperty("os.name");
        toBeReturned.put("OS Name", osName);
        String kernelVersion = System.getProperty("os.version");
        toBeReturned.put("Kernel Version", kernelVersion);
        String modelName = Build.MODEL;
        toBeReturned.put("Model Name", modelName);
        Long upTimeMins = SystemClock.uptimeMillis()/(1000*60);
        toBeReturned.put("UpTime", upTimeMins.toString()+" mins");
        TelephonyManager tm = (TelephonyManager)applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = tm.getNetworkType();
        String networkTypeString="";
        switch(networkType)
        {
            case TelephonyManager.NETWORK_TYPE_EDGE:
                networkTypeString  = "EDGE";
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                networkTypeString  = "GPRS";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                networkTypeString  = "UMTS";
                break;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                networkTypeString  = "CDMA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                networkTypeString  = "HSDPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                networkTypeString  = "HSPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                networkTypeString  = "HSPAP";
                break;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                networkTypeString  = "HSUPA";
                break;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                networkTypeString  = "IDEN";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                networkTypeString  = "LTE";
                break;
        }
        toBeReturned.put("Network Type", networkTypeString);
        toBeReturned.put("Network State", "N/A");
        toBeReturned.put("Battery", "N/A");

        long[] freeMemory = MemoryQuery.freeMemory();
        freeMemory[0] = freeMemory[0]/1024/1024;
        freeMemory[1] = freeMemory[1]/1024/1024;
        toBeReturned.put("Internal Memory Available", String.valueOf(freeMemory[0]) + " MB");
        toBeReturned.put("External Memory Available", String.valueOf(freeMemory[1]) + " MB");

      //  HashMap<String,String> test = (HashMap<String,String>)sampleListView.getAdapter().getItem(0);
        TelephonyManager telMng = (TelephonyManager)applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        telMng.listen(new PhoneStateListener() {
            @Override
            public void onServiceStateChanged (ServiceState serviceState) {
                super.onServiceStateChanged(serviceState);


                switch(serviceState.getState()) {
                    case ServiceState.STATE_EMERGENCY_ONLY:
                        fragment.refreshValue("Network State", "STATE_EMERGENCY_ONLY");
                        break;
                    case ServiceState.STATE_IN_SERVICE:
                        fragment.refreshValue("Network State", "STATE_IN_SERVICE");
                        break;
                    case ServiceState.STATE_OUT_OF_SERVICE:
                        fragment.refreshValue("Network State", "STATE_OUT_OF_SERVICE");
                        break;
                    case ServiceState.STATE_POWER_OFF:
                        fragment.refreshValue("Network State", "STATE_POWER_OFF");
                        break;
                    default:
                        fragment.refreshValue("Network State", "UNKNOWN");
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_SERVICE_STATE);


        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                fragment.refreshValue("Battery", String.valueOf(level) + "%");
            }
        };
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = applicationContext.registerReceiver(batteryReceiver,ifilter);
        return toBeReturned;
    }

}
