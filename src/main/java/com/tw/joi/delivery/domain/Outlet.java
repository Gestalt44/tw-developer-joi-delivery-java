package com.tw.joi.delivery.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Outlet {

    protected String name;

    protected String address;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long outletId;
    @OneToMany(mappedBy = "outlet")
    private List<OutletInventory> inventory;
}

