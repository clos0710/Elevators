package ACTIONS;

import ITEMS.People;
import TOOLS.PrinLog;

import java.io.IOException;
import java.io.PipedWriter;
import java.util.LinkedList;

public class SimuPeop implements Runnable{
	private final Integer PEOPLE;
	private final Integer MININTERVAL;
	private final Integer MAXINTERVAL;
    private final Integer FLOORS;
    private final LinkedList<People> PL;
    private final PipedWriter WRITER;
    private final PrinLog LOG;

    public SimuPeop(Integer People, Integer MinInterval, Integer MaxInterval, Integer floors, PipedWriter writer) {
    	PEOPLE = People;
    	MININTERVAL = MinInterval;
    	MAXINTERVAL = MaxInterval;
    	FLOORS = floors;
        PL = new LinkedList<>();
        WRITER = writer;
        LOG = new PrinLog("乘客日志",350, 400, 100, 100);
    }

    @Override
    public void run() {
        try {
            LOG.print("最少" + MININTERVAL + "s，最大" + MAXINTERVAL + "s到达一名乘客，共" + PEOPLE + "名乘客。\n\n");
            for (int i = 0; i < PEOPLE; i++) {
                Thread.sleep((long)(Math.random() * (MAXINTERVAL * 1000 - 1000) + MININTERVAL * 1000));
                People p = new People(i, FLOORS);
                PL.addLast(p);
                if (p.getSTATUS() == 1)  LOG.print(i + "号乘客：当前" + p.getNOWFLOOR() + "层，上行。\n");
                else LOG.print(i + "号乘客：当前" + p.getNOWFLOOR() + "层，下行。\n");
                WRITER.write(1);
            }
            WRITER.close();
        } catch (InterruptedException | IOException e) {
            e.getMessage();
        }
    }
    public LinkedList<People> getPL() { return PL; }
}
