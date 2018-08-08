package ilm.framework.comm;

import java.util.Vector;

public interface IEncrypter {
	public Vector encryptFileContent(Vector fileContent);

	public Vector decryptFromFile(Vector fileContent);
}
