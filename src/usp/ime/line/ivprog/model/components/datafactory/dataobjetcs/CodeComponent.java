package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class CodeComponent extends DataObject {
	public CodeComponent(String name, String description) {
		super(name, description);
	}

	public abstract void updateParent(String lastExp, String newExp, String operationContext);
}
