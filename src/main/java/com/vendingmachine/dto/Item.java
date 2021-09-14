package com.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private String code;
    private String name;
    private String cost;
    private String number;

    public Item(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String newCost) {
        this.cost = newCost;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String newNumber) {
        this.number = newNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) &&
                Objects.equals(cost, item.cost) &&
                Objects.equals(number, item.number);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.cost);
        hash = 89 * hash + Objects.hashCode(this.number);
        return hash;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
