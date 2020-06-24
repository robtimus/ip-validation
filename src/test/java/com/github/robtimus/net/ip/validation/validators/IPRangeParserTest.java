/*
 * IPRangeParserTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import com.github.robtimus.net.ip.IPRange;
import com.github.robtimus.net.ip.IPv4Address;
import com.github.robtimus.net.ip.IPv4Range;
import com.github.robtimus.net.ip.IPv4Subnet;
import com.github.robtimus.net.ip.IPv6Address;
import com.github.robtimus.net.ip.Subnet;

@SuppressWarnings("nls")
class IPRangeParserTest {

    @TestFactory
    DynamicTest[] testParseIPRange() {
        return new DynamicTest[] {
                testParseIPRangeInvalid(""),
                testParseIPRange("192.168.0.0/24", Subnet.valueOf("192.168.0.0", 24)),
                testParseIPRangeInvalidCIDR("192.168.0.0/33"),
                testParseIPRange("[192.168.10.10...192.168.10.13]", IPv4Address.valueOf("192.168.10.10").to(IPv4Address.valueOf("192.168.10.13"))),
                testParseIPRangeInvalid("192.168.10.10...192.168.10.13]"),
                testParseIPRangeInvalid("[192.168.10.10...192.168.10.13"),
                testParseIPRangeInvalid("[192.168.10.10..192.168.10.13]"),
                testParseIPRangeInvalid("[192.168.10.10....192.168.10.13]"),
                testParseIPRangeInvalid("[192.168.10...192.168.10.13]"),
                testParseIPRangeInvalid("[192.168.10.10...192..10.13]"),
                testParseIPRange("1234:abcd::/64", Subnet.valueOf("1234:abcd::", 64)),
                testParseIPRangeInvalidCIDR("1234:abcd::/129"),
                testParseIPRange("[1234:abcd::0030...1234:abcd::ffff]",
                        IPv6Address.valueOf("1234:abcd::0030").to(IPv6Address.valueOf("1234:abcd::ffff"))),
                testParseIPRangeInvalid("1234:abcd::0030...1234:abcd::ffff]"),
                testParseIPRangeInvalid("[1234:abcd::0030...1234:abcd::ffff"),
                testParseIPRangeInvalid("[1234:abcd::0030..1234:abcd::ffff]"),
                testParseIPRangeInvalid("[1234:abcd::0030....1234:abcd::ffff]"),
                testParseIPRangeInvalid("[1234::abcd::0030...1234:abcd::ffff]"),
                testParseIPRangeInvalid("[1234:abcd::0030...1234::abcd::ffff]"),
                testParseIPRangeInvalid("[192.168.10.10...1234:abcd::ffff]"),
                testParseIPRangeInvalid("[::...192.168.10.10]"),
        };
    }

    private DynamicTest testParseIPRange(String ipRange, IPRange<?> expected) {
        return dynamicTest(ipRange, () -> {
            IPRange<?> parsed = IPRangeParser.parseIPRange(ipRange);
            assertEquals(expected, parsed);
        });
    }

    private DynamicTest testParseIPRangeInvalid(String ipRange) {
        return dynamicTest(ipRange.isEmpty() ? "empty" : ipRange, () -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> IPRangeParser.parseIPRange(ipRange));
            assertEquals(Messages.IPAddress.invalidIPRange.get(ipRange), exception.getMessage());
        });
    }

    private DynamicTest testParseIPRangeInvalidCIDR(String ipRange) {
        return dynamicTest(ipRange.isEmpty() ? "empty" : ipRange, () -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> IPRangeParser.parseIPRange(ipRange));
            assertNotEquals(Messages.IPAddress.invalidIPRange.get(ipRange), exception.getMessage());
        });
    }

    @TestFactory
    DynamicTest[] testParseIPv4Range() {
        return new DynamicTest[] {
                testParseIPv4RangeInvalid(""),
                testParseIPv4Range("192.168.0.0/24", IPv4Subnet.valueOf("192.168.0.0", 24)),
                testParseIPv4RangeInvalidCIDR("192.168.0.0/33"),
                testParseIPv4Range("[192.168.10.10...192.168.10.13]", IPv4Address.valueOf("192.168.10.10").to(IPv4Address.valueOf("192.168.10.13"))),
                testParseIPv4RangeInvalid("192.168.10.10...192.168.10.13]"),
                testParseIPv4RangeInvalid("[192.168.10.10...192.168.10.13"),
                testParseIPv4RangeInvalid("[192.168.10.10..192.168.10.13]"),
                testParseIPv4RangeInvalid("[192.168.10.10....192.168.10.13]"),
                testParseIPv4RangeInvalid("[192.168.10...192.168.10.13]"),
                testParseIPv4RangeInvalid("[192.168.10.10...192..10.13]"),
                testParseIPv4RangeInvalidCIDR("1234:abcd::/64"),
                testParseIPv4RangeInvalidCIDR("1234:abcd::/129"),
                testParseIPv4RangeInvalid("[1234:abcd::0030...1234:abcd::ffff]"),
                testParseIPv4RangeInvalid("1234:abcd::0030...1234:abcd::ffff]"),
                testParseIPv4RangeInvalid("[1234:abcd::0030...1234:abcd::ffff"),
                testParseIPv4RangeInvalid("[1234:abcd::0030..1234:abcd::ffff]"),
                testParseIPv4RangeInvalid("[1234:abcd::0030....1234:abcd::ffff]"),
                testParseIPv4RangeInvalid("[1234::abcd::0030...1234:abcd::ffff]"),
                testParseIPv4RangeInvalid("[1234:abcd::0030...1234::abcd::ffff]"),
                testParseIPv4RangeInvalid("[192.168.10.10...1234:abcd::ffff]"),
                testParseIPv4RangeInvalid("[::...192.168.10.10]"),
        };
    }

    private DynamicTest testParseIPv4Range(String ipRange, IPv4Range expected) {
        return dynamicTest(ipRange, () -> {
            IPRange<?> parsed = IPRangeParser.parseIPv4Range(ipRange);
            assertEquals(expected, parsed);
        });
    }

    private DynamicTest testParseIPv4RangeInvalid(String ipRange) {
        return dynamicTest(ipRange.isEmpty() ? "empty" : ipRange, () -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> IPRangeParser.parseIPv4Range(ipRange));
            assertEquals(Messages.IPAddress.invalidIPRange.get(ipRange), exception.getMessage());
        });
    }

    private DynamicTest testParseIPv4RangeInvalidCIDR(String ipRange) {
        return dynamicTest(ipRange.isEmpty() ? "empty" : ipRange, () -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> IPRangeParser.parseIPv4Range(ipRange));
            assertNotEquals(Messages.IPAddress.invalidIPRange.get(ipRange), exception.getMessage());
        });
    }

    @TestFactory
    DynamicTest[] testParseIPv6Range() {
        return new DynamicTest[] {
                testParseIPv6RangeInvalid(""),
                testParseIPv6RangeInvalidCIDR("192.168.0.0/24"),
                testParseIPv6RangeInvalidCIDR("192.168.0.0/33"),
                testParseIPv6RangeInvalid("[192.168.10.10...192.168.10.13]"),
                testParseIPv6RangeInvalid("192.168.10.10...192.168.10.13]"),
                testParseIPv6RangeInvalid("[192.168.10.10...192.168.10.13"),
                testParseIPv6RangeInvalid("[192.168.10.10..192.168.10.13]"),
                testParseIPv6RangeInvalid("[192.168.10.10....192.168.10.13]"),
                testParseIPv6RangeInvalid("[192.168.10...192.168.10.13]"),
                testParseIPv6RangeInvalid("[192.168.10.10...192..10.13]"),
                testParseIPv6Range("1234:abcd::/64", Subnet.valueOf("1234:abcd::", 64)),
                testParseIPv6RangeInvalidCIDR("1234:abcd::/129"),
                testParseIPv6Range("[1234:abcd::0030...1234:abcd::ffff]",
                        IPv6Address.valueOf("1234:abcd::0030").to(IPv6Address.valueOf("1234:abcd::ffff"))),
                testParseIPv6RangeInvalid("1234:abcd::0030...1234:abcd::ffff]"),
                testParseIPv6RangeInvalid("[1234:abcd::0030...1234:abcd::ffff"),
                testParseIPv6RangeInvalid("[1234:abcd::0030..1234:abcd::ffff]"),
                testParseIPv6RangeInvalid("[1234:abcd::0030....1234:abcd::ffff]"),
                testParseIPv6RangeInvalid("[1234::abcd::0030...1234:abcd::ffff]"),
                testParseIPv6RangeInvalid("[1234:abcd::0030...1234::abcd::ffff]"),
                testParseIPv6RangeInvalid("[192.168.10.10...1234:abcd::ffff]"),
                testParseIPv6RangeInvalid("[::...192.168.10.10]"),
        };
    }

    private DynamicTest testParseIPv6Range(String ipRange, IPRange<?> expected) {
        return dynamicTest(ipRange, () -> {
            IPRange<?> parsed = IPRangeParser.parseIPv6Range(ipRange);
            assertEquals(expected, parsed);
        });
    }

    private DynamicTest testParseIPv6RangeInvalid(String ipRange) {
        return dynamicTest(ipRange.isEmpty() ? "empty" : ipRange, () -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> IPRangeParser.parseIPv6Range(ipRange));
            assertEquals(Messages.IPAddress.invalidIPRange.get(ipRange), exception.getMessage());
        });
    }

    private DynamicTest testParseIPv6RangeInvalidCIDR(String ipRange) {
        return dynamicTest(ipRange.isEmpty() ? "empty" : ipRange, () -> {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> IPRangeParser.parseIPv6Range(ipRange));
            assertNotEquals(Messages.IPAddress.invalidIPRange.get(ipRange), exception.getMessage());
        });
    }
}
