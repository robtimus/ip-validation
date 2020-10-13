module com.github.robtimus.ip.validation {
    requires transitive com.github.robtimus.ip.utils;
    requires transitive java.validation;

    exports com.github.robtimus.net.ip.validation;
    exports com.github.robtimus.net.ip.validation.validators;
}
