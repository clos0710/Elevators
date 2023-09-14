package ITEMS;

import java.util.LinkedList;

public class Elevator {
    private final Integer NO;
    private final Integer FLOORS;
    private Integer nowFloor;
    private final LinkedList<Integer> UPREQ;
    private final LinkedList<Integer> DOWNREQ;
    private int status; //0代表闲置、1代表向上、-1代表向下
    private int steps;


    public Elevator(Integer no, Integer floors) {
        NO = no;
        FLOORS = floors;
        nowFloor = 1;
        UPREQ = new LinkedList<>();
        DOWNREQ = new LinkedList<>();
        status = 0;
        steps = 0;
    }

    public Integer getNO() { return NO; }
    public Integer getNowFloor() { return nowFloor; }
    public LinkedList<Integer> getDOWNREQ() { return DOWNREQ; }
    public LinkedList<Integer> getUPREQ() { return UPREQ; }
    public int getStatus() { return status; }
    public int getSteps() { return steps; }
    
    public void setStatus(int status) { this.status = status; }
    private void incSteps() { steps++; }
    public void decNowFloor() {
        if (nowFloor > 1) nowFloor--;
        incSteps();
    }
    public void incNowFloor() {
        if (nowFloor < FLOORS) nowFloor++;
        incSteps();
    }
    public Integer getHighest() { return Math.max(UPREQ.isEmpty()?nowFloor:UPREQ.getLast(), DOWNREQ.isEmpty()?nowFloor:DOWNREQ.getFirst()); }
    public Integer getLowest() { return Math.min(UPREQ.isEmpty()?nowFloor:UPREQ.getFirst(), DOWNREQ.isEmpty()?nowFloor:DOWNREQ.getLast()); }
}
