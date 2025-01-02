package org.example.service.inflexdb;

import org.example.entity.DeviceType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryCreator {
    public @NotNull String getAllDeviceFluxQuery(String bucket) {
        return String.format(
                "from(bucket: \"%s\") " +
                        "|> range(start: -30d) " +
                        "|> filter(fn: (r) => %s) " +
                        "|> last()",
                bucket,
                Stream.of(DeviceType.values())
                        .map(this::getMeasurement)
                        .collect(Collectors.joining(" or "))
        );
    }


    public @NotNull String getDeviceByIdFluxQuery(String bucket, String measurement, String deviceId ){
        return String.format("from(bucket: \"%s\") " +
                "  |> range(start: -30d)" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"%s\")" +
                "  |> filter(fn: (r) => r[\"device\"] == \"%s\")" +
                "  |> last()", bucket, measurement, deviceId);
    }

    private @NotNull String getMeasurement(DeviceType deviceType) {
        return String.format("r[\"_measurement\"] == \"%s\"", deviceType.getMeasurement());
    }
}
