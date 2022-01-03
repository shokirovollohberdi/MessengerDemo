package uz.shokirov.models

import java.io.Serializable

class User:Serializable {
    var uid: String? = null
    var displayName: String? = null
    var email: String? = null
    var imageUrl: String? = null



    constructor()
    constructor(uid: String?, displayName: String?, email: String?, imageUrl: String?) {
        this.uid = uid
        this.displayName = displayName
        this.email = email
        this.imageUrl = imageUrl
    }

    override fun toString(): String {
        return "User(uid=$uid, displayName=$displayName, email=$email, imageUrl=$imageUrl)"
    }

}