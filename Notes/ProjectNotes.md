# Notes on Android app


## 1st part

Will use the code of the last part with an additional check of the day.

**Android SQLite**
[http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/](http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/)

## 2nd part
List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() # deprecated in LOLLIPOP
ActivityManager.RunningAppProcessInfo
	* pid
	* processName
ActivityManager.getMemoryInfo(int[] pids)


## 3rd part
### OS kernel
[http://developer.android.com/reference/java/lang/System.html](http://developer.android.com/reference/java/lang/System.html)
System getProperty("os.name")
System getProperty("os.version")

### Device model
[http://developer.android.com/reference/android/os/Build.html](http://developer.android.com/reference/android/os/Build.html)
Build.DEVICE
Build.MANUFACTURER
Build.MODEL
Build.PRODUCT

### Device Uptime
[http://developer.android.com/reference/android/os/SystemClock.html#uptimeMillis%28%29](http://developer.android.com/reference/android/os/SystemClock.html#uptimeMillis%28%29)
long SystemClock.uptimeMillis()

### Device network state and signal
[http://developer.android.com/reference/android/telephony/ServiceState.html](http://developer.android.com/reference/android/telephony/ServiceState.html)
int getState()

[http://developer.android.com/reference/android/telephony/NeighboringCellInfo.html#getNetworkType%28%29](http://developer.android.com/reference/android/telephony/NeighboringCellInfo.html#getNetworkType%28%29)
int NeighboringCellInfo.getNetworkType()

### Device Battery
[http://developer.android.com/reference/android/os/BatteryManager.html](http://developer.android.com/reference/android/os/BatteryManager.html)
int BatteryManager.getIntProperty(BatteryManager.EXTRA_STATUS)


### Device internal/external memory info

[http://stackoverflow.com/questions/4595334/get-free-space-on-internal-memory](http://stackoverflow.
com/questions/4595334/get-free-space-on-internal-memory)

<code>
/*************************************************************************************************
Returns size in bytes.

If you need calculate external memory, change this: 
    StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
to this: 
    StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());        
**************************************************************************************************/
    public long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());   
        long   Total  = ( (long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        return Total;
    }

    public long FreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Free   = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        return Free;
    }

    public long BusyMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());   
        long   Total  = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        long   Free   = (statFs.getAvailableBlocks()   * (long) statFs.getBlockSize());
        long   Busy   = Total - Free;
        return Busy;
    }

</code>