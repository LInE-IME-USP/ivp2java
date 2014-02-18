package usp.ime.line.ivprog.view.domaingui.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import usp.ime.line.ivprog.listeners.IValueListener;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.editinplace.EditInPlace;
import usp.ime.line.ivprog.view.domaingui.editinplace.EditBoolean;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class AskUserFrameBoolean extends JDialog implements IValueListener{
    
    private JPanel contentPane;
    private EditBoolean value;
    private JPanel content;
    private JPanel buttons;
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel header;
    private JLabel plsInsertLabel;
    private JLabel propertyLabel;
    private String finalValue = "true";
    private boolean interrupt = false;

    public AskUserFrameBoolean() {
        super(new JFrame(), true);
        initLayout();
        initContent();
        initEditInPlace();
        initButtonsPanel();
        initButtons();
        initHeader();
        initLabels();
        pack();
        setLocationRelativeTo(null);
    }

    private void initLabels() {
        plsInsertLabel = new JLabel(ResourceBundleIVP.getString("AskUser.boolean.messageLabel"));
        header.add(plsInsertLabel);
        propertyLabel = new JLabel(ResourceBundleIVP.getString("AskUser.boolean.propertyLabel"));
        header.add(propertyLabel);
    }

    private void initHeader() {
        header = new JPanel();
        header.setBackground(FlatUIColors.MAIN_BG);
        contentPane.add(header, BorderLayout.NORTH);
    }

    private void initButtons() {
        btnOk = new JButton(ResourceBundleIVP.getString("AskUser.OKBtn.text"));
        btnOk.setToolTipText(ResourceBundleIVP.getString("AskUser.OKBtn.tip"));
        buttons.add(btnOk);
        btnOk.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                interrupt = false;
                setVisible(false);
            }
        });
        btnCancel = new JButton(ResourceBundleIVP.getString("AskUser.cancelBtn.text"));
        btnCancel.setToolTipText(ResourceBundleIVP.getString("AskUser.cancelBtn.tip"));
        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                interrupt = true;
                setVisible(false);
            }
        });
        buttons.add(btnCancel);
    }

    private void initButtonsPanel() {
        buttons = new JPanel();
        buttons.setBackground(FlatUIColors.MAIN_BG);
        contentPane.add(buttons, BorderLayout.SOUTH);
    }

    private void initEditInPlace() {
        value = new EditBoolean();
        value.setValue(""+finalValue+"");
        value.setValueListener(this);
        content.add(value);
    }

    private void initContent() {
        content = new JPanel();
        content.setBackground(FlatUIColors.MAIN_BG);
        contentPane.add(content, BorderLayout.CENTER);
    }

    private void initLayout() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    public boolean getFinalValue() {
        if(finalValue.equals("true")){
            return true;
        }else{
            return false;
        }
    }

    public void setFinalValue(String finalValue) {
        this.finalValue = finalValue;
    }
    
    public void showAskUser(){
        value.setValue(""+finalValue+"");
        setVisible(true);
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    public void valueChanged(String value) {
        finalValue = value;
        this.value.setValue(value);
    }
    
}
