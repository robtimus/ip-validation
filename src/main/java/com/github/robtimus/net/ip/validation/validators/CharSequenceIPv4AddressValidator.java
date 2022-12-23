/*
 * CharSequenceIPv4AddressValidator.java
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

import static com.github.robtimus.net.ip.validation.validators.IPRangeParser.parseIPv4Ranges;
import static com.github.robtimus.net.ip.validation.validators.IPv4AddressValidator.isValidIPv4Address;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.github.robtimus.net.ip.IPAddressFormatter;
import com.github.robtimus.net.ip.IPv4Range;
import com.github.robtimus.net.ip.validation.IPv4Address;

/**
 * An {@link IPv4Address} constraint validator for {@link CharSequence}.
 *
 * @author Rob Spoor
 */
public class CharSequenceIPv4AddressValidator implements ConstraintValidator<IPv4Address, CharSequence> {

    private String message;
    private Collection<IPv4Range> ipRanges;

    @Override
    public void initialize(IPv4Address constraintAnnotation) {
        message = constraintAnnotation.message();
        ipRanges = parseIPv4Ranges(constraintAnnotation);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (ipRanges.isEmpty()) {
            return com.github.robtimus.net.ip.IPv4Address.isIPv4Address(value);
        }
        return IPAddressFormatter.ipv4().tryParse(value)
                .map(ip -> isValidIPv4Address(ip, ipRanges, message, context))
                .orElse(false);
    }
}
