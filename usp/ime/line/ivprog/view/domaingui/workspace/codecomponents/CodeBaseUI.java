
/*
 * iVProg2 - interactive Visual Programming to the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.IfElse;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariableBasic;
import usp.ime.line.ivprog.view.utils.GripArea;
import usp.ime.line.ivprog.view.utils.IconButtonUI;
import usp.ime.line.ivprog.view.utils.RoundedJPanel;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public abstract class CodeBaseUI extends RoundedJPanel implements IDomainObjectUI {

  private String parentModelID;
  private String thisModelID;
  private String scopeModelID;
  private JPanel contentPanel;
  private JPanel gripArea;
  private JPanel trashCanPanel;
  private JPanel panel;


  // Class with graphical component to each command of iVProg2
  // Called by: usp/ime/line/ivprog/view/IVPRenderer.java: renderFor(...)
  // Called by: usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/AttributionLineUI.java
  // Called by: usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/ForUI.java
  // Called by: usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/IfElseUI.java
  // Called by: usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/PrintUI.java
  // Called by: usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/ReadUI.java
  // Called by: usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/WhileUI.java
  public CodeBaseUI (String modelID) {
    if (usp.ime.line.ivprog.Ilm.DEBUG)
      System.out.println("./usp/ime/line/ivprog/view/domaingui/workspace/codecomponents/CodeBaseUI.java: CodeBaseUI(" + modelID + ")");
    thisModelID = modelID;
    setLayout(new BorderLayout());
    setArcs(new Dimension(15, 15));
    initGripArea();
    initCompositePanel();
    initTrashCan();
    addMouseListener(Services.getML());
    addMouseMotionListener((MouseMotionListener) Services.getML());
    }

  private void initTrashCan () {
    trashCanPanel = new JPanel(new BorderLayout());
    Action action = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        // need to check
        Tracking.track("event=CLICK;where=BTN_CODE_TRASHCAN;");
        if (Services.getModelMapping().get(parentModelID) instanceof IfElse) {
          IfElse ifelse = (IfElse) Services.getModelMapping().get(parentModelID);
          String context = ifelse.getChildContext(thisModelID);
          Services.getController().removeChild(parentModelID, thisModelID, context);
          } else {
          Services.getController().removeChild(parentModelID, thisModelID, "");
          }
        }
      };
    action.putValue(Action.SMALL_ICON, new ImageIcon(CodeBaseUI.class.getResource("/usp/ime/line/resources/icons/trash16x16.png")));
    action.putValue(Action.SHORT_DESCRIPTION, ResourceBundleIVP.getString("removeComponent"));
    JButton btn = new JButton(action);
    btn.setUI(new IconButtonUI());
    trashCanPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    trashCanPanel.setOpaque(false);
    trashCanPanel.add(btn, BorderLayout.NORTH);
    add(trashCanPanel, BorderLayout.EAST);
    }

  private void initCompositePanel () {
    contentPanel = new JPanel(new BorderLayout());
    contentPanel.setOpaque(false);
    contentPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    add(contentPanel, BorderLayout.CENTER);
    }

  private void initGripArea () {
    GripArea grip = new GripArea(thisModelID);
    gripArea = new JPanel();
    BorderLayout bl_gripArea = new BorderLayout();
    bl_gripArea.setHgap(2);
    gripArea.setLayout(bl_gripArea);
    gripArea.add(grip, BorderLayout.CENTER);
    gripArea.setBorder(new EmptyBorder(5, 5, 5, 5));
    gripArea.add(grip, BorderLayout.CENTER);
    gripArea.setOpaque(false);
    add(gripArea, BorderLayout.WEST);
    }

  public String getModelID () {
    return thisModelID;
    }

  public String getModelParent () {
    return parentModelID;
    }

  public String getModelScope () {
    return scopeModelID;
    }

  public void setModelID (String id) {
    thisModelID = id;
    }

  public void setModelParent (String id) {
    parentModelID = id;
    }

  public void setModelScope (String id) {
    scopeModelID = id;
    }

  protected void addContentPanel (JPanel panel) {
    contentPanel.add(panel);
    }

  }
