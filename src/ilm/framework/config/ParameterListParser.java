package ilm.framework.config;

import java.util.HashMap;
import java.util.Map;

public class ParameterListParser implements IParameterListParser {
    public Map Parse(String[] parameterList) {
        if (parameterList != null) {
            Map result = new HashMap();
            for (int i = 0; i < parameterList.length; i++) {
                if ((parameterList[i].startsWith("-")) && (i + 1 < parameterList.length)) {
                    String key = parameterList[i].substring(1);
                    String value = parameterList[i + 1];
                    result.put(key, value);
                    i++;
                }
            }
            return result;
        }
        return null;
    }
}
