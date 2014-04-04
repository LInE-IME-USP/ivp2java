package usp.ime.line.ivprog.view.domaingui.editinplace;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.listeners.IValueListener;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.workspace.codecomponents.IDomainObjectUI;

public class EditInPlace extends JPanel implements KeyListener {
    private JLabel            nameLabel;
    private JPanel            nameContainer;
    private JTextField        nameField;
    private IValueListener    valueListener;
    public static int         PATTERN_VARIABLE_NAME          = 0;
    public static int         PATTERN_VARIABLE_VALUE_DOUBLE  = 1;
    public static int         PATTERN_VARIABLE_VALUE_INTEGER = 2;
    public static int         PATTERN_VARIABLE_VALUE_STRING  = 3;
    private int               currentPattern                 = 0;
    private String[]          patternsTyping                 = { "^[a-zA-Z_][a-zA-Z0-9_]*$", "^[0-9]*.[0-9]*$", "^[0-9]*$", ".*" };
    private String[]          patterns                       = { "^[a-zA-Z_][a-zA-Z0-9_]*$", "\\b[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?\\b", "\\b[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?\\b", ".*" };
    private Color             bgColor                        = FlatUIColors.MAIN_BG;
    public static final Color hoverColor                     = FlatUIColors.HOVER_COLOR;
    public boolean            isUpdate                       = false;
    
    public EditInPlace(Color bgColor) {
        this.bgColor = bgColor;
        FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setVgap(0);
        flowLayout.setHgap(0);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        initNameContainer();
        initNameLabel();
        initNameField();
    }
    
    public EditInPlace() {
        FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setVgap(0);
        flowLayout.setHgap(0);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        initNameContainer();
        initNameLabel();
        initNameField();
    }
    
    private void initNameContainer() {
        nameContainer = new JPanel();
        nameContainer.setBackground(bgColor);
        add(nameContainer);
    }
    
    private void initNameLabel() {
        nameLabel = new JLabel("");
        nameLabel.addMouseListener(new VariableMouseListener());
        nameContainer.add(nameLabel);
    }
    
    private void hasFocusLost() {
        nameContainer.setVisible(true);
        nameField.setVisible(false);
        if (valueListener != null) {
            valueListener.valueChanged(nameField.getText());
        }
        nameField.setBorder(BorderFactory.createEtchedBorder());
    }
    
    private void initNameField() {
        nameField = new JTextField(5);
        nameField.addKeyListener(this);
        nameField.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent arg0) {
                if (!isUpdate) {
                    String value = nameField.getText();
                    if (value.matches(patterns[currentPattern])) {
                        hasFocusLost();
                    } else {
                        nameField.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                }
                isUpdate = false;
            }
            
            public void focusGained(FocusEvent arg0) {
            }
        });
        nameField.setVisible(false);
        initInputMap();
        add(nameField);
    }
    
    private void initInputMap() {
        AbstractAction editDone = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                Tracking.getInstance().track("event=FOCUSLOST;where=EDIT_DONE_EDITINPLACE;");
                isUpdate = true;
                nameField.setFocusable(false);
                nameField.setFocusable(true);
            }
        };
        nameField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ENTER), editDone);
        nameField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), editDone);
        nameField.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_TAB), editDone);
    }
    
    public void setValueListener(IValueListener listener) {
        valueListener = listener;
    }
    
    private class VariableMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
        }
        
        public void mouseEntered(MouseEvent arg0) {
            nameContainer.setBackground(hoverColor);
        }
        
        public void mouseExited(MouseEvent arg0) {
            nameContainer.setBackground(bgColor);
        }
        
        public void mousePressed(MouseEvent arg0) {
        }
        
        public void mouseReleased(MouseEvent e) {
            if (e.getSource().equals(nameLabel)) {
                nameField.setVisible(true);
                nameContainer.setVisible(false);
                nameField.requestFocus();
            }
        }
    }
    
    public void setValue(String name) {
        nameField.setText(name);
        nameLabel.setText(name);
        revalidate();
        repaint();
    }
    
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
    public void keyTyped(KeyEvent e) {
        // enter é 13... verificar pq esta funfando com 10
        if (((int) e.getKeyChar()) == 10 || ((int) e.getKeyChar()) == 13) {
            hasFocusLost();
            return;
        }
        String value = nameField.getText();
        if (nameField.getSelectionStart() == 0) {
            value = e.getKeyChar() + value;
        } else if (nameField.getSelectionStart() == value.length()) {
            value = value + e.getKeyChar();
        } else {
            value = value.substring(0, nameField.getSelectionStart()) + e.getKeyChar() + value.substring(nameField.getSelectionEnd());
        }
        if (!value.matches(patternsTyping[currentPattern])) {
            int c = ((int) e.getKeyChar());
            if ((c != 8) && (c != 127)) {
                getToolkit().beep();
                e.consume();
            }
        }
    }
    
    public boolean isValidValue(KeyEvent e) {
        return false;
    }
    
    public int getCurrentPattern() {
        return currentPattern;
    }
    
    public void setCurrentPattern(int currentPattern) {
        this.currentPattern = currentPattern;
    }
    
    public void resetTextField() {
        nameField.setText(nameLabel.getText());
    }
}
