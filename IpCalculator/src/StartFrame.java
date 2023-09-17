import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    public StartFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(200,90);
        this.setLocationRelativeTo(null);
        JButton but1=new JButton("Ip calculator");
        but1.addActionListener((e -> {
            MainFrame mf=new MainFrame();
        }));
        JButton but2=new JButton("Subnet divider");
        but2.addActionListener((e -> {
            AnotherFrame mf=new AnotherFrame();
        }));
        this.add(but1, BorderLayout.NORTH);
        this.add(but2,BorderLayout.SOUTH);
        this.setVisible(true);

    }
}
