/*
 * IPv6Address.java
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
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.github.robtimus.net.ip.IPAddress;
import com.github.robtimus.net.ip.validation.IPv6Address.List;
import com.github.robtimus.net.ip.validation.validators.CharSequenceIPv6AddressValidator;
import com.github.robtimus.net.ip.validation.validators.IPAddressIPv6AddressValidator;

/**
 * Validates the annotated {@link CharSequence} or {@link IPAddress} is an IPv6 address.
 * <p>
 * The {@link #ipRanges()} parameter allows filtering on IP ranges. If it is left empty, any IPv6 address will be considered valid.
 *
 * @author Rob Spoor
 */
@Documented
@Constraint(validatedBy = { CharSequenceIPv6AddressValidator.class, IPAddressIPv6AddressValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface IPv6Address {

    /**
     * The error message.
     */
    String message() default "{com.github.robtimus.net.ip.validation.IPv6Address.message}";

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
     * These must be valid IPv6 CIDR notations, or these must be in format {@code [<from>...<to>]},
     * where {@code <from>} and {@code <to>} must both be valid IPv6 addresses.
     */
    String[] ipRanges() default {};

    /**
     * Defines several {@link IPv6Address} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * The {@link IPv6Address} annotations.
         */
        IPv6Address[] value();
    }
}
