/*
 * IPAddressValidator.java
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
import jakarta.validation.ConstraintValidatorContext;
import com.github.robtimus.net.ip.IPAddress;
import com.github.robtimus.net.ip.IPRange;

final class IPAddressValidator {

    private static final String DEFAULT_MESSAGE = "{com.github.robtimus.net.ip.validation.IPAddress.message}"; //$NON-NLS-1$
    private static final String MESSAGE_WITH_IP_RANGES = "{com.github.robtimus.net.ip.validation.IPAddress.message.withIPRanges}"; //$NON-NLS-1$

    private IPAddressValidator() {
        throw new IllegalStateException("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    static boolean isValidIPAddress(IPAddress<?> ipAddress, Collection<IPRange<?>> ipRanges, String message, ConstraintValidatorContext context) {
        if (ipRanges.isEmpty() || ipRangesContainIPAddress(ipRanges, ipAddress)) {
            return true;
        }
        if (DEFAULT_MESSAGE.equals(message)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MESSAGE_WITH_IP_RANGES)
                    .addConstraintViolation();
        }
        return false;
    }

    private static boolean ipRangesContainIPAddress(Collection<IPRange<?>> ipRanges, IPAddress<?> ipAddress) {
        for (IPRange<?> ipRange : ipRanges) {
            if (ipRange.contains(ipAddress)) {
                return true;
            }
        }
        return false;
    }
}
