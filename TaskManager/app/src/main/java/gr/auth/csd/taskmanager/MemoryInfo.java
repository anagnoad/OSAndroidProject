package gr.auth.csd.taskmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Steve Laskaridis on 1/5/2015.
 */
public class MemoryInfo {
    private Date dateCaptured;
    private long internalMemoryFree;
    private long internalMemoryAvailable;
    private long externalMemoryFree;
    private long externalMemoryAvailable;

    public Date getDateCaptured() {
        return dateCaptured;
    }

    public void setDateCaptured(Date dateCaptured) {
        this.dateCaptured = dateCaptured;
    }

    public long getInternalMemoryFree() {
        return internalMemoryFree;
    }

    public void setInternalMemoryFree(long internalMemoryFree) {
        this.internalMemoryFree = internalMemoryFree;
    }

    public long getInternalMemoryAvailable() {
        return internalMemoryAvailable;
    }

    public void setInternalMemoryAvailable(long internalMemoryAvailable) {
        this.internalMemoryAvailable = internalMemoryAvailable;
    }

    public long getExternalMemoryFree() {
        return externalMemoryFree;
    }

    public void setExternalMemoryFree(long externalMemoryFree) {
        this.externalMemoryFree = externalMemoryFree;
    }

    public long getExternalMemoryAvailable() {
        return externalMemoryAvailable;
    }

    public void setExternalMemoryAvailable(long externalMemoryAvailable) {
        this.externalMemoryAvailable = externalMemoryAvailable;
    }

    public MemoryInfo(Date dateCaptured, long internalMemoryFree, long internalMemoryAvailable, long externalMemoryFree, long externalMemoryAvailable) {
        this.dateCaptured = dateCaptured;
        this.internalMemoryFree = internalMemoryFree;
        this.internalMemoryAvailable = internalMemoryAvailable;
        this.externalMemoryFree = externalMemoryFree;
        this.externalMemoryAvailable = externalMemoryAvailable;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        builder.append(df.format(dateCaptured));
        builder.append("\n\n");
        builder.append("Internal: ");
        builder.append(internalMemoryFree/1024/1024 + "/" + internalMemoryAvailable/1024/1024 + " MB");
        builder.append("\n");
        builder.append("External: ");
        builder.append(externalMemoryFree/1024/1024 + "/" + externalMemoryAvailable/1024/1024 + " MB");
        return builder.toString();
//        return "MemoryInfo{" +
//                "dateCaptured=" + dateCaptured +
//                ", internalMemoryFree=" + internalMemoryFree +
//                ", internalMemoryAvailable=" + internalMemoryAvailable +
//                ", externalMemoryFree=" + externalMemoryFree +
//                ", externalMemoryAvailable=" + externalMemoryAvailable +
//                '}';
    }

}
