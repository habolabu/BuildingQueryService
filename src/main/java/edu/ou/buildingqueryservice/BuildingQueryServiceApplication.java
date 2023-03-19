package edu.ou.buildingqueryservice;

import edu.ou.coreservice.annotation.BaseQueryAnnotation;
import org.springframework.boot.SpringApplication;

@BaseQueryAnnotation
public class BuildingQueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuildingQueryServiceApplication.class, args);
    }

}
