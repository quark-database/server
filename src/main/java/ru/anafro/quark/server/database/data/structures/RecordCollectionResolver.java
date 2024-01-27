package ru.anafro.quark.server.database.data.structures;

public record RecordCollectionResolver(
        ru.anafro.quark.server.database.data.structures.RecordCollectionResolver.RecordCollectionResolverCase resolverCase) {

    public RecordCollection createEmptyCollection() {
        return new LinearRecordCollection();
    }

    public enum RecordCollectionResolverCase {
        SELECTOR_IS_TOO_COMPLEX,
        JUST_SELECT_EVERYTHING,
        FIND_UNIQUE,
        FIND_REPEATING,
        NO_FURTHER_MANIPULATIONS
    }
}
