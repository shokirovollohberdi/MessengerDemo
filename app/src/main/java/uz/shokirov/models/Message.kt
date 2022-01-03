package uz.shokirov.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Message {
    var id: String? = null
    var text: String? = null

    @SuppressLint("SimpleDateFormat")
    var fromUid: String? = null
    var toUid: String? = null
    var date = SimpleDateFormat("HH:mm").format(Date())

    constructor(id: String?, text: String?, fromUid: String?, toUid: String?) {
        this.id = id
        this.text = text
        this.fromUid = fromUid
        this.toUid = toUid
    }

    constructor()

    override fun toString(): String {
        return "Message(id=$id, text=$text, fromUid=$fromUid, toUid=$toUid, date='$date')"
    }


}