/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 * Create an attribution
 *   constructor calls 'initialization()' and 'addComponents()' in this order
 *   initialization(): expression = new ExpressionFieldUI(getModelID(), getModelScope()); varSelector = new VariableSelectorUI(getModelID());
 *   addComponents() : blockContent(): when crating a new attribution, it is borned with no right side, indicating the message "select a variable" (AttributionLineUI.lblNewLabel)
 * 
 * @see: ExpressionFieldUI.java: 
 *   private boolean isEditing = true;  // is under edition - can change varialbe
 *   private boolean isBlocked = false; // is not blocked   - the lock is open and the user can edit variables
 *   private void initExpressionHolder (String parent, String scope): expressionHolderUI = new ExpressionHolderUI(parent, scope);
 * @see: ExpressionHolderUI.java: create the expression (with operators and listeners)
 * @see: usp/ime/line/ivprog/view/IVPRenderer.java: AttributionLineUI attLine = new AttributionLineUI(?, attLineModel.getUniqueID(), attLineModel.getScopeID(), attLineModel.getParentID());
 * 
 */

package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.VariableReference;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.DynamicFlowLayout;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.Font;

public class AttributionLineUI extends CodeBaseUI {

  private JPanel contentPanel;
  private JLabel codeLabel;
  private VariableSelectorUI varSelector;
  private ExpressionFieldUI expression;
  private String context;
  private boolean isLeftVarSet = false;
  private JLabel blockedLabel;
  private String leftVarModelID;

  public AttributionLineUI (String id, String scope, String parent) {
    super(id);
    //D System.out.println("AttributionLineUI.java: " + id + ", " + parent + ", " + scope);
    //D try { String str=""; System.err.println(str.charAt(3)); } catch (Exception e1) { e1.printStackTrace(); }
    setModelID(id);
    setModelParent(parent);
    setModelScope(scope);
    initialization();
    addComponents();
    }

  private void initialization () {
    expression = new ExpressionFieldUI(getModelID(), getModelScope());
    contentPanel = new JPanel();
    codeLabel = new JLabel(ResourceBundleIVP.getString("AttLine.text"));
    varSelector = new VariableSelectorUI(getModelID());
    varSelector.setModelScope(getModelScope());
    varSelector.setIsolationMode(true);
    blockedLabel = new JLabel(ResourceBundleIVP.getString("AttributionLineUI.lblNewLabel.text")); // "select a variable"
    blockedLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
    blockedLabel.setForeground(Color.PINK);
    setBackground(FlatUIColors.MAIN_BG);
    }

  private void addComponents () {
    contentPanel.setOpaque(false);
    contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
    contentPanel.add(varSelector);
    contentPanel.add(codeLabel);
    contentPanel.add(expression);
    contentPanel.add(blockedLabel);
    blockContent(); // this means that the user doesn't get the right side of this expression //default is: editable when is created - keep this blocked
    addContentPanel(contentPanel);
    }

  public void blockContent () {
    codeLabel.setVisible(false);
    expression.setVisible(false);
    blockedLabel.setVisible(true);
    }

  public void unblockContent () {
    codeLabel.setVisible(true);
    expression.setVisible(true);
    expression.setHoldingType(((VariableReference) Services.getModelMapping().get(leftVarModelID)).getReferencedType());
    blockedLabel.setVisible(false);
    }

  public void setContext (String context) {
    this.context = context;
    }

  public String getContext () {
    return context;
    }

  public void setLeftVarModelID (String leftVariableID) {
    this.leftVarModelID = leftVariableID;
    varSelector.setModelID(leftVariableID);
    }

  public boolean isLeftVarSet () {
    return isLeftVarSet;
    }

  public void setLeftVarSet (boolean isLeftVarSet) {
    //D System.out.println("AttributionLineUI.java: setLeftVarSet: isLeftVarSet=" + isLeftVarSet);
    this.isLeftVarSet = isLeftVarSet;
    expression.setBlocked(!isLeftVarSet);
    if (isLeftVarSet) {
      unblockContent();
      }
    else {
      blockContent();
      }
    }

  public void updateHoldingType (short type) {
    expression.setHoldingType(type);
    }

  public boolean isContentSet () {
    boolean isCSet = true;
    if (!varSelector.isContentSet()) {
      isCSet = false;
      }
    if (!expression.isContentSet()) {
      isCSet = false;
      }
    return isCSet;
    }

  public void lockDownCode () {
    varSelector.editStateOff(varSelector.getVarListSelectedItem());
    expression.setEdition(false);
    }

  }
