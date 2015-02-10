package gr.auth.csd.taskmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the information captured from the device on a single day, meaning the
 * internal/external memory that is currently available as well as their total size.
 * Created by Steve Laskaridis on 1/5/2015.
 */
public class MemoryInfo {
    /**
     * The date that this information was captured.
     */
    private Date dateCaptured;
    /**
     * The free internal memory of the device. Long, as it is returned in bytes.
     */
    private long internalMemoryFree;
    /**
     * The total available internal memory of the device. Long, as it is returned in bytes.
     */
    private long internalMemoryAvailable;
    /**
     * The free external memory of the device. Long, as it is returned in bytes.
     */
    private long externalMemoryFree;
    /**
     * The total available external memory of the device. Long, as it is returned in bytes.
     */
    private long externalMemoryAvailable;

    /**
     * Getter for the Date of the record.
     * @return
     */
    public Date getDateCaptured() {
        return dateCaptured;
    }

    /**
     * Setter for the Date of the record.
     * @param dateCaptured
     */
    public void setDateCaptured(Date dateCaptured) {
        this.dateCaptured = dateCaptured;
    }

    /**
     * Getter for the free internal memory field.
     * @return free internal memory (Bytes)
     */
    public long getInternalMemoryFree() {
        return internalMemoryFree;
    }

    /**
     * Setter for the free internal memory field.
     * @param internalMemoryFree
     */
    public void setInternalMemoryFree(long internalMemoryFree) {
        this.internalMemoryFree = internalMemoryFree;
    }

    /**
     * Getter for the total available internal memory field.
     * @return total available internal memory (Bytes)
     */
    public long getInternalMemoryAvailable() {
        return internalMemoryAvailable;
    }

    /**
     * Setter for the total available internal memory field.
     * @param internalMemoryAvailable
     */
    public void setInternalMemoryAvailable(long internalMemoryAvailable) {
        this.internalMemoryAvailable = internalMemoryAvailable;
    }

    /**
     * Getter for the free external memory field.
     * @return free external memory (Bytes)
     */
    public long getExternalMemoryFree() {
        return externalMemoryFree;
    }

    /**
     * Setter for the free external memory field.
     * @param externalMemoryFree
     */
    public void setExternalMemoryFree(long externalMemoryFree) {
        this.externalMemoryFree = externalMemoryFree;
    }

    /**
     * Getter for the total available external memory field.
     * @return
     */
    public long getExternalMemoryAvailable() {
        return externalMemoryAvailable;
    }

    /**
     * Setter for the total available external memory field.
     * @param externalMemoryAvailable
     */
    public void setExternalMemoryAvailable(long externalMemoryAvailable) {
        this.externalMemoryAvailable = externalMemoryAvailable;
    }

    /**
     * Default ctor
     * @param dateCaptured
     * @param internalMemoryFree
     * @param internalMemoryAvailable
     * @param externalMemoryFree
     * @param externalMemoryAvailable
     */
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
