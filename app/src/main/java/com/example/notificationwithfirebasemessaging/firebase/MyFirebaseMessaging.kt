package com.example.notificationwithfirebasemessaging.firebase

import com.google.firebase.messaging.FirebaseMessaging

object MyFirebaseMessaging {
    fun subscriberToTopic(
        topic:String,
        onSuccessCallback:(String)->Unit,
        onFailedCallback: ((String) -> Unit?)? =null){
        FirebaseMessaging.getInstance()
            .subscribeToTopic(topic)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    onSuccessCallback(topic)
                }else{
                    onFailedCallback?.let { it(topic) }
                }
            }
            .addOnFailureListener {ex->
                onFailedCallback?.let { it -> ex.message?.let { msg -> it(msg) } }
            }
    }
}