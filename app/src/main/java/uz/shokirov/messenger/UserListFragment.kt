package uz.shokirov.messenger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.R
import uz.shokirov.adapter.OnClick
import uz.shokirov.adapter.RvUserListAdapter
import uz.shokirov.messenger.databinding.FragmentUserListBinding
import uz.shokirov.models.User


class UserListFragment : Fragment() {
    lateinit var binding: FragmentUserListBinding
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var list: ArrayList<User>
    lateinit var adapter: RvUserListAdapter
    lateinit var auth: FirebaseAuth
    var exit = 0
    lateinit var userB: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users")
        auth = FirebaseAuth.getInstance()

        binding.group.setOnClickListener {
            findNavController().navigate(uz.shokirov.messenger.R.id.groupFragment)
        }

        binding.animationView.visibility = View.VISIBLE
        binding.linear.visibility = View.GONE

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list = ArrayList()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(User::class.java)
                    if (value != null) {
                        if (auth.currentUser?.uid != value.uid) {
                            list.add(value)
                        }
                    }
                }
                binding.linear.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                adapter = RvUserListAdapter(list, object : OnClick {
                    override fun onClick(user: User, position: Int) {
                        exit = 1
                        userB = user
                        onPause()
                        findNavController().navigate(
                            uz.shokirov.messenger.R.id.messageFragment,
                            bundleOf("user" to user)
                        )
                    }
                })
                binding.rv.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return binding.root
    }

//    @SuppressLint("UseRequireInsteadOfGet")
 /*   override fun onPause() {
        super.onPause()
        if (exit != 1)
            finishAffinity(activity!!)
    }*/
}