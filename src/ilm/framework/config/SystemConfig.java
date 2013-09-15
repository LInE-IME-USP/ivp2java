package ilm.framework.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Properties;

public final class SystemConfig extends Observable implements Serializable {

    private static final String CUSTOM_PROPERTIES_KEY = "config";
    private Properties _parameters;
    private boolean _isApplet;
    private Locale _currentLocale;

    public SystemConfig(boolean isApplet, Map parameterList) {
        this(isApplet, parameterList, null);
    }

    public SystemConfig(boolean isApplet, Map parameterList, Properties properties) {
        if (properties != null) {
            _parameters = properties;
        } else {
            try {
                if (!isApplet) {
                    if (parameterList.containsKey(CUSTOM_PROPERTIES_KEY)) {
                        _parameters = new Properties();
                        _parameters.load(getClass().getResourceAsStream(
                                (String) parameterList.get(CUSTOM_PROPERTIES_KEY)));
                    } else {
                        _parameters = getDefaultProperties();
                    }
                } else {
                    _parameters = new Properties();
                    _parameters.putAll(parameterList);
                }
            } catch (InvalidParameterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        _isApplet = isApplet;
        setProperties(parameterList);
        setLanguage(_parameters.getProperty("language"));
    }

    private Properties getDefaultProperties() throws InvalidParameterException,
            FileNotFoundException, IOException {
        Properties defaultProperties = new Properties();
        defaultProperties.load(getClass().getResourceAsStream("defaultConfig.properties"));
        return defaultProperties;
    }

    private void setProperties(Map parameterList) {
        Iterator parameterIterator = parameterList.entrySet().iterator();
        while (parameterIterator.hasNext()) {
            Map.Entry entry = (Entry) parameterIterator.next();
            _parameters.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
    }

    public void setParameter(String key, String value) {
        _parameters.setProperty(key, value);
        setChanged();
        notifyObservers();
    }

    public void setLanguage(String language) {
        _currentLocale = new Locale(language);
        setChanged();
        notifyObservers();
    }

    public String getValue(String key) {
        return _parameters.getProperty(key);
    }

    public Locale getLanguage() {
        return _currentLocale;
    }

    public boolean isApplet() {
        return _isApplet;
    }

    public String toString() {
        String string = "";
        Enumeration e = _parameters.propertyNames();
        while (e.hasMoreElements()) {
            String s = (String) e.nextElement();
            string += "<" + s + ">" + _parameters.getProperty(s) + "</" + s + ">";
        }
        return string;
    }
}
