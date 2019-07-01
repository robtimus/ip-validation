/*
 * IPv4AddressValidator.java
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

import java.util.Collection;
import javax.validation.ConstraintValidatorContext;
import com.github.robtimus.net.ip.IPAddress;
import com.github.robtimus.net.ip.IPv4Address;
import com.github.robtimus.net.ip.IPv4Range;

final class IPv4AddressValidator {

    private static final String DEFAULT_MESSAGE = "{com.github.robtimus.net.ip.validation.IPv4Address.message}"; //$NON-NLS-1$
    private static final String MESSAGE_WITH_IP_RANGES = "{com.github.robtimus.net.ip.validation.IPv4Address.message.withIPRanges}"; //$NON-NLS-1$

    private IPv4AddressValidator() {
        throw new Error("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    static boolean isValidIPv4Address(IPAddress<?> ipAddress, Collection<IPv4Range> ipRanges, String message, ConstraintValidatorContext context) {
        return ipAddress instanceof IPv4Address && isValidIPv4Address((IPv4Address) ipAddress, ipRanges, message, context);
    }

    static boolean isValidIPv4Address(IPv4Address ipAddress, Collection<IPv4Range> ipRanges, String message, ConstraintValidatorContext context) {
        if (ipRanges.isEmpty() || ipRangesContainIPv4Address(ipRanges, ipAddress)) {
            return true;
        }
        if (DEFAULT_MESSAGE.equals(message)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MESSAGE_WITH_IP_RANGES)
                    .addConstraintViolation();
        }
        return false;
    }

    private static boolean ipRangesContainIPv4Address(Collection<IPv4Range> ipRanges, IPv4Address ipAddress) {
        for (IPv4Range ipRange : ipRanges) {
            if (ipRange.contains(ipAddress)) {
                return true;
            }
        }
        return false;
    }
}
