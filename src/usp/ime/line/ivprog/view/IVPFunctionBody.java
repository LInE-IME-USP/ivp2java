package usp.ime.line.ivprog.view;

import javax.swing.JPanel;

import usp.ime.line.ivprog.model.components.datafactory.dataobjetcs.DataObject;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class IVPFunctionBody extends JPanel {
	
	private static final long serialVersionUID = -1559611466195605109L;
	private String name = null;
	private short type = -1;
	private JPanel variablesPanel;
	private IVPCanvas canvas;
	private DataObject function = null;
	
	public IVPFunctionBody() {
		setLayout(new BorderLayout(0, 0));
		variablesPanel = new JPanel();
		add(variablesPanel, BorderLayout.NORTH);
		canvas = new IVPCanvas();
		add(canvas, BorderLayout.CENTER);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public DataObject getDataFunction() {
		return function;
	}

	public void setDataFunction(DataObject function) {
		this.function = function;
	}

	
}