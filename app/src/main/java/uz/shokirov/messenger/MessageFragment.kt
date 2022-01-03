package uz.shokirov.messenger

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.shokirov.adapter.MessageAdapter
import uz.shokirov.messenger.databinding.FragmentMessageBinding
import uz.shokirov.models.Message
import uz.shokirov.models.User
import uz.shokirov.notification.NotificationData
import uz.shokirov.notification.PushNotification
import uz.shokirov.retrofit.RetrofitInstance
import uz.shokirov.service.MyMessageService
import kotlin.collections.ArrayList


const val TOPIC = "/topics/myTopic"

class MessageFragment : Fragment() {
    lateinit var binding: FragmentMessageBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var messageAdapter: MessageAdapter
    private val TAG = "MessageFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(layoutInflater)

        var user = arguments?.getSerializable("user") as User

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("messages")

        MyMessageService.sharedPref =
            activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        MyMessageService.token



        binding.btnSend.setOnClickListener {
            val messageText = binding.edtMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val key = reference.push().key
                val messenge = Message(key, messageText, firebaseAuth.uid, user.uid)
                reference.child(key!!).setValue(messenge)
                binding.edtMessage.text.clear()
                PushNotification(
                    NotificationData(
                        firebaseAuth.currentUser?.displayName.toString(), messageText
                    ), MyMessageService.token.toString()
                ).also {
                    sendNotification(it)
                }
            } else {

            }

        }

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                val listMessage = ArrayList<Message>()
                for (child in children) {
                    val value = child.getValue(Message::class.java)
                    if (value != null) {
                        if ((value.fromUid == firebaseAuth?.uid && value.toUid == user.uid) || value.fromUid == user.uid && value.toUid == firebaseAuth?.uid) {
                            listMessage.add(value)
                        }
                    }
                }
                messageAdapter = MessageAdapter(listMessage, firebaseAuth.uid!!)
                binding.rv.adapter = messageAdapter
                binding.rv.scrollToPosition(listMessage.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        return binding.root
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "sendNotification: succeses")
                } else {
                    Log.e(TAG, "sendNotification: ${response.errorBody().toString()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "sendNotification: ${e.toString()}")
            }
        }

    override fun onPause() {
        super.onPause()
        findNavController().popBackStack()
    }
}