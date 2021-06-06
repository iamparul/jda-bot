package org.bot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertyReader {
    private final Properties configProp = new Properties();

    private PropertyReader() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("data.properties");
        System.out.println("Reading all properties from the file");
        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LazyHolder {
        private static final PropertyReader INSTANCE = new PropertyReader();
    }

    public static PropertyReader getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key) {
        return configProp.containsKey(key);
    }
}

