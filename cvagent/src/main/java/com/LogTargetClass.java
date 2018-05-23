package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class LogTargetClass {

    private final Set<String> excludes;
    private final Set<String> includes;
    private final Set<String> excludeMasks;
    private final Set<String> includeMasks;

    public LogTargetClass() {
        excludes = getResource("/org/exclude/excludes.txt");
        excludeMasks = getResource("/org/exclude/exclude_masks.txt");
        includes = getResource("/org/include/includes.txt");
        includeMasks = getResource("/org/include/include_masks.txt");

    }

    public boolean isTarget(String className) {

        className = className.replaceAll("/", ".");

        if (includes.contains(className)) {
            return true;
        }

        for (String includeMask : includeMasks) {
            if (className.startsWith(includeMask)) {
                return true;
            }
        }

        if (excludes.contains(className)) {
            return false;
        }

        for (String excludeMask : excludeMasks) {
            if (className.startsWith(excludeMask)) {
                return false;
            }
        }
        return true;
    }

    public Set<String> getResource(String filePath) {
        Set<String> excludes = new HashSet<String>();
        InputStream stream = null;
        BufferedReader reader = null;
        try {
            stream = LogTargetClass.class.getResourceAsStream(filePath);
            reader = new BufferedReader(new InputStreamReader(stream));
            String className;
            while ((className = reader.readLine()) != null) {
                excludes.add(className);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return excludes;
    }
}
