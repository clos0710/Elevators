package MAIN;

import ACTIONS.DispELev;
import ACTIONS.RunElev;
import ACTIONS.SimuPeop;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Main {
    public static void main(String[] args) throws IOException {
    	
        Integer People = 10;
        Integer MinInterval = 1;
        Integer MaxInterval = 3;
        
        Integer Elevators = 4;
        Integer Floors = 30;
        
        Integer RunInterval = 1;

        PipedWriter w = new PipedWriter();
        SimuPeop sp = new SimuPeop(People, MinInterval, MaxInterval, Floors, w);
        new Thread(sp).start();

        RunElev re = new RunElev(Elevators, Floors, RunInterval, sp.getPL());
        new Thread(re).start();

        PipedReader r = new PipedReader();
        w.connect(r);
        DispELev de = new DispELev(sp.getPL(), re.getEL(), r);
        new Thread(de).start();
    }
}