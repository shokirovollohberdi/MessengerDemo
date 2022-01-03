package uz.shokirov.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.shokirov.messenger.databinding.FragmentPhoneRegisterBinding
import uz.shokirov.models.Objects


class PhoneRegisterFragment : Fragment() {
    lateinit var binding: FragmentPhoneRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneRegisterBinding.inflate(layoutInflater)

        binding.btnEnter.setOnClickListener {
            if (binding.btnEnter.text.toString().trim().isEmpty()&&binding.edtName.text.toString().trim().isEmpty()) {
                Toast.makeText(context, "Ma'lumot to'liq emas", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(
                    R.id.smsFragment,
                    bundleOf("number" to binding.edtPhone.text.toString())
                )
                Objects.name = binding.edtName.text.toString()
            }
        }

        return binding.root
    }

}