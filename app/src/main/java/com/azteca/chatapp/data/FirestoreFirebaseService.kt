package com.azteca.chatapp.data

import com.azteca.chatapp.data.model.UserModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreFirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {

    companion object {
        private const val COLLECT_USER = "user"
        private const val COLLECT_CHATS_R = "chatRoom"
        private const val COLLECT_CHAT = "chatProf"
        private const val SRG_USER_PRO = "userPdof"
        const val DB_USERNAME = "username"
        const val DB_LIST_USER = "listUser"
        const val DB_TIMESTAMP = "timestamp"
    }

    fun collectionUser(): CollectionReference {
        return firestore.collection(COLLECT_USER)
    }

    fun getInfUser(userId: String): DocumentReference {
        return firestore.collection(COLLECT_USER).document(userId)
    }

    suspend fun setInfUser(userId: String, userModel: UserModel): Boolean {
        return suspendCoroutine { continuation ->
            firestore.collection(COLLECT_USER).document(userId)
                .set(userModel).addOnCompleteListener {
                    continuation.resume(it.isSuccessful)
                }.addOnFailureListener {
                    // modificar las reglas de firestore
                    continuation.resumeWithException(it)
                }
        }
    }

    fun getChatroom(chatRoomId: String): DocumentReference {
        return firestore.collection(COLLECT_CHATS_R).document(chatRoomId)
    }

    fun getChatroomMsg(chatRoomId: String): CollectionReference {
        return getChatroom(chatRoomId).collection(COLLECT_CHAT)
    }

    fun getChatroomId(userId1: String, userId2: String): String {
        return if (userId1.hashCode() < userId2.hashCode()) {
            userId1 + "_" + userId2
        } else {
            userId2 + "_" + userId1
        }
    }

    fun getChatroomCollections(): CollectionReference {
        return firestore.collection(COLLECT_CHATS_R)
    }

    fun getOtherUserFromChatRoom(listUsers: List<String>, currentUser: String): DocumentReference {
        return if (listUsers[0] == currentUser) {
            collectionUser().document(listUsers[1])
        } else {
            collectionUser().document(listUsers[0])
        }
    }

    fun refImgProfileUser(userId: String): StorageReference {
        return firebaseStorage.getReference().child(SRG_USER_PRO).child(userId)
    }

}