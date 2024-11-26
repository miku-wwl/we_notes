package com.weilai.redisson.object;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class Geo {
    @Autowired
    private RedissonClient redisson;

    @PostConstruct
    public void init() {
        log.info("Geo init");
        redisson.getKeys().flushall();
        RGeo<String> geo = redisson.getGeo("myGeo");
        GeoEntry entry = new GeoEntry(13.361389, 38.115556, "Palermo");

        geo.add(entry);
        geo.add(15.087269, 37.502669, "Catania");

        Double dist = geo.dist("Palermo", "Catania", GeoUnit.METERS);
        log.info("dist: {}", dist);

        Map<String, GeoPosition> pos = geo.pos("Palermo", "Catania");
        log.info("pos: {}", pos);

        List<String> cities = geo.radius(15, 37, 200, GeoUnit.KILOMETERS);
        log.info("cities: {}", cities);

        List<String> allNearCities = geo.radius("Palermo", 10, GeoUnit.KILOMETERS);
        log.info("allNearCities: {}", allNearCities);

        Map<String, Double> citiesWithDistance = geo.radiusWithDistance(15, 37, 200, GeoUnit.KILOMETERS);
        log.info("citiesWithDistance: {}", citiesWithDistance);

        Map<String, Double> allNearCitiesDistance = geo.radiusWithDistance("Palermo", 10, GeoUnit.KILOMETERS);
        log.info("allNearCitiesDistance: {}", allNearCitiesDistance);

        Map<String, GeoPosition> citiesWithPosition = geo.radiusWithPosition(15, 37, 200, GeoUnit.KILOMETERS);
        log.info("citiesWithPosition: {}", citiesWithPosition);

        Map<String, GeoPosition> allNearCitiesPosition = geo.radiusWithPosition("Palermo", 10, GeoUnit.KILOMETERS);
        log.info("allNearCitiesPosition: {}", allNearCitiesPosition);

    }
}