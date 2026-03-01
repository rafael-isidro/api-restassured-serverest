package org.rafaelisidro.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Manipulation {

    private Manipulation() {}

    public static Properties getProp() {
        Properties props = new Properties();

        try (FileInputStream file = new FileInputStream("src/main/resources/application-test.properties")){
            props.load(file);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return props;
    }
}