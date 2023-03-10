import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawListener implements ActionListener {
    private JTextField textField;
    private JTextArea textArea;

    private JLabel nameLabel;

    private NameList nameList;

    public DrawListener(NameList nameList) {
        this.nameList = nameList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
