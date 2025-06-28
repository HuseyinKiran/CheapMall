package com.huseyinkiran.cheapmall.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.huseyinkiran.cheapmall.core.Constants.FAV_DB
import com.huseyinkiran.cheapmall.core.Constants.USERS
import com.huseyinkiran.cheapmall.domain.model.Product
import com.huseyinkiran.cheapmall.domain.model.Rating
import com.huseyinkiran.cheapmall.domain.repository.FavoritesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
): FavoritesRepository {

    private fun getUid(): String? = auth.currentUser?.uid

    override suspend fun addToFavorites(product: Product) {
        val uid = getUid()

        val productMap = hashMapOf(
            "id" to product.id,
            "title" to product.title,
            "price" to product.price,
            "description" to product.description,
            "category" to product.category,
            "image" to product.image,
            "rating" to hashMapOf(
                "rate" to product.rating.rate,
                "count" to product.rating.count
            )
        )

        if (uid != null) {
            firestore.collection(USERS).document(uid)
                .collection(FAV_DB).document(product.id.toString()).set(productMap)
                .await()
        }

    }

    override suspend fun removeFromFavorites(productId: Int) {
        val uid = getUid()

        if (uid != null) {
            firestore.collection(USERS).document(uid)
                .collection(FAV_DB).document(productId.toString())
                .delete()
                .await()
        }
    }

    override suspend fun isFavorite(productId: Int): Boolean {
        val uid = getUid()

        return try {
            val doc = uid?.let {
                firestore.collection(USERS).document(it).collection(FAV_DB)
                    .document(productId.toString()).get().await()
            }
            doc?.exists() ?: false
        } catch (e: Exception) {
            false
        }
    }

    override fun getFavorites(): Flow<List<Product>> = callbackFlow {
        val uid = getUid()
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = firestore.collection(USERS).document(uid).collection(FAV_DB)
            .addSnapshotListener { snapshot, _ ->
                val favorites = snapshot?.mapNotNull { doc ->
                    try {
                        val ratingMap = doc["rating"] as Map<*, *>
                        Product(
                            id = (doc["id"] as Long).toInt(),
                            title = doc["title"] as String,
                            price = doc["price"] as Double,
                            description = doc["description"] as String,
                            category = doc["category"] as String,
                            image = doc["image"] as String,
                            rating = Rating(
                                rate = (ratingMap["rate"] as Double),
                                count = (ratingMap["count"] as Long).toInt()
                            )
                        )
                    } catch (e: Exception) {
                        null
                    }
                } ?: emptyList()
                trySend(favorites)
            }

        awaitClose { listener.remove() }
    }

}