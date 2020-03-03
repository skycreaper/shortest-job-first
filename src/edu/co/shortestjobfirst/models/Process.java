package edu.co.shortestjobfirst.models;

import java.awt.Color;

/**
 *
 * @author juancsr
 */
public class Process extends Thread {
    private String processName;
    private boolean locked;
    private boolean duplicate;
    private int timeWasBlocked;
    private int arriveTime;
    private int frontArriveTime;
    private int executionTime; // tiempo de rafaga
    private int totalExecutionTime; // tiempo total de rafaga
    private int startTime;
    private int endTime;
    private int returnTime;
    private int waitTime;
    private int row; // fila para pintar en el front
    private Color color;
    private volatile boolean running = true;

    /*
    * Processes's constructor 
    * @param name Name of the process
    * @param arriveTime Time it takes to the process arrive into the critial section
    * @param frontArriveTime tiempo de llegada del proceso mostrado en el front
    * @param exectionTime: Time it takes for the process to be executed
    */
    public Process(String name, int arriveTime, int frontArriveTime, int executionTime, int totalExecutionTime) {
        locked = false;
        this.processName = name;
        this.arriveTime = arriveTime;
        this.frontArriveTime = frontArriveTime;
        this.executionTime = executionTime;
        this.totalExecutionTime = executionTime;
        this.duplicate = false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    public void calculateTimes() {
        endTime = executionTime + startTime;
        returnTime = endTime - arriveTime;
        waitTime = returnTime - executionTime;
    }
    
    public boolean getLocked() {
        return this.locked;
    }
    
    public void termiante() {
        this.running = false;
    }
    
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public int getEndTime() {
        return this.endTime; 
    }
    
    public int getReturnTime() {
        return this.returnTime;
    }
    
    public int getWaitTime() {
        return this.waitTime;
    }

    public int getTimeWasBlocked() {
        return timeWasBlocked;
    }

    public void setTimeWasBlocked(int timeWasBlocked) {
        this.timeWasBlocked = timeWasBlocked;
    }

    public int getFrontArriveTime() {
        return frontArriveTime;
    }

    public void setFrontArriveTime(int frontArriveTime) {
        this.frontArriveTime = frontArriveTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTotalExecutionTime() {
        return totalExecutionTime;
    }

    public void setTotalExecutionTime(int totalExecutionTime) {
        this.totalExecutionTime = totalExecutionTime;
    }
}
