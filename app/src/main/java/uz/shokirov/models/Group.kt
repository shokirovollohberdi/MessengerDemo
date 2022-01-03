package uz.shokirov.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Group {
    var id: String? = null
    var text: String? = null
    var own:String?=null
    @SuppressLint("SimpleDateFormat")
    var fromUid: String? = null
    var date = SimpleDateFormat("HH:mm").format(Date())


    constructor()
    constructor(id: String?, text: String?, own: String?, fromUid: String?) {
        this.id = id
        this.text = text
        this.own = own
        this.fromUid = fromUid
    }

    override fun toString(): String {
        return "Group(id=$id, text=$text, fromUid=$fromUid, date='$date')"
    }


}