package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Expression;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.For;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.While;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;
import java.awt.Color;
import java.awt.Font;

public class ForUI extends CodeBaseUI implements ICodeListener {
    
    private JPanel            contentPanel;
    private JPanel            header;
    private IVPContainer      container;
    private JLabel            codeBlockName;
    private JButton           expandBtnUP;
    private JButton           expandBtnDOWN;
    private Icon              up;
    private Icon              down;
    private Icon              dot;
    private Icon              ellipsis;
    private String            context;
    private JLabel            timesLabel;
    private JLabel            fromLbl;
    private JLabel            upToLbl;
    private JLabel            stepLbl;
    private JLabel            lastParLbl;
    private ExpressionFieldUI lowerBoundField;
    private ExpressionFieldUI upperBoundField;
    private ExpressionFieldUI incrementField;
    private JButton           btnMoreOptions;
    private JButton           btnLessOptions;
    private JLabel indexLbl;
    
    public ForUI(String id) {
        super(id);
        setModelID(id);
        initContentPanel();
        initExpandButtonIcon();
        initHeader();
        initExpressionHolder();
        initContainer();
        addContentPanel(contentPanel);
        setBackground(FlatUIColors.MAIN_BG);
        Services.getService().getController().addComponentListener(this, id);
    }
    
    private void initContainer() {
        container = new IVPContainer(true, getModelID(), "");
        container.setContainerBackground(FlatUIColors.MAIN_BG);
        container.setVisible(false);
        contentPanel.add(container, BorderLayout.CENTER);
    }
    
    private void initExpandButtonIcon() {
        up = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/expand_up.png"));
        down = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/expand_down.png"));
        dot = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/dot.png"));
        ellipsis = new javax.swing.ImageIcon(getClass().getResource("/usp/ime/line/resources/icons/ellipsis.png"));
    }
    
    private void initContentPanel() {
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
    }
    
    private void initHeader() {
        header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setOpaque(false);
        contentPanel.add(header, BorderLayout.NORTH);
        initExpandBtnUP();
        initExpandBtnDOWN();
        initExpression();
        initFieldsAndLabels();
        initCodeBlockLabel();
    }
    
    private void initExpressionHolder() {
    }
    
    private void initExpression() {
    }
    
