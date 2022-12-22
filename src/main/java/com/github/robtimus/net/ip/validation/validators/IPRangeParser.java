/*
 * IPRangeParser.java
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

import static java.util.stream.Collectors.toList;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import com.github.robtimus.net.ip.IPAddress;
import com.github.robtimus.net.ip.IPAddressFormatter;
import com.github.robtimus.net.ip.IPRange;
import com.github.robtimus.net.ip.IPv4Address;
import com.github.robtimus.net.ip.IPv4Range;
import com.github.robtimus.net.ip.IPv4Subnet;
import com.github.robtimus.net.ip.IPv6Address;
import com.github.robtimus.net.ip.IPv6Range;
import com.github.robtimus.net.ip.IPv6Subnet;
import com.github.robtimus.net.ip.Subnet;

final class IPRangeParser {

    private IPRangeParser() {
        throw new IllegalStateException("cannot create instances of " + getClass().getName()); //$NON-NLS-1$
    }

    static Collection<IPRange<?>> parseIPRanges(com.github.robtimus.net.ip.validation.IPAddress constraintAnnotation) {
        return Arrays.stream(constraintAnnotation.ipRanges())
                .map(IPRangeParser::parseIPRange)
                .collect(toList());
    }

    static IPRange<?> parseIPRange(String ipRange) {
        return parseIPRange(ipRange, Subnet::valueOf, IPAddressFormatter.anyVersionWithDefaults(), (from, to) -> createRange(from, to, ipRange));
    }

    @SuppressWarnings("unchecked")
    private static <IP extends IPAddress<IP>> IPRange<?> createRange(IPAddress<?> from, IPAddress<?> to, String ipRange) {
        if (from.getClass() != to.getClass()) {
            throw new IllegalArgumentException(Messages.IPAddress.invalidIPRange(ipRange));
        }
        // from and to are of the same class, so the cast is safe
        return ((IP) from).to((IP) to);
    }

    static Collection<IPv4Range> parseIPv4Ranges(com.github.robtimus.net.ip.validation.IPv4Address constraintAnnotation) {
        return Arrays.stream(constraintAnnotation.ipRanges())
                .map(IPRangeParser::parseIPv4Range)
                .collect(toList());
    }

    static IPv4Range parseIPv4Range(String ipRange) {
        return parseIPRange(ipRange, IPv4Subnet::valueOf, IPAddressFormatter.ipv4(), IPv4Address::to);
    }

    static Collection<IPv6Range> parseIPv6Ranges(com.github.robtimus.net.ip.validation.IPv6Address constraintAnnotation) {
        return Arrays.stream(constraintAnnotation.ipRanges())
                .map(IPRangeParser::parseIPv6Range)
                .collect(toList());
    }

    static IPv6Range parseIPv6Range(String ipRange) {
        return parseIPRange(ipRange, IPv6Subnet::valueOf, IPAddressFormatter.ipv6WithDefaults(), IPv6Address::to);
    }

    private static <IP extends IPAddress<?>, R extends IPRange<?>> R parseIPRange(String ipRange,
            Function<CharSequence, R> subnetParser,
            IPAddressFormatter<IP> formatter,
            BiFunction<IP, IP, R> ipRangeConstructor) {

        if (ipRange.indexOf('/') != -1) {
            return subnetParser.apply(ipRange);
        }

        if (!ipRange.startsWith("[") || !ipRange.endsWith("]")) { //$NON-NLS-1$ //$NON-NLS-2$
            throw new IllegalArgumentException(Messages.IPAddress.invalidIPRange(ipRange));
        }
        int index = ipRange.indexOf("..."); //$NON-NLS-1$
        if (index == -1) {
            throw new IllegalArgumentException(Messages.IPAddress.invalidIPRange(ipRange));
        }
        ParsePosition position = new ParsePosition(1);
        IP from = formatter.parse(ipRange, position);
        if (from == null || position.getIndex() != index) {
            throw new IllegalArgumentException(Messages.IPAddress.invalidIPRange(ipRange));
        }
        position.setIndex(index + 3);
        IP to = formatter.parse(ipRange, position);
        if (to == null || position.getIndex() != ipRange.length() - 1) {
            throw new IllegalArgumentException(Messages.IPAddress.invalidIPRange(ipRange));
        }
        return ipRangeConstructor.apply(from, to);
    }
}
