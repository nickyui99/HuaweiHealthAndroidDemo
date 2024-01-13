package com.test2.abc.view

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test2.abc.R
import com.test2.abc.databinding.FragmentHeartRateBinding
import com.test2.abc.utils.DateTimeUtils
import com.test2.abc.utils.HeartRateListAdapter
import com.test2.abc.viewmodel.HeartRateViewModel

class HeartRateFragment : Fragment(R.layout.fragment_heart_rate) {

    private val TAG = HeartRateFragment::class.java.simpleName

    private var _binding: FragmentHeartRateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeartRateViewModel by activityViewModels()

    private val startTime = DateTimeUtils.get7DaysBeforeToday()
    private val endTime = DateTimeUtils.getToday()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeartRateBinding.bind(view)

        // Fetch heart rate data
        viewModel.fetchInstantaneousHeartData(requireContext(), startTime, endTime)

        viewModel.intantaneousHRData.observe(viewLifecycleOwner, Observer { heartRateList ->
            Log.d(TAG, heartRateList.toString())

            if (heartRateList != null) {
                Log.d(TAG, "Groups: " + heartRateList.group.size)

                if (heartRateList.group.isNotEmpty()) {
                    binding.txtHeader.text = "${DateTimeUtils.formatCalendarToDateString(startTime, "dd/MM")} - ${DateTimeUtils.formatCalendarToDateString(endTime, "dd/MM")}"

                    heartRateList.group.forEach { group ->
                        group.sampleSet.forEach { sampleSet ->
                            val recyclerView: RecyclerView = view.findViewById(R.id.heartRateRecyclerView)
                            val layoutManager = LinearLayoutManager(requireContext())
                            recyclerView.layoutManager = layoutManager

                            val adapter = HeartRateListAdapter(requireContext(), sampleSet.samplePoints)
                            recyclerView.adapter = adapter
                        }
                    }
                } else {
                    Log.d(TAG, "No data found!!")

                    binding.txtHeader.text = "No data found!!"
                }
            }
        })
    }
}