    private void initExpandBtnUP() {
        expandBtnUP = new JButton();
        expandBtnUP.setIcon(up);
        expandBtnUP.setUI(new IconButtonUI());
        expandBtnUP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                expandedActions();
            }
        });
        expandBtnUP.setVisible(false);
        header.add(expandBtnUP);
    }
    
    private void initExpandBtnDOWN() {
        expandBtnDOWN = new JButton();
        expandBtnDOWN.setIcon(down);
        expandBtnDOWN.setUI(new IconButtonUI());
        expandBtnDOWN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                notExpandedAction();
            }
        });
        header.add(expandBtnDOWN);
    }
    
    protected void notExpandedAction() {
        container.setVisible(true);
        expandBtnUP.setVisible(true);
        expandBtnDOWN.setVisible(false);
        revalidate();
        repaint();
    }
    
    protected void expandedActions() {
        container.setVisible(false);
        expandBtnUP.setVisible(false);
        expandBtnDOWN.setVisible(true);
        revalidate();
        repaint();
    }
    
    private void initFieldsAndLabels() {
        String increment;
        increment = ((For) Services.getService().getModelMapping().get(getModelID())).getIncrementExpression();
        initIndexLabel();
        codeBlockName = new JLabel(ResourceBundleIVP.getString("ForUI.for.text"));
        timesLabel = new JLabel(ResourceBundleIVP.getString("ForUI.times.text"));
        fromLbl = new JLabel(ResourceBundleIVP.getString("ForUI.from.text"));
        fromLbl.setVisible(false);
        initLowerBound();
        upToLbl = new JLabel(ResourceBundleIVP.getString("ForUI.upTo.text"));
        upToLbl.setVisible(false);
        initUpperBound();
        stepLbl = new JLabel(ResourceBundleIVP.getString("ForUI.step.text"));
        stepLbl.setVisible(false);
        initIncrementField(increment);
        lastParLbl = new JLabel(ResourceBundleIVP.getString("ForUI.step2.text"));
        lastParLbl.setVisible(false);
        initBtns();
    }
    
    private void initBtns() {
        btnMoreOptions = new JButton(ellipsis);
        btnMoreOptions.setUI(new IconButtonUI());
        btnMoreOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                complexFor();
            }
        });
        btnLessOptions = new JButton(dot);
        btnLessOptions.setUI(new IconButtonUI());
        btnLessOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simpleFor();
            }
        });
        btnLessOptions.setVisible(false);
    }
    
    private void initIncrementField(String increment) {
        incrementField = new ExpressionFieldUI(getModelID(), this.getModelScope());
        incrementField.setBlocked(false);
        incrementField.setHolderContent(Services.getService().getRenderer().paint(increment));
        incrementField.setHoldingType(Expression.EXPRESSION_INTEGER);
        incrementField.setVisible(false);
        incrementField.setForHeader(true);
        incrementField.setForContext("forIncrement");
    }
    
    private void initUpperBound() {
        upperBoundField = new ExpressionFieldUI(this.getModelID(), this.getModelScope());
        upperBoundField.setBlocked(false);
        upperBoundField.setVisible(true);
        upperBoundField.setHoldingType(Expression.EXPRESSION_INTEGER);
        upperBoundField.setForHeader(true);
        upperBoundField.setForContext("forUpperBound");
    }
    
    private void initLowerBound() {
        lowerBoundField = new ExpressionFieldUI(getModelID(), this.getModelScope());
        lowerBoundField.setBlocked(false);
        lowerBoundField.setHoldingType(Expression.EXPRESSION_INTEGER);
        lowerBoundField.setVisible(false);
        lowerBoundField.setForHeader(true);
        lowerBoundField.setForContext("forLowerBound");
    }
    
    private void initIndexLabel() {
        For f = (For) Services.getService().getModelMapping().get(getModelID());
        indexLbl = new JLabel(ResourceBundleIVP.getString("ForUI.index.text")+f.getIndexCount()); 
        indexLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
        indexLbl.setForeground(Color.BLUE);
        indexLbl.setVisible(false);
    }
    
    private void complexFor() {
        timesLabel.setVisible(false);
        indexLbl.setVisible(true);
        fromLbl.setVisible(true);
        lowerBoundField.setVisible(true);
        upToLbl.setVisible(true);
        upperBoundField.setVisible(true);
        stepLbl.setVisible(true);
        incrementField.setVisible(true);
        lastParLbl.setVisible(true);
        btnLessOptions.setVisible(true);
        btnMoreOptions.setVisible(false);
    }
    
    private void simpleFor() {
        timesLabel.setVisible(true);
        fromLbl.setVisible(false);
        lowerBoundField.setVisible(false);
        upToLbl.setVisible(false);
        upperBoundField.setVisible(true);
        indexLbl.setVisible(false);
        stepLbl.setVisible(false);
        incrementField.setVisible(false);
        lastParLbl.setVisible(false);
        btnLessOptions.setVisible(false);
        btnMoreOptions.setVisible(true);
    }
    
    private void initCodeBlockLabel() {
        header.add(codeBlockName);
        header.add(indexLbl);
        header.add(fromLbl);
        header.add(lowerBoundField);
        header.add(upToLbl);
        header.add(upperBoundField);
        header.add(stepLbl);
        header.add(incrementField);
        header.add(lastParLbl);
        header.add(timesLabel);
        header.add(btnMoreOptions);
        header.add(btnLessOptions);
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getContext() {
        return context;
    }
    
    public void addChild(String childID, String context) {
        container.addChild(childID);
    }
    
    public void childRemoved(String childID, String context) {
        container.childRemoved(childID);
    }
    
    public void restoreChild(String childID, int index, String context) {
        container.restoreChild(childID, index);
    }
    
    public void valueAsIndex() {
        simpleFor();
        btnLessOptions.setVisible(false);
        btnMoreOptions.setVisible(false);
    }
    
    public void nothingAsIndex() {
        simpleFor();
        btnLessOptions.setVisible(false);
        btnMoreOptions.setVisible(true);
    }
    
    public void variableAsIndex() {
        simpleFor();
        btnLessOptions.setVisible(false);
        btnMoreOptions.setVisible(true);
    }
}
