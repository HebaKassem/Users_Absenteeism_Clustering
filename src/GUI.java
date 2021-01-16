import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI extends JFrame {

    private JPanel GUIpanel;

    private JRadioButton absenteeismFileRadioButton;
    private JRadioButton reviewsFileRadioButton;
    private JTextField kField;
    private JTextField percentField;
    private JButton runClusteringButton;

    public GUI() {

        ButtonGroup bg = new ButtonGroup();
        bg.add(absenteeismFileRadioButton);
        bg.add(reviewsFileRadioButton);

        runClusteringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sk = kField.getText();
                    Integer k=Integer.parseInt(sk);
                    String percent = percentField.getText();
                    float ipercent=Float.parseFloat(percent);

                    if (absenteeismFileRadioButton.isSelected()) {

                        Main m = new Main();

                        JOptionPane.showMessageDialog(null, m.run(k, ipercent));
                    } else if (reviewsFileRadioButton.isSelected()) {
                        //JOptionPane.showMessageDialog(null, Main.run(2, k, percent));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new GUI().GUIpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
