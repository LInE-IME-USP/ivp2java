package ilm.framework.modules.assignment;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.DomainObject;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

public class ObjectListModuleGUI extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private ObjectListModule _objectList;
    private JPanel contentPane;
    private JList list;

    public ObjectListModuleGUI() {
        setBounds(100, 100, 200, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        list = new JList();
        contentPane.add(list, BorderLayout.CENTER);
        setTitle(IlmProtocol.OBJECT_LIST_MODULE_NAME);
    }

    public void update(Observable o, Object arg) {
        if (o instanceof ObjectListModule) {
            _objectList = (ObjectListModule) o;
            // TODO need a better non-brute force way to do this
            DefaultListModel listModel = new DefaultListModel();
            for (int i = 0; i < _objectList.getObjectList().size(); i++) {
                if (((DomainObject) _objectList.getObjectList().get(i)) != null)
                    listModel.addElement(((DomainObject) _objectList.getObjectList().get(i))
                            .getDescription());
            }
            list.setModel(listModel);
        }
    }
}
