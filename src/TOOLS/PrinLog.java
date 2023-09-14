package TOOLS;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrinLog extends JFrame {
	private static final long serialVersionUID = 1L;
	private final String title;
	private final JTextArea ta;

    public PrinLog(String t, int w, int h, int x, int y) {
        ta = new JTextArea();
        ta.setEditable(false);
        ta.addMouseListener(new MouseAdapter(){
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if (e.getButton() == MouseEvent.BUTTON3) {
        			setTitle(title + (!isAlwaysOnTop()?"（置顶）":""));
        			setAlwaysOnTop(!isAlwaysOnTop());
        		}
        	}
        });
        add(new JScrollPane(ta));
        title = t;
        setTitle(title);
        setSize(w,h);
        setLocation(x,y);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void print(String s){
        s = (new SimpleDateFormat("HH:mm:ss").format(new Date())) + "：" + s;
        ta.append(s);
        ta.setCaretPosition(ta.getText().length());
    }
}
