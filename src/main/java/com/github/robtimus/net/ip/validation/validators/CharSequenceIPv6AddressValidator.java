/*
 * CharSequenceIPv6AddressValidator.java
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

import static com.github.robtimus.net.ip.validation.validators.IPRangeParser.parseIPv6Ranges;
import static com.github.robtimus.net.ip.validation.validators.IPv6AddressValidator.isValidIPv6Address;
import java.util.Collection;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.github.robtimus.net.ip.IPAddressFormatter;
import com.github.robtimus.net.ip.IPv6Range;
import com.github.robtimus.net.ip.validation.IPv6Address;

/**
 * An {@link IPv6Address} constraint validator for {@link CharSequence}.
 *
 * @author Rob Spoor
 */
public class CharSequenceIPv6AddressValidator implements ConstraintValidator<IPv6Address, CharSequence> {

    private String message;
    private Collection<IPv6Range> ipRanges;

    @Override
    public void initialize(IPv6Address constraintAnnotation) {
        message = constraintAnnotation.message();
        ipRanges = parseIPv6Ranges(constraintAnnotation);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (ipRanges.isEmpty()) {
            return com.github.robtimus.net.ip.IPv6Address.isIPv6Address(value);
        }
        return IPAddressFormatter.ipv6WithDefaults().tryParse(value)
                .map(ip -> isValidIPv6Address(ip, ipRanges, message, context))
                .orElse(false);
    }
}
