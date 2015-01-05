package gr.auth.csd.taskmanager;

import android.os.StatFs;
import android.os.Environment;

/**
 * Created by Steve Laskaridis on 1/5/2015.
 */
public class MemoryQuery {

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


}
