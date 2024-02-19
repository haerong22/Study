package org.example.mvc;

import org.example.mvc.controller.HandlerKey;

public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}
