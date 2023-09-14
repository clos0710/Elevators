package ITEMS;

import java.util.Random;

public class People {
    private final Integer NO;
    private final Integer NOWFLOOR;
    private final Integer AIMFLOOR;
    private final Integer STATUS;
    private boolean isWaiting = true;
    private boolean isServed = false;
    private Integer elevNo = -1;

    public People(Integer no, Integer ground) {
        NO = no;
        NOWFLOOR = (int)(Math.random() * ground + 1);

        if (NOWFLOOR == 1) STATUS = 1;
        else if (NOWFLOOR.equals(ground)) STATUS = -1;
        else {
            if (new Random().nextBoolean()) STATUS = 1;
            else STATUS = -1;
        }

        if (STATUS == 1) {
            AIMFLOOR = (int)(Math.random() * (ground - NOWFLOOR) + NOWFLOOR + 1);
        } else {
            AIMFLOOR = (int)(Math.random() * (NOWFLOOR - 1) + 1);
        }
    }

    public Integer getNO() { return NO; }
    public Integer getNOWFLOOR() { return NOWFLOOR; }
    public Integer getAIMFLOOR() { return AIMFLOOR; }
    public Integer getSTATUS() { return STATUS; }
    public Integer getElevNo() { return elevNo; }
    public boolean isWaiting() { return isWaiting; }
    public boolean isServed() { return isServed; }
    public void setWaiting(boolean waiting) { isWaiting = waiting; }
    public void setServed(boolean served) { isServed = served; }
    public void setElevNo(Integer no) { elevNo = no; }
}