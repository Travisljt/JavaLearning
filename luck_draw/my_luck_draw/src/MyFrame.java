import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private NameList nameList;

    //name输入区域
    private JTextField inputNameField;

    //显示名字的label
    private JLabel nameLabel;

    //奖品显示label
    private JLabel priceLabel;

    //抽奖button
    private JButton drawButton;

    //添加name的button
    private JButton addButton;
    private DrawListener drawListener;

    private PriceListener priceListener;

    private AddListener addListener;

    public MyFrame(String title, int posX, int posY, int width, int height) {
        setLayout(new FlowLayout());
        setBackground(Color.gray);
        setTitle(title);
        setBounds(posX, posY, width, height);
        initialize();
//        outputLabel = new JLabel("Test");
//        executeButton = new JButton("抽奖");
//        add(outputLabel);
//        add(inputTextField);
//        add(executeButton);
//        setVisible(true);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        listener = new MyListener();
//        listener.setTextField(inputTextField);
//        listener.setTextLabel(outputLabel);
//        inputTextField.addActionListener(listener);
//        executeButton.addActionListener(listener);
    }

    public void initialize() {
        nameList = new NameList();
        inputNameField = new JTextField(8);
        priceLabel = new JLabel("三等奖");
        nameLabel = new JLabel("抽奖即将开始，请按下抽奖按钮");
        drawButton = new JButton("抽奖");
        addButton = new JButton("添加名字");
        add(inputNameField);
        add(priceLabel);
        add(drawButton);
        add(nameLabel);
        add(addButton);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addListener = new AddListener();
        drawListener = new DrawListener(nameList);
        priceListener = new PriceListener();
    }


}
