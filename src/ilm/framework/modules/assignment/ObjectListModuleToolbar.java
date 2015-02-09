package ilm.framework.modules.assignment;

import ilm.framework.modules.IlmModuleToolbar;

import java.util.Observable;

import javax.swing.JButton;

import usp.ime.line.ivprog.model.utils.Tracking;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class ObjectListModuleToolbar extends IlmModuleToolbar {
	private static final long serialVersionUID = 1L;
	private ObjectListModuleGUI _window;
	private JButton button;

	public ObjectListModuleToolbar() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		button = makeButton("objectlist", "OBJECT LIST", "Open the list of objects", "Object List window");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showWindow();
				Tracking.getInstance().track("event=CLICK;where=BTN_OBJECT_LIST;");
			}
		});
		// add(button);
		_window = new ObjectListModuleGUI();
	}

	private void showWindow() {
		_window.setVisible(true);
	}

	public void update(Observable o, Object arg) {
		if (_window != null) {
			_window.update(o, arg);
		}
	}
}
