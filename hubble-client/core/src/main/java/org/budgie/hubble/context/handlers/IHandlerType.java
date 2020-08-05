package org.budgie.hubble.context.handlers;

import javax.servlet.ServletContext;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public interface IHandlerType {

    public void execute(final ServletContext servletContext);
}
