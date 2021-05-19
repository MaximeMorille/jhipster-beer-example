package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeerMapperTest {

    private BeerMapper beerMapper;

    @BeforeEach
    public void setUp() {
        beerMapper = new BeerMapperImpl();
    }
}
