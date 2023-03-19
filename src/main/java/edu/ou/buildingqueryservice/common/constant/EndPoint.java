package edu.ou.buildingqueryservice.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndPoint {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Area {
        public static final String BASE = "/area";
        public static final String DETAIL = "/{areaSlug}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Apartment {
        public static final String BASE = "/apartment";
        public static final String DETAIL = "/{apartmentSlug}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Room {
        public static final String BASE = "/room";
        public static final String DETAIL = "/{roomSlug}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Parking {
        public static final String BASE = "/parking";
        public static final String DETAIL = "/{parkingSlug}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class PriceTag {
        public static final String BASE = "/price-tag";
        public static final String DETAIL = "/{priceTagSlug}";
        public static final String ALL = "/all";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ParkingType {
        public static final String BASE = "/parking-type";
        public static final String DETAIL = "/{parkingTypeSlug}";

        public static final String ALL = "/all";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ParkingSpace {
        public static final String BASE = "/parking-space";

    }
}
