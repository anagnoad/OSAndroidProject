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


    /*************************************************************************************************
     Returns size in bytes.

     If you need calculate external memory, change this:
     StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
     to this:
     StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
     **************************************************************************************************/
    public static long[] totalMemory()
    {
        long[] total = new long[2];
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs statFsE = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        total[0]  = ( (long) statFs.getBlockCountLong() * (long) statFs.getBlockSizeLong());
        total[1] = ( (long) statFsE.getBlockCountLong() * (long) statFsE.getBlockSizeLong());

        return total;
    }

    public static long[] freeMemory()
    {
        long[] free = new long[2];
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs statFsE = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        free[0]   = (statFs.getAvailableBlocksLong() * (long) statFs.getBlockSizeLong());
        free[1]   = (statFsE.getAvailableBlocksLong() * (long) statFsE.getBlockSizeLong());
        return free;
    }

    public static long[] occupiedMemory()
    {
        long[] total = new long[2];
        long[] free = new long[2];
        long[] occupied = new long[2];
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs statFsE = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        total[0]  = ((long) statFs.getBlockCountLong() * (long) statFs.getBlockSizeLong());
        free[0]   = (statFs.getAvailableBlocksLong()   * (long) statFs.getBlockSizeLong());
        occupied[0]   = total[0] - free[0];
        total[1]  = ((long) statFsE.getBlockCountLong() * (long) statFsE.getBlockSizeLong());
        free[1]   = (statFsE.getAvailableBlocksLong()   * (long) statFsE.getBlockSizeLong());
        occupied[1]   = total[1] - free[1];

        return occupied;
    }

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

    public static HashMap<String,String> getSystemMetrics(Context applicationContext, final DeviceInfoFragment fragment) {
        HashMap<String, String> toBeReturned = new HashMap<>();
        String osName = System.getProperty("os.name");
        toBeReturned.put("OS Name", osName);
        String kernelVersion = System.getProperty("os.version");
        toBeReturned.put("Kernel Version", kernelVersion);
        String modelName = Build.MODEL;
        toBeReturned.put("Model Name", modelName);
        Long upTimeMins = SystemClock.uptimeMillis()/(1000*60);
        toBeReturned.put("UpTime", upTimeMins.toString());
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

        TelephonyManager telMng = (TelephonyManager)applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        telMng.listen(new PhoneStateListener() {
            @Override
            public void onServiceStateChanged (ServiceState serviceState) {
                super.onServiceStateChanged(serviceState);


                switch(serviceState.getState()) {
                    case ServiceState.STATE_EMERGENCY_ONLY:
                        fragment.getSampleTextView().setText("STATE_EMERGENCY_ONLY");
                        break;
                    case ServiceState.STATE_IN_SERVICE:
                        fragment.getSampleTextView().setText("STATE_IN_SERVICE");
                        break;
                    case ServiceState.STATE_OUT_OF_SERVICE:
                        fragment.getSampleTextView().setText("STATE_OUT_OF_SERVICE");
                        break;
                    case ServiceState.STATE_POWER_OFF:
                        fragment.getSampleTextView().setText("STATE_POWER_OFF");
                        break;
                    default:
                        fragment.getSampleTextView().setText("Unknown");
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_SERVICE_STATE);


        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                fragment.getSampleTextView().setText( String.valueOf(level) + "%");
            }
        };
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = applicationContext.registerReceiver(batteryReceiver,ifilter);
        return toBeReturned;
    }

}
