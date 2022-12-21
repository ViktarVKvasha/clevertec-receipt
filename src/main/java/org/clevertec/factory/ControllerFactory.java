package org.clevertec.factory;

import org.clevertec.controller.Controller;

/**
 * Абстрактная фабрика контроллеров
 *
 * @param <T> тип оъекта
 * @author Viktar Kvasha
 */

public abstract class ControllerFactory<T> {

    protected String startupPath;

    public void loadStartupPath(String path) {
        this.startupPath = path;
    }

    public Controller<T> orderController(InputType inputType) {
        Controller<T> controller;
        controller = createController(inputType);
        return controller;
    }

    protected abstract Controller<T> createController(InputType inputType);
}
