/*
 * AbstractConstraintTest.java
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

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.Comparator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;

abstract class AbstractConstraintTest {

    private static final Validator VALIDATOR = Validation.byDefaultProvider()
            .configure()
            .traversableResolver(new SimpleTraversableResolver())
            .buildValidatorFactory()
            .getValidator();

    private static final Comparator<ConstraintViolation<?>> BY_PATH = comparing((ConstraintViolation<?> v) -> v.getPropertyPath().toString());
    private static final Comparator<ConstraintViolation<?>> BY_ANNOTATION = comparing(
            (ConstraintViolation<?> v) -> v.getConstraintDescriptor().getAnnotation().annotationType().toString());
    private static final Comparator<ConstraintViolation<?>> COMPARATOR = BY_PATH.thenComparing(BY_ANNOTATION);

    protected <T> List<ConstraintViolation<T>> validate(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        return VALIDATOR.validateValue(beanType, propertyName, value, groups).stream()
                .sorted(COMPARATOR)
                .collect(toList());
    }

    protected void assertAnnotation(ConstraintViolation<?> violation, Class<? extends Annotation> annotationType) {
        assertNotNull(violation.getConstraintDescriptor());
        assertNotNull(violation.getConstraintDescriptor().getAnnotation());
        assertEquals(annotationType, violation.getConstraintDescriptor().getAnnotation().annotationType());
    }

    private static final class SimpleTraversableResolver implements TraversableResolver {

        @Override
        public boolean isReachable(Object traversableObject, Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject,
                ElementType elementType) {

            return true;
        }

        @Override
        public boolean isCascadable(Object traversableObject, Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject,
                ElementType elementType) {

            return true;
        }
    }
}
