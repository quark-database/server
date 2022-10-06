package ru.anafro.quark.server.utils.hashing;

import ru.anafro.quark.server.utils.hashing.integers.IntegerHashingFunctionRegistry;
import ru.anafro.quark.server.utils.hashing.strings.StringHashingFunctionRegistry;

public class HashingFunctionRegistryGroup {
    private final IntegerHashingFunctionRegistry integerHashingFunctionRegistry = new IntegerHashingFunctionRegistry();
    private final StringHashingFunctionRegistry stringHashingFunctionRegistry = new StringHashingFunctionRegistry();

    public IntegerHashingFunctionRegistry forIntegers() {
        return integerHashingFunctionRegistry;
    }

    public StringHashingFunctionRegistry forStrings() {
        return stringHashingFunctionRegistry;
    }
}
