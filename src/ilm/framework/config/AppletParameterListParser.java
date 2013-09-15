package ilm.framework.config;

import java.util.HashMap;
import java.util.Map;

public class AppletParameterListParser implements IParameterListParser {

    public Map Parse(String[] parameterList) {
        Map result = new HashMap();
        int equalsIndex;
        String key;
        String value;
        for (int i = 0; i < parameterList.length; i++) {
            equalsIndex = parameterList[i].lastIndexOf("=");
            key = parameterList[i].substring(0, equalsIndex);
            value = parameterList[i].substring(equalsIndex + 1);
            result.put(key, value);
        }
        return result;
    }
}
