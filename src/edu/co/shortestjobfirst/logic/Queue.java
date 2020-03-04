package edu.co.shortestjobfirst.logic;

import edu.co.shortestjobfirst.UI.GUI;
import edu.co.shortestjobfirst.models.Process;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author juancsr
 */
public final class Queue extends Thread {

    private final GUI gui;
    private int numberOfProcesses;
    private final String ALPHABETH[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private List<Process> processes;
    private List<Process> newProcesses;

    public Queue() {
        gui = new GUI();
        gui.btnStart.addActionListener(new java.awt.event.ActionListener() {      //Metodo implementado provisional para el actionperformed 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
    }

    public void initProcesses() {
        Process newProcess;
        int executionTime;
        Random ran = new Random();
        numberOfProcesses = ran.nextInt(5) + 1;
        processes = new ArrayList<>();
        newProcesses = new ArrayList<>();

        for (int i = 0; i < numberOfProcesses; i++) {
            executionTime = ran.nextInt(6) + 1;
            newProcess = new Process(ALPHABETH[i], i, i, executionTime, executionTime);
            newProcess.setColor(gui.getRandomColor());
            newProcess.setRowTable(i);
            processes.add(newProcess);
        }
    }

    int getTotalTime() {
        int acum = 0;
        for (int i = 0; i < processes.size(); i++) {
            acum += processes.get(i).getExecutionTime();
        }
        return acum;
    }

    public void btnStartActionPerformed(java.awt.event.ActionEvent evt) {
        initProcesses();
        gui.drawTable(processes);
        gui.drawDiagram(processes, getTotalTime());
        //setBlockedProcceses();
//        gui.lblNumberOfProcess.setText("PROCESOS: " + numberOfProcesses);

        Thread waitThread;
        waitThread = new Thread() {
            @Override
            public void run() {
                Process actualProcess, blockedProcess = null;
                Random ran = new Random();
                boolean locked;
                int startTime = 0;
                int remainingTime = 0;
                try {
                    gui.btnStart.setEnabled(false);
                    int i = 0;
                    actualProcess = processes.get(i);
                    while (processes.size() > 0) {
                        System.out.println("Process: "+actualProcess.getProcessName());
                        actualProcess.setStartTime(startTime);
                        
                        if (!actualProcess.isDuplicate()) {
                            actualProcess.setRow(i);
                        }
                        
                        gui.editDiagramCell(actualProcess.getProcessName(), actualProcess.getRow(), 0);
                        
                        locked = ran.nextBoolean() && actualProcess.getExecutionTime() > 2;
                        actualProcess.calculateTimes();
                        
                        if (locked) {
                            remainingTime = actualProcess.getExecutionTime() - (ran.nextInt(actualProcess.getExecutionTime() - 2) + 1);

                            actualProcess.setLocked(locked);
                            actualProcess.setTimeWasBlocked(actualProcess.getEndTime() - remainingTime);
                            
                            blockedProcess = new Process(
                                    actualProcess.getProcessName(),
                                    processes.size(),
                                    actualProcess.getFrontArriveTime(),
                                    remainingTime,
                                    actualProcess.getExecutionTime()
                            );
                            
                            blockedProcess.setRowTable(numberOfProcesses++);
                            blockedProcess.setRow(actualProcess.getRow());
                            blockedProcess.setColor(actualProcess.getColor());
                            blockedProcess.setDuplicate(true);

                            actualProcess.calculateTimes();

                            for (int j = actualProcess.getStartTime(); j < (actualProcess.getTimeWasBlocked() - actualProcess.getStartTime()) + actualProcess.getStartTime(); j++) {
                                gui.paintCell(j + 1, actualProcess.getRow(), actualProcess.getColor());
                                this.sleep(1000);
                            }
                            for (int j = actualProcess.getTimeWasBlocked(); j < actualProcess.getEndTime(); j++) {
                                gui.paintCell(j + 1, actualProcess.getRow(), Color.red);
                                this.sleep(1000);
                            }
                            
                            actualProcess.setExecutionTime(actualProcess.getExecutionTime() - remainingTime);
                            actualProcess.setEndTime(actualProcess.getTimeWasBlocked());
                            gui.addTableRow(blockedProcess.getProcessName(),
                                    blockedProcess.getFrontArriveTime(),
                                    blockedProcess.getExecutionTime()
                            );
                            actualProcess.setRow(i);

                        } else {
                            
                            for (int j = actualProcess.getStartTime(); j < actualProcess.getEndTime(); j++) {
                                gui.paintCell(j + 1, actualProcess.getRow(), actualProcess.getColor());
                                this.sleep(1000);
                            }
                        }
                        
                        startTime = actualProcess.getEndTime();
                        
                        gui.addTableInfo(actualProcess.getStartTime(), 
                                actualProcess.getEndTime(), actualProcess.getReturnTime(), 
                                actualProcess.getWaitTime(), actualProcess.getRowTable());
                        
                        if (!actualProcess.isDuplicate()) i++;
                        
                        
                        processes.remove(actualProcess);

                        actualProcess = getNextProcess();
                        if (locked) {
                            if (processes.isEmpty()) {
                                actualProcess = blockedProcess;
                            }
                            processes.add(blockedProcess);
                        }
                    }
                    gui.btnStart.setEnabled(true);

                } catch (InterruptedException e) {
                    System.out.println("Error en waitThread: " + e.getMessage());
                }
                System.out.println("Finalizó");
            }
        };
        waitThread.start();
    }

    /**
     * Establece cual será el siguiente proceso a ser ejecutado
     *
     * @return
     */
    Process getNextProcess() {
        if (processes.size() > 0) {
            List auxList = processes;
            Collections.sort(auxList, new SortbyExectionTime());
            return (Process) auxList.get(0);
        } else {
            return null;
        }
    }

    public void setBlockedProcceses() {
        boolean locked;
        Thread t;
        Process duplicateProcess, process;
        Random ran = new Random();

        int flagProcess = 0;

        for (int i = 0; i < processes.size(); i++) {
            process = processes.get(i);

            locked = ran.nextBoolean();

            if (locked) {
                int remainingTime = process.getExecutionTime() - (ran.nextInt(process.getExecutionTime()));
                System.out.println("bloqueado " + remainingTime);
                process.setLocked(locked);
                process.setTimeWasBlocked(process.getEndTime() - remainingTime);

                duplicateProcess = new Process(
                        process.getProcessName() + "B",
                        processes.size(),
                        process.getFrontArriveTime(),
                        remainingTime,
                        process.getExecutionTime()
                );
                processes.add(duplicateProcess);
            }
        }

        printProcessesTable();
    }

    void printProcessesTable() {
        System.out.println("-------------------------------------------"
                + "-------------------------------------------");
        System.out.println("name\tarriveTime\tarriveFrontTime\texecutionTime\tstartTime\tendTime"
                + "\treturnTime\twaitTime\tblocked");
        processes.forEach((process) -> {
            System.out.println(process.getProcessName() + "\t"
                    + process.getArriveTime() + "\t\t"
                    + process.getFrontArriveTime() + "\t\t"
                    + process.getExecutionTime() + "\t\t"
                    + process.getStartTime() + "\t\t"
                    + process.getEndTime() + "\t\t"
                    + process.getReturnTime() + "\t\t"
                    + process.getWaitTime() + "\t\t"
                    + process.getLocked());
        });
        System.out.println("-------------------------------------------"
                + "-------------------------------------------");
    }

}

class SortbyExectionTime implements Comparator<Process> {

    // Used for sorting in ascending order of 
    // roll number 
    @Override
    public int compare(Process b, Process a) {
        if (b.getLocked()) {
            return b.getExecutionTime();
        } else {
            if (b.getExecutionTime() - a.getExecutionTime() == 0) {
                return b.getArriveTime() - a.getArriveTime();
            } else {
                return b.getExecutionTime() - a.getExecutionTime();
            }
        }
    }
}
