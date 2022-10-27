package ru.anafro.quark.server.databases.data.structures;

public class RecordCollectionResolver {
    private final RecordCollectionResolverCase resolverCase;

    public RecordCollectionResolver(RecordCollectionResolverCase resolverCase) {
        this.resolverCase = resolverCase;
    }

    public RecordCollection createEmptyCollection() {
        return new LinearRecordCollection();
    }

    public RecordCollectionResolverCase getResolverCase() {
        return resolverCase;
    }

    public enum RecordCollectionResolverCase {
        SELECTOR_IS_TOO_COMPLEX,
        JUST_SELECT_EVERYTHING,
        FIND_UNIQUE,
        FIND_REPEATING,
        NO_FURTHER_MANIPULATIONS
    }
}
