package usp.ime.line.ivprog.view.utils.language;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceBundleIVP {
    private static final String         BUNDLE_NAME     = "usp.ime.line.ivprog.view.utils.language.ptBR";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
