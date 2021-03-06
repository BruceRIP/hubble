package org.budgie.hubble.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Bruce Rip
 * @repositori <a href="https://github.com/Bruce29/">Bruce29</a>
 * @date 08 March 2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HubbleClient {
}
