package ilm.framework.modules.assignment;

import ilm.framework.modules.IlmModuleToolbar;

import java.util.Observable;

import javax.swing.JButton;

import usp.ime.line.ivprog.model.utils.Tracking;
import usp.ime.line.ivprog.view.utils.language.ResourceBundleIVP;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class UndoRedoModuleToolbar extends IlmModuleToolbar {
	private static final long serialVersionUID = 1L;
	private UndoRedoModule _undoRedo;
	private JButton _undoButton;
	private JButton _redoButton;

	public UndoRedoModuleToolbar() {
		_undoButton = makeButton("undo", "UNDO", ResourceBundleIVP.getString("undoBtn.Tip"), ResourceBundleIVP.getString("undoBtn.AltText"));
		add(_undoButton);
		_undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_undoRedo.undo();
				Tracking.getInstance().track("event=CLICK;where=BTN_UNDO;");
			}
		});
		_undoButton.setEnabled(false);
		_redoButton = makeButton("redo", "REDO", ResourceBundleIVP.getString("redoBtn.Tip"), ResourceBundleIVP.getString("redoBtn.AltText"));
		add(_redoButton);
		_redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_undoRedo.redo();
				Tracking.getInstance().track("event=CLICK;where=BTN_REDO;");
			}
		});
		_redoButton.setEnabled(false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}

	public void update(Observable o, Object arg) {
		if (o instanceof UndoRedoModule) {
			_undoRedo = (UndoRedoModule) o;
			updateUndoButton();
			updateRedoButton();
		}
	}

	private void updateUndoButton() {
		if (_undoRedo.isUndoStackEmpty()) {
			_undoButton.setEnabled(false);
		} else {
			_undoButton.setEnabled(true);
		}
	}

	private void updateRedoButton() {
		if (_undoRedo.isRedoStackEmpty()) {
			_redoButton.setEnabled(false);
		} else {
			_redoButton.setEnabled(true);
		}
	}
}
