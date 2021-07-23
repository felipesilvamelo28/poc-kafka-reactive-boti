package com.example.testekafka.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "arquivo")
public class Arquivo {

    @Id
    private String id;
    private String location_id;
    private String location_uuid;
    private String sku;
    private int total_quantity;
    private int reserved_quantity;
    private int available_quantity;
    private String threshold;
    private String updated_at;
    private boolean enabled;
    private String external_id;
    private String distribution_center;

    public Arquivo(String id, String location_id, String location_uuid, String sku, int total_quantity, int reserved_quantity, int available_quantity, String threshold, String updated_at, boolean enabled, String external_id, String distribution_center) {
        this.id = id;
        this.location_id = location_id;
        this.location_uuid = location_uuid;
        this.sku = sku;
        this.total_quantity = total_quantity;
        this.reserved_quantity = reserved_quantity;
        this.available_quantity = available_quantity;
        this.threshold = threshold;
        this.updated_at = updated_at;
        this.enabled = enabled;
        this.external_id = external_id;
        this.distribution_center = distribution_center;
    }

    public Arquivo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getLocation_uuid() {
        return location_uuid;
    }

    public void setLocation_uuid(String location_uuid) {
        this.location_uuid = location_uuid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public int getReserved_quantity() {
        return reserved_quantity;
    }

    public void setReserved_quantity(int reserved_quantity) {
        this.reserved_quantity = reserved_quantity;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getDistribution_center() {
        return distribution_center;
    }

    public void setDistribution_center(String distribution_center) {
        this.distribution_center = distribution_center;
    }
}
