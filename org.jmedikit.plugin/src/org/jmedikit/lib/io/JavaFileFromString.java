package org.jmedikit.lib.io;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaFileFromString extends SimpleJavaFileObject{

	final String code;

    public JavaFileFromString( String name, String code) {
        super( URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
	
}
