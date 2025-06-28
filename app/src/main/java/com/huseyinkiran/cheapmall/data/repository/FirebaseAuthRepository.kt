package com.huseyinkiran.cheapmall.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.huseyinkiran.cheapmall.core.Constants.USERS
import com.huseyinkiran.cheapmall.domain.model.Person
import com.huseyinkiran.cheapmall.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    private val _currentUserUid = MutableStateFlow(auth.currentUser?.uid)
    override val currentUserUid: StateFlow<String?> = _currentUserUid

    private val authStateListener = FirebaseAuth.AuthStateListener {  firebaseAuth ->
        _currentUserUid.value = firebaseAuth.currentUser?.uid
    }

    init {
        auth.addAuthStateListener(authStateListener)
    }

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Result<Person> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: return Result.failure(Exception("UID is null"))

            val person = Person(uid, name, email)
            firestore.collection(USERS).document(uid).set(person).await()
            Result.success(person)

        } catch (e: FirebaseAuthException) {
            Result.failure(e)
        } catch (e: FirebaseFirestoreException) {
            Result.failure(e)
        }
    }

    override suspend fun signInUser(email: String, password: String): Result<Person> {
        return try {

            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: return Result.failure(Exception("UID is null"))

            val snapshot = firestore.collection(USERS).document(uid).get().await()
            val person = snapshot.toObject(Person::class.java)
                ?: return Result.failure(Exception("User not found"))

            Result.success(person)

        } catch (e: FirebaseAuthException) {
            Result.failure(e)
        } catch (e: FirebaseFirestoreException) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
        _currentUserUid.value = null
    }

    override suspend fun getUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

}