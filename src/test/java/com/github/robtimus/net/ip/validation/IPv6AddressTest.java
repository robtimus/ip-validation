/*
 * IPv6AddressTest.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

@SuppressWarnings("nls")
class IPv6AddressTest extends AbstractConstraintTest {

    @Nested
    class ForCharSequence {

        @Test
        void testNull() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequence", null);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        void testInvalidIPv6Address() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequence", "127.0.0.1");
            assertEquals(1, violations.size());

            ConstraintViolation<TestClass> violation = violations.get(0);
            assertAnnotation(violation, IPv6Address.class);
            assertEquals("must be a valid IPv6 address", violation.getMessage());
        }

        @Test
        void testValidWithoutIPRanges() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequence", "::1");
            assertEquals(Collections.emptyList(), violations);
        }

        @TestFactory
        DynamicTest[] testValidWithSubnet() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithSubnet", "1234:abcd::13");
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithSubnet", "1234:abce::13");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv6Address.class);
                        assertEquals("must be an IPv6 address in one of [1234:abcd::/32]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testValidWithIPRange() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithIPRange", "1234:5678::11");
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithIPRange", "1234:5678::14");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv6Address.class);
                        assertEquals("must be an IPv6 address in one of [[1234:5678::10...1234:5678::13]]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testInvalidWithCustomMessage() {
            return new DynamicTest[] {
                    dynamicTest("invalid format", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithCustomMessage", "127.0.0.1");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv6Address.class);
                        assertEquals("custom", violation.getMessage());
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithCustomMessage", "1234:5678::14");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv6Address.class);
                        assertEquals("custom", violation.getMessage());
                    }),
            };
        }
    }

    @Nested
    class ForIPAddress {

        @Test
        void testNull() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddress", null);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        void testWrongType() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddress", ip("127.0.0.1"));
            assertEquals(1, violations.size());

            ConstraintViolation<TestClass> violation = violations.get(0);
            assertAnnotation(violation, IPv6Address.class);
            assertEquals("must be a valid IPv6 address", violation.getMessage());
        }

        @Test
        void testValidWithoutIPRanges() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddress", ip("::1"));
            assertEquals(Collections.emptyList(), violations);
        }

        @TestFactory
        DynamicTest[] testValidWithSubnet() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithSubnet", ip("1234:abcd::13"));
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithSubnet", ip("1234:abce::13"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv6Address.class);
                        assertEquals("must be an IPv6 address in one of [1234:abcd::/32]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testValidWithIPRange() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithIPRange", ip("1234:5678::11"));
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithIPRange", ip("1234:5678::14"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv6Address.class);
                        assertEquals("must be an IPv6 address in one of [[1234:5678::10...1234:5678::13]]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testInvalidWithCustomMessage() {
            return new DynamicTest[] {
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithCustomMessage",
                                ip("1234:5678::14"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv6Address.class);
                        assertEquals("custom", violation.getMessage());
                    }),
            };
        }

        private com.github.robtimus.net.ip.IPAddress<?> ip(String value) {
            return com.github.robtimus.net.ip.IPAddress.valueOf(value);
        }
    }

    private static final class TestClass {

        @IPv6Address
        private CharSequence charSequence;

        @IPv6Address(ipRanges = "1234:abcd::/32")
        private CharSequence charSequenceWithSubnet;

        @IPv6Address(ipRanges = "[1234:5678::10...1234:5678::13]")
        private CharSequence charSequenceWithIPRange;

        @IPv6Address(ipRanges = { "1234:abcd::/32", "[1234:5678::10...1234:5678::13]" }, message = "custom")
        private CharSequence charSequenceWithCustomMessage;

        @IPv6Address
        private com.github.robtimus.net.ip.IPAddress<?> ipAddress;

        @IPv6Address(ipRanges = "1234:abcd::/32")
        private com.github.robtimus.net.ip.IPAddress<?> ipAddressWithSubnet;

        @IPv6Address(ipRanges = "[1234:5678::10...1234:5678::13]")
        private com.github.robtimus.net.ip.IPAddress<?> ipAddressWithIPRange;

        @IPv6Address(ipRanges = { "1234:abcd::/32", "[1234:5678::10...1234:5678::13]" }, message = "custom")
        private com.github.robtimus.net.ip.IPAddress<?> ipAddressWithCustomMessage;
    }
}
