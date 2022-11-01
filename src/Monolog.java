import functions.AnyFunctions;
import functions.CubicFunction;
import functions.IFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monolog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField TtextField;
    private JTextField F2textField;
    private JTextField BtextField;
    private JTextField CtextField;
    private JTextField WtextField;
    private JTextField DtextField;
    private JTextField C1textField;
    private JTextField A2textField;
    private JTextField C2textField;
    private JTextField A1textField;
    private JTextField AtextField;
    private JTextField F1textField;
    private JTextField W2textField;
    private JTextField W1textField;
    private JTextField FtextField;
    private JLabel A1;
    private JPanel A;
    private JTextField textField;
    private JButton buildButton;
    public static Map<String, Double> params;
    public static ArrayList<AnyFunctions> anyFunctions;
    private DrawPanel dp;

    public Monolog() {
        setContentPane(contentPane);
        //setPreferredSize(new Dimension(1500, 800));
        params = new HashMap<>();
        params.put("A", 1.0);
        params.put("B", 1.0);
        params.put("C", 1.0);
        params.put("D", 1.0);
        params.put("W", 1.0);
        params.put("F" , 1.0);
        params.put("A1", 1.0);
        params.put("A2", 1.0);
        params.put("F1", 1.0);
        params.put("F2", 1.0);
        params.put("W1", 1.0);
        params.put("W2", 1.0);
        params.put("C1", 1.0);
        params.put("C2", 1.0);
        params.put("T", 1.0);

        anyFunctions = new ArrayList<>();

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double A = 1.0;
                double B = 1.0;
                double C = 1.0;
                double D = 1.0;
                double F = 1.0;
                double T = 1.0;
                double W = 1.0;
                double A1 = 1.0;
                double A2 = 1.0;
                double W1 = 1.0;
                double W2 = 1.0;
                double F1 = 1.0;
                double F2 = 1.0;
                double C1 = 1.0;
                double C2 = 1.0;
                Pattern pattern = Pattern.compile("^\\d+");

                Matcher Amatcher = pattern.matcher(AtextField.getText());
                if(Amatcher.find()){
                    A = Double.valueOf(AtextField.getText().substring(Amatcher.start(), Amatcher.end()));
                }

                Matcher Bmatcher = pattern.matcher(BtextField.getText());
                if(Bmatcher.find()){
                    B = Double.parseDouble(BtextField.getText());
                }

                Matcher Cmatcher = pattern.matcher(CtextField.getText());
                if(Cmatcher.find()) {
                    C = Double.parseDouble(CtextField.getText());
                }

                Matcher Dmatcher = pattern.matcher(DtextField.getText());
                if(Dmatcher.find()) {
                    D = Double.parseDouble(DtextField.getText());
                }

                Matcher Tmatcher = pattern.matcher(TtextField.getText());
                if(Tmatcher.find()) {
                    T = Double.parseDouble(TtextField.getText());
                }

                Matcher Fmatcher = pattern.matcher(FtextField.getText());
                if(Fmatcher.find()) {
                    F = Double.parseDouble(FtextField.getText());
                }

                Matcher Wmatcher = pattern.matcher(WtextField.getText());
                if(Wmatcher.find()) {
                    W = Double.parseDouble(WtextField.getText());
                }

                Matcher C1matcher = pattern.matcher(C1textField.getText());
                if(C1matcher.find()){
                    C1 = Double.parseDouble(C1textField.getText());
                }

                Matcher C2matcher = pattern.matcher(C2textField.getText());
                if(C2matcher.find()){
                    C2 = Double.parseDouble(C2textField.getText());
                }

                Matcher A2matcher = pattern.matcher(A2textField.getText());
                if(A2matcher.find()){
                    A2 = Double.parseDouble(A2textField.getText());
                }

                Matcher A1matcher = pattern.matcher(A1textField.getText());
                if(A1matcher.find()){
                    A1 = Double.parseDouble(A1textField.getText());
                }

                Matcher F1matcher = pattern.matcher(F1textField.getText());
                if(F1matcher.find()){
                    F1 = Double.parseDouble(F1textField.getText());
                }

                Matcher F2matcher = pattern.matcher(F2textField.getText());
                if(F2matcher.find()){
                    F2 = Double.parseDouble(F2textField.getText());
                }

                Matcher W1matcher = pattern.matcher(W1textField.getText());
                if(W1matcher.find()){
                    W1 = Double.parseDouble(W1textField.getText());
                }

                Matcher W2matcher = pattern.matcher(W2textField.getText());
                if(W2matcher.find()){
                    W2 = Double.parseDouble(W2textField.getText());
                }

                params.put("A", A);
                params.put("B", B);
                params.put("C", C);
                params.put("D", D);
                params.put("W", W);
                params.put("F" , F);
                params.put("A1", A1);
                params.put("A2", A2);
                params.put("F1", F1);
                params.put("F2", F2);
                params.put("W1", W1);
                params.put("W2", W2);
                params.put("C1", C1);
                params.put("C2", C2);
                params.put("T", T);
            }
        });


        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField.getText() != ""){
                    AnyFunctions f = new AnyFunctions(textField.getText());
                    anyFunctions.add(f);
                }
            }
        });


        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        Monolog dialog = new Monolog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
