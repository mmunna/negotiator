/*
 * Created by IntelliJ IDEA.
 * User: ahaduzzaman.munna
 * Date: 1/13/14
 * Time: 4:18 AM
 */
package com.amunna.negotiator.service.datacollect.wiring;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention (RetentionPolicy.RUNTIME)
@Target ({ ElementType.FIELD, ElementType.PARAMETER })
@BindingAnnotation
public @interface PriceCollectExecutor {
}
