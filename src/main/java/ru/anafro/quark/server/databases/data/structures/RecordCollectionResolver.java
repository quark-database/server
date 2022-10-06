package ru.anafro.quark.server.databases.data.structures;

public class RecordCollectionResolver {
    private final RecordCollectionResolverCase resolverCase;

    public RecordCollectionResolver(RecordCollectionResolverCase resolverCase) {
        this.resolverCase = resolverCase;
    }

    public RecordCollection createEmptyCollection() {
        return switch(resolverCase) {
            case FIND_KEY_IN_SMALL_TABLE -> new HashMapRecordCollection();
            case FIND_KEY_IN_BIG_TABLE -> new TreeRecordCollection();

            default -> new LinearRecordCollection();
        };
    }

    public RecordCollectionResolverCase getResolverCase() {
        return resolverCase;
    }

    public enum RecordCollectionResolverCase {
        SELECTOR_IS_TOO_COMPLEX,
        JUST_SELECT_EVERYTHING,
        FIND_KEY_IN_BIG_TABLE,
        FIND_KEY_IN_SMALL_TABLE,
        NO_FURTHER_MANIPULATIONS
    }
}
