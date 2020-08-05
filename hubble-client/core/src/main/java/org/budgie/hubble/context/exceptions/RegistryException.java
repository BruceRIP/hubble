package org.budgie.hubble.context.exceptions;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class RegistryException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(Throwable cause) {
        super(cause);
    }

}
