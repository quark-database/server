package ru.anafro.quark.server.utils.hashing;

import ru.anafro.quark.server.utils.hashing.integers.IntegerHashingFunctionList;
import ru.anafro.quark.server.utils.hashing.strings.StringHashingFunctionList;

public class HashingFunctionRegistryGroup {
    private final IntegerHashingFunctionList integerHashingFunctionRegistry = new IntegerHashingFunctionList();
    private final StringHashingFunctionList stringHashingFunctionRegistry = new StringHashingFunctionList();

    public IntegerHashingFunctionList forIntegers() {
        return integerHashingFunctionRegistry;
    }

    public StringHashingFunctionList forStrings() {
        return stringHashingFunctionRegistry;
    }
}
