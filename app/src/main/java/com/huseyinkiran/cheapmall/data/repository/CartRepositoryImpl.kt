package com.huseyinkiran.cheapmall.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.huseyinkiran.cheapmall.core.Constants.CART_DB
import com.huseyinkiran.cheapmall.core.Constants.USERS
import com.huseyinkiran.cheapmall.domain.model.Product
import com.huseyinkiran.cheapmall.domain.model.Rating
import com.huseyinkiran.cheapmall.domain.repository.CartRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): CartRepository {

    private fun getUid(): String? = auth.currentUser?.uid

    override suspend fun addToCart(product: Product) {
        val uid = getUid()

        val cartItemMap = hashMapOf(
            "id" to product.id,
            "title" to product.title,
            "description" to product.description,
            "category" to product.category,
            "price" to product.price,
            "image" to product.image,
            "quantity" to (product.quantity ?: 1),
            "rating" to hashMapOf(
                "rate" to product.rating.rate,
                "count" to product.rating.count
            )
        )

        if (uid != null) {
            firestore.collection(USERS).document(uid)
                .collection(CART_DB).document(product.id.toString())
                .set(cartItemMap)
                .await()
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        val uid = getUid()

        if (uid != null) {
            firestore.collection(USERS).document(uid)
                .collection(CART_DB).document(productId.toString())
                .delete()
                .await()
        }
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        val uid = getUid()

        if (quantity >= 1) {
            if (uid != null) {
                firestore.collection(USERS).document(uid)
                    .collection(CART_DB).document(productId.toString())
                    .update("quantity", quantity)
                    .await()
            }
        }

    }

    override fun getCartItems(): Flow<List<Product>> = callbackFlow {
        val uid = getUid()
        if (uid == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = firestore.collection(USERS).document(uid)
            .collection(CART_DB)
            .addSnapshotListener { snapshot, _ ->
                val products = snapshot?.mapNotNull { doc ->
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
                                rate = ratingMap["rate"] as Double,
                                count = (ratingMap["count"] as Long).toInt()
                            ),
                            quantity = (doc["quantity"] as Long).toInt()
                        )
                    } catch (e: Exception) {
                        null
                    }
                } ?: emptyList()
                trySend(products)
            }

        awaitClose { listener.remove() }
    }

    override fun getTotalAmountFlow(): Flow<Double> = callbackFlow {
        val uid = getUid()
        if (uid == null) {
            trySend(0.0)
            close()
            return@callbackFlow
        }

        val listener = firestore.collection(USERS).document(uid)
            .collection(CART_DB)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    var total = 0.0
                    for (doc in snapshot) {
                        val price = doc.getDouble("price") ?: 0.0
                        val quantity = (doc.getLong("quantity") ?: 1L).toInt()
                        total += price * quantity
                    }
                    trySend(total)
                }
            }

        awaitClose { listener.remove() }
    }

    override suspend fun clearCart() {
        val uid = getUid()

        val products = uid?.let {
            firestore.collection(USERS).document(it)
                .collection(CART_DB).get().await()
        }

        if (products != null) {
            for (doc in products) {
                doc.reference.delete().await()
            }
        }
    }

}