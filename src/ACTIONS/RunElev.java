package ACTIONS;

import ITEMS.Elevator;
import ITEMS.People;
import TOOLS.PrinLog;

import java.util.Comparator;
import java.util.LinkedList;

public class RunElev implements Runnable{
	private final Integer RUNINTERVAL;
    private final LinkedList<Elevator> EL;
    private final LinkedList<People> PL;
    private final PrinLog LOG;
    
    public RunElev(Integer numOfElves, Integer floors, Integer runInterval, LinkedList<People> peopleList){
        EL = new LinkedList<>();
        for(int i = 0; i < numOfElves; i++) {
            this.EL.add(new Elevator(i, floors));
        }

        RUNINTERVAL = runInterval;
        PL = peopleList;
        
        LOG = new PrinLog("运行日志",500, 400, 500, 100);
        LOG.print("共" + numOfElves + "台电梯（均在1层，共享上下按钮）。楼高" + floors + "层，每隔" + RUNINTERVAL + "s电梯运行一层。\n\n");
    }

    @Override
    public void run() {
        try {
            while (true) {
                moveElev();
                Thread.sleep(RUNINTERVAL * 1000);
            }
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

    private void moveElev() {
        for (Elevator e : EL) {
            if (e.getStatus() == 1) {
                if (e.getUPREQ().contains(e.getNowFloor())) {
                    LOG.print("▲ 电梯：" + e.getNO() + "号，在" + e.getNowFloor() + "层停靠。\n");
                    servePeople(e);
                    e.getUPREQ().remove(e.getNowFloor());
                    LOG.print("▲ 电梯：" + e.getNO() + "号，向上：" + e.getUPREQ() + "，向下：" + e.getDOWNREQ() + "。\n\n");
                }
                if (e.getNowFloor() < e.getHighest()) {
                	e.incSteps();
                	e.incNowFloor();
                }
                else if (!e.getDOWNREQ().isEmpty()) {
                	e.setStatus(-1);
                }
                else {
                    e.setStatus(0);
                    LOG.print("■ 电梯：" + e.getNO() + "号，在" + e.getNowFloor() + "层闲置！\n\n");
                }
            } else if (e.getStatus() == -1) {
                if (e.getDOWNREQ().contains(e.getNowFloor())) {
                    LOG.print("▼ 电梯：" + e.getNO() + "号，在" + e.getNowFloor() + "层停靠。\n");
                    servePeople(e);
                    e.getDOWNREQ().remove(e.getNowFloor());
                    LOG.print("▼ 电梯：" + e.getNO() + "号，向上：" + e.getUPREQ() + "，向下：" + e.getDOWNREQ() + "。\n\n");
                }
                if (e.getNowFloor() > e.getLowest()) {
                	e.incSteps();
                	e.decNowFloor();
                }
                else if (!e.getUPREQ().isEmpty()) {
                	e.setStatus(1);
                }
                else {
                    e.setStatus(0);
                    LOG.print("■ 电梯：" + e.getNO() + "号，在" + e.getNowFloor() + "层闲置！\n\n");
                }
            }
        }
    }
    public LinkedList<Elevator> getEL() { return EL; }
    private void servePeople(Elevator e) {
        for (People p : PL) {
            if (!p.isServed()) {
                if (p.isWaiting() && p.getNOWFLOOR().equals(e.getNowFloor()) && p.getSTATUS().equals(e.getStatus())) {
                    p.setWaiting(false);
                    p.setElevNo(e.getNO());
                    if (p.getSTATUS() == 1) {
                        if (!e.getUPREQ().contains(p.getAIMFLOOR())) {
                            e.getUPREQ().add(p.getAIMFLOOR());
                            e.getUPREQ().sort(Comparator.naturalOrder());
                        }
                    } else {
                        if (!e.getDOWNREQ().contains(p.getAIMFLOOR())) {
                            e.getDOWNREQ().add(p.getAIMFLOOR());
                            e.getDOWNREQ().sort(Comparator.reverseOrder());
                        }
                    }
                    LOG.print("→ 乘客：" + p.getNO() + "号，进入" + e.getNO() + "号电梯（目标" + p.getAIMFLOOR() + "层）。\n");
                }
                if (p.getAIMFLOOR().equals(e.getNowFloor()) && !p.isWaiting() && p.getElevNo().equals(e.getNO())) {
                    p.setServed(true);
                    LOG.print("← 乘客：" + p.getNO() + "号，走出" + e.getNO() + "号电梯。\n");
                }
            }
        }
    }
}
