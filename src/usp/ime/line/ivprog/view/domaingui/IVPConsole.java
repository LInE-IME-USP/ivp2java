package usp.ime.line.ivprog.view.domaingui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;

public class IVPConsole extends JPanel{
    
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private StyledDocument doc;
    private String[]       styles;
    private final StyleContext     cont        = StyleContext.getDefaultStyleContext();
    private final AttributeSet     attrRed     = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    private final AttributeSet     attrRegular = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.WHITE);
    private final AttributeSet     attrYellow  = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.YELLOW);

    public IVPConsole() {
        setLayout(new BorderLayout(0, 0));
        setBackground(FlatUIColors.CONSOLE_COLOR);
        scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane);
        textPane = new JTextPane();
        doc = textPane.getStyledDocument();
        textPane.setFont(new Font("Consolas", Font.BOLD, 12));
        textPane.setOpaque(true);
        textPane.setBackground(FlatUIColors.CONSOLE_COLOR);
        textPane.setEditable(false);
        scrollPane.setViewportView(textPane);
        Services.getService().getController().setConsole(this);
    }
    
    public void println(Object o) {
        print(String.valueOf(o) + "\n");
        repaint();
    }
    
    public void print(final Object o) {
        try {
            doc.insertString(doc.getLength(), String.valueOf(o), attrRegular);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void printError(Object o) {
        try {
            doc.insertString(doc.getLength(), ">(!)< ", attrYellow);
            doc.insertString(doc.getLength(), String.valueOf(o) + "\n", attrYellow);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void clean() {
        textPane.setText("");
    }
    
}
