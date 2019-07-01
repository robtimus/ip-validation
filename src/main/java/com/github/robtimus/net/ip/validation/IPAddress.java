/*
 * IPAddress.java
 * Copyright 2019 Rob Spoor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.robtimus.net.ip.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.github.robtimus.net.ip.validation.IPAddress.List;
import com.github.robtimus.net.ip.validation.validators.CharSequenceIPAddressValidator;
import com.github.robtimus.net.ip.validation.validators.IPAddressIPAddressValidator;

/**
 * Validates the annotated {@link CharSequence} is an IP address.
 * <p>
 * The {@link #ipRanges()} parameter allows filtering on IP ranges. For this reason, this annotation can also be applied to
 * {@link com.github.robtimus.net.ip.IPAddress IPAddresses}. If it is left empty, any IP address will be considered valid.
 *
 * @author Rob Spoor
 */
@Documented
@Constraint(validatedBy = { CharSequenceIPAddressValidator.class, IPAddressIPAddressValidator.class, })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface IPAddress {

    /**
     * The error message.
     */
    String message() default "{com.github.robtimus.net.ip.validation.IPAddress.message}";

    /**
     * The validation groups.
     */
    Class<?>[] groups() default { };

    /**
     * The payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * The optional IP ranges to filter on.
     * These must be valid CIDR notations (which can be defined for both IPv4 and IPv6), or these must be in format {@code [<from>...<to>]},
     * where {@code <from>} and {@code <to>} must either both be valid IPv4 addresses or both be valid IPv6 addresses.
     * It is an error to mix IPv4 and IPv6 addresses.
     */
    String[] ipRanges() default {};

    /**
     * Defines several {@link IPAddress} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {

        /**
         * The {@link IPAddress} annotations.
         */
        IPAddress[] value();
    }
}
