package org.clevertec.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для доступа к пути точки входа в приложение
 *
 * @author Viktar Kvasha
 */

public class ApplicationStartUpPath {

    public Path getApplicationStartUp() throws UnsupportedEncodingException,
            MalformedURLException {
        URL startupUrl = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        Path path;
        try {
            path = Paths.get(startupUrl.toURI());
        } catch (Exception e) {
            try {
                path = Paths.get(new URL(startupUrl.getPath()).getPath());
            } catch (Exception ipe) {
                path = Paths.get(startupUrl.getPath());
            }
        }
        path = path.getParent();
        return path;
    }
}
