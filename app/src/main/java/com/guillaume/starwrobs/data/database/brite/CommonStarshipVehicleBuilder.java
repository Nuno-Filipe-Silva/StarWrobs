package com.guillaume.starwrobs.data.database.brite;

import com.guillaume.starwrobs.data.database.SWDatabaseContract.CommonStarshipVehicle;

public abstract class CommonStarshipVehicleBuilder extends BaseBuilder {

    public CommonStarshipVehicleBuilder name(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_NAME, value);
        return this;
    }

    public CommonStarshipVehicleBuilder model(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MODEL, value);
        return this;
    }

    public CommonStarshipVehicleBuilder manufacturer(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MANUFACTURER, value);
        return this;
    }

    public CommonStarshipVehicleBuilder costInCredits(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_COST_IN_CREDITS, value);
        return this;
    }

    public CommonStarshipVehicleBuilder length(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_LENGTH, value);
        return this;
    }

    public CommonStarshipVehicleBuilder maxAtmospheringSpeed(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_MAX_ATMOSPHERING_SPEED, value);
        return this;
    }

    public CommonStarshipVehicleBuilder crew(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CREW, value);
        return this;
    }

    public CommonStarshipVehicleBuilder passengers(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_PASSENGERS, value);
        return this;
    }

    public CommonStarshipVehicleBuilder cargoCapacity(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CARGO_CAPACITY, value);
        return this;
    }

    public CommonStarshipVehicleBuilder consumables(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CONSUMABLES, value);
        return this;
    }

    public CommonStarshipVehicleBuilder objectClass(String value) {
        values.put(CommonStarshipVehicle.STARSHIP_VEHICLE_CLASS, value);
        return this;
    }
}
