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
import com.test2.abc.utils.DateTimeUtils
import com.test2.abc.utils.HeartRateListAdapter
import com.test2.abc.viewmodel.HeartRateViewModel
import java.util.Calendar

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

        binding.txtWeek.text = formatWeekText(startTime, endTime)

        attachActions()
        fetchData()

        viewModel.intantaneousHRData.observe(viewLifecycleOwner, Observer { heartRateList ->
            Log.d(TAG, heartRateList.toString())
            binding.txtWeek.text = formatWeekText(startTime, endTime)
            if (heartRateList != null) {
                Log.d(TAG, "Groups: " + heartRateList.group.size)

                if (heartRateList.group.isNotEmpty()) {
                    binding.txtHeader.text = formatWeekText(startTime, endTime)

                    heartRateList.group.forEach { group ->
                        Log.d(TAG, "Sample set: " + group.sampleSet.size)
                        group.sampleSet.forEach { sampleSet ->
                            Log.d(TAG, "Sample points: " + sampleSet.samplePoints.size)
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

    private fun fetchData() {
        // Fetch heart rate data
        viewModel.fetchInstantaneousHeartData(requireContext(), startTime, endTime)
    }

    private fun attachActions() {
        binding.btnPreviousWeek.setOnClickListener{
            // Subtract 7 days from the current date
            startTime.add(Calendar.DAY_OF_MONTH, -7)
            endTime.add(Calendar.DAY_OF_MONTH, -7)
            fetchData()
        }

        binding.btnNextWeek.setOnClickListener {
            val today = Calendar.getInstance()

            // Clone the endTime to avoid modifying the original instance
            val last = endTime.clone() as Calendar

            // Add 7 days to the cloned instance
            last.add(Calendar.DAY_OF_MONTH, 7)

            // Check if the new date (last) is after today
            if (last.before(today)) {
                // If it is, then update the dates
                startTime.add(Calendar.DAY_OF_MONTH, 7)
                endTime.add(Calendar.DAY_OF_MONTH, 7)

                // Fetch data using the updated dates
                fetchData()
            } else {
                // If the new date is not after today, you can handle it as needed
                // For example, show a message, or perform a different action
                // Here, we're simply logging a message
                Log.d("DateLimit", "Cannot go beyond today")
            }
        }


    }

    private fun formatWeekText(startTime: Calendar, endTime: Calendar) : String{
        return "${DateTimeUtils.formatCalendarToDateString(startTime, "dd/MM")} - ${DateTimeUtils.formatCalendarToDateString(endTime, "dd/MM")}"
    }
}