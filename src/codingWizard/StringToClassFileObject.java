package codingWizard;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * Created by PC on 10.05.2017.
 */
public class StringToClassFileObject extends SimpleJavaFileObject {

    final String code;

    protected StringToClassFileObject(String name, String code) {
        super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}
