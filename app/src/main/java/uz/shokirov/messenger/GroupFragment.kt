package uz.shokirov.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.shokirov.adapter.GroupAdapter
import uz.shokirov.messenger.databinding.FragmentGroupBinding
import uz.shokirov.models.Group

class GroupFragment : Fragment() {
    lateinit var binding: FragmentGroupBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var groupAdapter: GroupAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupBinding.inflate(layoutInflater)


        /* var user = arguments?.getSerializable("user") as User*/


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("group")


        binding.btnSend.setOnClickListener {
            val messageText = binding.edtMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val key = reference.push().key
                val messenge =
                    Group(key, messageText, firebaseAuth.currentUser?.displayName, firebaseAuth.uid)
                reference.child(key!!).setValue(messenge)
                binding.edtMessage.text.clear()
            } else {

            }

        }

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                val listMessage = ArrayList<Group>()
                for (child in children) {
                    val value = child.getValue(Group::class.java)
                    if (value != null) {
                        listMessage.add(value)
                    }
                }
                groupAdapter = GroupAdapter(listMessage, firebaseAuth.uid!!)
                binding.rv.adapter = groupAdapter
                binding.rv.scrollToPosition(listMessage.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return binding.root
    }

}