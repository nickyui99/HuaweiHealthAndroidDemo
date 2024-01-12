package com.test2.abc.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test2.abc.R
import com.test2.abc.databinding.FragmentHeartRateBinding
import com.test2.abc.utils.HeartRateListAdapter
import com.test2.abc.viewmodel.HeartRateViewModel

class HeartRateFragment : Fragment(R.layout.fragment_heart_rate) {

    private val TAG = HeartRateFragment::class.java.simpleName

    private var _binding: FragmentHeartRateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeartRateViewModel by activityViewModels()

    companion object {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeartRateBinding.bind(view)

        // Fetch heart rate data
        viewModel.fetchInstantaneousHeartData(requireContext())

         viewModel.intantaneousHRData.observe(viewLifecycleOwner, Observer { heartRateList ->
             Log.d(TAG, heartRateList.toString())

             if(heartRateList != null) {
                 // Update UI or perform other actions for HeartRate
                 val recyclerView: RecyclerView = binding.recyclerView
                 val layoutManager = LinearLayoutManager(requireContext())
                 recyclerView.layoutManager = layoutManager

                 // Initialize adapter with heartRateList
                 val adapter = HeartRateListAdapter(heartRateList.group)
                 recyclerView.adapter = adapter
             }
         })
    }

}