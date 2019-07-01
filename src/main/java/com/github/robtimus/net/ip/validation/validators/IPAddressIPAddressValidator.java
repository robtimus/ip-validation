/*
 * IPAddressIPAddressValidator.java
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

package com.github.robtimus.net.ip.validation.validators;

import static com.github.robtimus.net.ip.validation.validators.IPAddressValidator.isValidIPAddress;
import static com.github.robtimus.net.ip.validation.validators.IPRangeParser.parseIPRanges;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.github.robtimus.net.ip.IPAddress;
import com.github.robtimus.net.ip.IPRange;

/**
 * An {@link com.github.robtimus.net.ip.validation.IPAddress IPAddress} constraint validator for {@link com.github.robtimus.net.ip.IPAddress}.
 *
 * @author Rob Spoor
 */
public class IPAddressIPAddressValidator implements ConstraintValidator<com.github.robtimus.net.ip.validation.IPAddress, IPAddress<?>> {

    private String message;
    private Collection<IPRange<?>> ipRanges;

    @Override
    public void initialize(com.github.robtimus.net.ip.validation.IPAddress constraintAnnotation) {
        message = constraintAnnotation.message();
        ipRanges = parseIPRanges(constraintAnnotation);
    }

    @Override
    public boolean isValid(IPAddress<?> value, ConstraintValidatorContext context) {
        return value == null || isValidIPAddress(value, ipRanges, message, context);
    }
}
