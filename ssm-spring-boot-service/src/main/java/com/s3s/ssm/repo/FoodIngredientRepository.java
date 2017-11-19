package com.s3s.ssm.repo;

import org.springframework.data.repository.CrudRepository;

import com.sunrise.xdoc.entity.catalog.FoodIngredient;

public interface FoodIngredientRepository extends CrudRepository<FoodIngredient, Long> {

}
