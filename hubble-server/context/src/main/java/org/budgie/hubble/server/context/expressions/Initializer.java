package org.budgie.hubble.server.context.expressions;

@FunctionalInterface
public interface Initializer {

    public boolean run(Class clazz);
}
