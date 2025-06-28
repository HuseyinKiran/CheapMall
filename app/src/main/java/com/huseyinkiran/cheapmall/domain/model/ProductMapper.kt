package com.huseyinkiran.cheapmall.domain.model

import com.huseyinkiran.cheapmall.data.model.ProductDto

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = Rating(rate = rating.rate, count = rating.count)
    )
}