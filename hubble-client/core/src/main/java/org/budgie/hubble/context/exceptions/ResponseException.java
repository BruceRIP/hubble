package org.budgie.hubble.context.exceptions;

/**
 * @author Bruce Rip
 * @repository <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
public class ResponseException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ResponseException(String message) {
        super(message);
    }

}
