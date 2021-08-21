package ru.imatveev.collection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * show complexity of collection's method
 */
@Target(ElementType.METHOD)
public @interface Complex {
    Complexity value();

    enum Complexity {
        CONSTANT("O(1)"),
        LINEAR("O(n)"),
        QUADRATIC("O(n^2)"),
        EXPONENTIAL("O(2^n)"),
        LOGARITHMIC("O(log n)");
        private final String description;

        Complexity(String description) {
            this.description = description;
        }
    }
}
