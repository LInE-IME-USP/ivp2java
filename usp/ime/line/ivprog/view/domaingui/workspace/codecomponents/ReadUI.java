
/*
 * iVProg2 - interactive Visual Programming for the Internet
 * Java version
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Print;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.ReadData;
import usp.ime.line.ivprog.model.utils.Services;
import usp.ime.line.ivprog.view.FlatUIColors;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

public class ReadUI extends CodeBaseUI {

  private JPanel contentPanel;
  private JLabel codeBlockName;
  private String context;
  private VariableSelectorUI initialExpression;

  public ReadUI (String id, String parentID, String scopeID) {
    super(id);
    setModelParent(parentID);
    setModelScope(scopeID);
    setModelID(id);
    initialization(id);
    addComponents();
    }

  private void initialization (String id) {
    contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    codeBlockName = new JLabel(ResourceBundleIVP.getString("ReadUI.text"));
    setBackground(FlatUIColors.MAIN_BG);
    }

  private void addComponents () {
    contentPanel.setOpaque(false);
    contentPanel.add(codeBlockName);
    String writableExpression = ((ReadData) Services.getModelMapping().get(getModelID())).getWritableObject();
    initialExpression = (VariableSelectorUI) Services.getRenderer().paint(writableExpression);
    initialExpression.setIsolationMode(true);
    contentPanel.add(initialExpression);
    addContentPanel(contentPanel);
    }

  public void setContext (String context) {
    this.context = context;
    }

  public String getContext () {
    return context;
    }

  public boolean isContentSet () {
    boolean isCSet = true;
    if (!initialExpression.isContentSet()) {
      isCSet = false;
      }
    return isCSet;
    }

  public void lockDownCode () {
    initialExpression.editStateOff(initialExpression.getVarListSelectedItem());
    }

  }
