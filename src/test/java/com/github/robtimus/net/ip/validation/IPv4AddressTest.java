/*
 * IPv4AddressTest.java
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
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

@SuppressWarnings("nls")
class IPv4AddressTest extends AbstractConstraintTest {

    @Nested
    class ForCharSequence {

        @Test
        void testNull() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequence", null);
            assertEquals(Collections.emptyList(), violations);
        }

        @Test
        void testInvalidIPv4Address() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequence", "::1");
            assertEquals(1, violations.size());

            ConstraintViolation<TestClass> violation = violations.get(0);
            assertAnnotation(violation, IPv4Address.class);
            assertEquals("must be a valid IPv4 address", violation.getMessage());
        }

        @Test
        void testValidWithoutIPRanges() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequence", "127.0.0.1");
            assertEquals(Collections.emptyList(), violations);
        }

        @TestFactory
        DynamicTest[] testValidWithSubnet() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithSubnet", "192.168.0.13");
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithSubnet", "192.168.1.13");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv4Address.class);
                        assertEquals("must be an IPv4 address in one of [192.168.0.0/24]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testValidWithIPRange() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithIPRange", "192.168.10.11");
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithIPRange", "192.168.10.14");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv4Address.class);
                        assertEquals("must be an IPv4 address in one of [[192.168.10.10...192.168.10.13]]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testInvalidWithCustomMessage() {
            return new DynamicTest[] {
                    dynamicTest("invalid format", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithCustomMessage", "::1");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv4Address.class);
                        assertEquals("custom", violation.getMessage());
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "charSequenceWithCustomMessage", "192.168.10.14");
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv4Address.class);
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
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddress", ip("::1"));
            assertEquals(1, violations.size());

            ConstraintViolation<TestClass> violation = violations.get(0);
            assertAnnotation(violation, IPv4Address.class);
            assertEquals("must be a valid IPv4 address", violation.getMessage());
        }

        @Test
        void testValidWithoutIPranges() {
            List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddress", ip("127.0.0.1"));
            assertEquals(Collections.emptyList(), violations);
        }

        @TestFactory
        DynamicTest[] testValidWithSubnet() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithSubnet", ip("192.168.0.13"));
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithSubnet", ip("192.168.1.13"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv4Address.class);
                        assertEquals("must be an IPv4 address in one of [192.168.0.0/24]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testValidWithIPRange() {
            return new DynamicTest[] {
                    dynamicTest("contains", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithIPRange", ip("192.168.10.11"));
                        assertEquals(Collections.emptyList(), violations);
                    }),
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithIPRange", ip("192.168.10.14"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv4Address.class);
                        assertEquals("must be an IPv4 address in one of [[192.168.10.10...192.168.10.13]]", violation.getMessage());
                    }),
            };
        }

        @TestFactory
        DynamicTest[] testInvalidWithCustomMessage() {
            return new DynamicTest[] {
                    dynamicTest("doesn't contain", () -> {
                        List<ConstraintViolation<TestClass>> violations = validate(TestClass.class, "ipAddressWithCustomMessage",
                                ip("192.168.10.14"));
                        assertEquals(1, violations.size());

                        ConstraintViolation<TestClass> violation = violations.get(0);
                        assertAnnotation(violation, IPv4Address.class);
                        assertEquals("custom", violation.getMessage());
                    }),
            };
        }

        private com.github.robtimus.net.ip.IPAddress<?> ip(String value) {
            return com.github.robtimus.net.ip.IPAddress.valueOf(value);
        }
    }

    private static final class TestClass {

        @IPv4Address
        private CharSequence charSequence;

        @IPv4Address(ipRanges = "192.168.0.0/24")
        private CharSequence charSequenceWithSubnet;

        @IPv4Address(ipRanges = "[192.168.10.10...192.168.10.13]")
        private CharSequence charSequenceWithIPRange;

        @IPv4Address(ipRanges = { "192.168.0.0/24", "[192.168.10.10...192.168.10.13]" }, message = "custom")
        private CharSequence charSequenceWithCustomMessage;

        @IPv4Address
        private com.github.robtimus.net.ip.IPAddress<?> ipAddress;

        @IPv4Address(ipRanges = "192.168.0.0/24")
        private com.github.robtimus.net.ip.IPAddress<?> ipAddressWithSubnet;

        @IPv4Address(ipRanges = "[192.168.10.10...192.168.10.13]")
        private com.github.robtimus.net.ip.IPAddress<?> ipAddressWithIPRange;

        @IPv4Address(ipRanges = { "192.168.0.0/24", "[192.168.10.10...192.168.10.13]" }, message = "custom")
        private com.github.robtimus.net.ip.IPAddress<?> ipAddressWithCustomMessage;
    }
}
