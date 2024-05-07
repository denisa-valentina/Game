package GameWindow;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


/*! \class GameWindow
    \brief Implementeaza notiunea de fereastra a jocului.

    Membrul windowFrame este un obiect de tip JFrame care va avea utilitatea unei
    ferestre grafice
 */
public class GameWindow {

    //private final JFrame windowFrame; // fereastra principala a jocului

    public GameWindow(GamePanel gamePanel) {
        JFrame windowFrame = new JFrame();

        windowFrame.setTitle("Stuck in Adventure Time");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // inchide consola cand inchidem fereastra

        windowFrame.add(gamePanel); // inseram ceea ce am desenat in "rama"
        windowFrame.setResizable(false);


        windowFrame.pack(); // causes this window to be sized to fit the preferred size and layouts of it subcomponents
        windowFrame.setLocationRelativeTo(null); // fereastra se va deschide in mijlocul ecranului, nu in colt stanga sus
        windowFrame.setVisible(true);

        windowFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("Back");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
