package com.blogstack.utils;

import com.blogstack.commons.BlogStackCommonConstants;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public enum BlogStackCommonUtils {

    INSTANCE;

    public String uniqueIdentifier(String... prefix) {
        return (prefix.length > BigInteger.ZERO.intValue() ? prefix[BigInteger.ZERO.intValue()] : BlogStackCommonConstants.INSTANCE.BLANK_STRING).concat(UUID.randomUUID().toString().replace(BlogStackCommonConstants.INSTANCE.HYPHEN_STRING, BlogStackCommonConstants.INSTANCE.BLANK_STRING));
    }

    public String getMessageStringFromException(String exceptionMessage) {
        return exceptionMessage.substring(BigInteger.ZERO.intValue(), exceptionMessage.lastIndexOf(BlogStackCommonConstants.INSTANCE.SERVICE_STRING) - BigInteger.ONE.intValue()).strip();
    }
}