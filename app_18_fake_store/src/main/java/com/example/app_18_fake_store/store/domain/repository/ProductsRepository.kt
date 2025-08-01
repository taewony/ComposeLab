package com.example.app_18_fake_store.store.domain.repository

import com.example.app_18_fake_store.store.domain.model.Product

interface ProductsRepository {

    // 반환 타입을 Either -> Result 로 변경
    suspend fun getProducts(): Result<List<Product>>

}
