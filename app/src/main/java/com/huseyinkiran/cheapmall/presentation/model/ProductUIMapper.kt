package com.huseyinkiran.cheapmall.presentation.model

import com.huseyinkiran.cheapmall.domain.model.Product
import com.huseyinkiran.cheapmall.domain.model.Rating

fun Product.toUIModel(): ProductUIModel {
    return ProductUIModel(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = RatingUIModel(rate = rating.rate, count = rating.count),
        quantity = quantity
    )
}

fun ProductUIModel.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = Rating(rate = rating.rate, count = rating.count),
        quantity = quantity
    )
}