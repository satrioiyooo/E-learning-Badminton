package com.example.badminton.ui.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.badminton.databinding.FragmentAboutBinding
import com.example.badminton.ui.chatbot.ChatbotActivity
import com.example.badminton.ui.data.HasilUjian
import com.example.badminton.ui.home.ujian.HasilUjianDatabase
import com.example.badminton.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
        binding.imageButton.setOnClickListener {
            startActivity(Intent(requireActivity(), ChatbotActivity::class.java))
            requireActivity()
        }


        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            binding.fullName.text = firebaseUser.displayName
            binding.email.text = firebaseUser.email
        } else {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        // Initialize RecyclerView
        binding.hasilrv.layoutManager = LinearLayoutManager(requireContext())

        // Fetch data from database and set the adapter
        lifecycleScope.launch {
            val hasilList = fetchHasilUjianFromDatabase()
            binding.hasilrv.adapter = HasilUjianAdapter(hasilList)
        }
    }

    private suspend fun fetchHasilUjianFromDatabase(): List<HasilUjian> {
        val db = HasilUjianDatabase.getDatabase(requireContext())
        return withContext(Dispatchers.IO) {
            db.hasilUjianDao().getAll().firstOrNull() ?: emptyList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
