package gr.auth.csd.taskmanager;

import android.os.Build;
import android.os.StatFs;
import android.os.Environment;

/**
 * Helper static methods Class for acquiring internal and external memory info.
 */
public class MemoryQuery {

    /*************************************************************************************************
     Returns size in bytes.

     If you need calculate external memory, change this:
     StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
     to this:
     StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
     **************************************************************************************************/
    /**
     * Method that returns the total available memory of the device in bytes.
     * @return a long array, with two integers. The first one is the internal memory, while the second one
     * is the external one.
     */
    public static long[] totalMemory()
    {
        long[] total = new long[2];
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs statFsE = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        if(Build.VERSION.SDK_INT>18) {
            total[0] = ((long) statFs.getBlockCountLong() * (long) statFs.getBlockSizeLong());
            total[1] = ((long) statFsE.getBlockCountLong() * (long) statFsE.getBlockSizeLong());
        }
        else
        {
            total[0] = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
            total[1] = ((long) statFsE.getBlockCount() * (long) statFsE.getBlockSize());
        }
        return total;
    }
    /**
     * Method that returns the free memory of the device in bytes.
     * @return a long array, with two integers. The first one is the internal memory, while the second one
     * is the external one.
     */
    public static long[] freeMemory()
    {
        long[] free = new long[2];
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs statFsE = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        if(Build.VERSION.SDK_INT>18) {
            free[0] = (statFs.getAvailableBlocksLong() * (long) statFs.getBlockSizeLong());
            free[1] = (statFsE.getAvailableBlocksLong() * (long) statFsE.getBlockSizeLong());
        }
        else {
            free[0] = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
            free[1] = (statFsE.getAvailableBlocks() * (long) statFsE.getBlockSize());
        }
        return free;
    }
    /**
     * Method that returns the occupied memory of the device in bytes.
     * @return a long array, with two integers. The first one is the internal memory, while the second one
     * is the external one.
     */
    public static long[] occupiedMemory()
    {
        long[] total = new long[2];
        long[] free = new long[2];
        long[] occupied = new long[2];
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs statFsE = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        if(Build.VERSION.SDK_INT>18) {
            total[0] = ((long) statFs.getBlockCountLong() * (long) statFs.getBlockSizeLong());
            free[0] = (statFs.getAvailableBlocksLong() * (long) statFs.getBlockSizeLong());
        }
        else
        {
            total[0] = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
            free[0] = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        }
        occupied[0]   = total[0] - free[0];
        if(Build.VERSION.SDK_INT>18) {
            total[1] = ((long) statFsE.getBlockCountLong() * (long) statFsE.getBlockSizeLong());
            free[1] = (statFsE.getAvailableBlocksLong() * (long) statFsE.getBlockSizeLong());
        }
        else
        {
            total[1] = ((long) statFsE.getBlockCount() * (long) statFsE.getBlockSize());
            free[1] = (statFsE.getAvailableBlocks() * (long) statFsE.getBlockSize());
        }
        occupied[1]   = total[1] - free[1];

        return occupied;
    }

}
