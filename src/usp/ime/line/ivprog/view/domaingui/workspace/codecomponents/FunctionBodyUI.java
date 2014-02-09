package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import usp.ime.line.ivprog.listeners.ICodeListener;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.CodeComposite;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;
import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.Function;
import usp.ime.line.ivprog.view.domaingui.variables.IVPVariablePanel;
import usp.ime.line.ivprog.view.domaingui.workspace.IVPContainer;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.peer.ScrollbarPeer;

import javax.swing.ScrollPaneConstants;

public class FunctionBodyUI extends JPanel implements ICodeListener {

    private static final long serialVersionUID = -1559611466195605109L;
    private String            name             = null;
    private String            type             = "-1";
    private IVPVariablePanel  variablesPanel;
    private IVPContainer      canvas;
    private JScrollPane       canvasHolder;
    private String            functionID       = null;

    public FunctionBodyUI(String functionID, boolean isMain) {
        this.functionID = functionID;
        setLayout(new BorderLayout(0, 0));
        variablesPanel = new IVPVariablePanel(functionID, isMain);
        add(variablesPanel, BorderLayout.NORTH);
        canvas = new IVPContainer(false, functionID);
        canvasHolder = new JScrollPane();
        canvasHolder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        canvasHolder.setViewportView(canvas);
        add(canvasHolder, BorderLayout.CENTER);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String string) {
        this.type = string;
    }

    public String getDataFunction() {
        return functionID;
    }

    public void setDataFunction(String function) {
        this.functionID = function;
    }

    public void addChild(String childID) {

        canvas.addChild(childID);
    }

    public void childRemoved(String childID) {
        canvas.childRemoved(childID);
    }

    public void restoreChild(String childID, int index) {
        canvas.restoreChild(childID, index);
    }

}