package com.huseyinkiran.cheapmall.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.huseyinkiran.cheapmall.data.api.ProductsApi
import com.huseyinkiran.cheapmall.data.repository.CartRepositoryImpl
import com.huseyinkiran.cheapmall.data.repository.FavoritesRepositoryImpl
import com.huseyinkiran.cheapmall.data.repository.FirebaseAuthRepository
import com.huseyinkiran.cheapmall.data.repository.ProductRepositoryImpl
import com.huseyinkiran.cheapmall.domain.repository.AuthRepository
import com.huseyinkiran.cheapmall.domain.repository.CartRepository
import com.huseyinkiran.cheapmall.domain.repository.FavoritesRepository
import com.huseyinkiran.cheapmall.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @[Singleton Provides]
    fun provideProductRepository(api: ProductsApi): ProductRepository = ProductRepositoryImpl(api)

    @[Singleton Provides]
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = FirebaseAuthRepository(firebaseAuth, firestore)

    @[Singleton Provides]
    fun provideFavoritesRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): FavoritesRepository = FavoritesRepositoryImpl(firestore, firebaseAuth)

    @[Singleton Provides]
    fun provideCartRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): CartRepository = CartRepositoryImpl(firestore, firebaseAuth)

}