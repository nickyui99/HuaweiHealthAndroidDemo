package com.test2.abc.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.test2.abc.Constants
import com.test2.abc.R
import com.test2.abc.databinding.FragmentMainBinding
import com.test2.abc.event.BusEvent
import com.test2.abc.utils.MyHttpClient
import com.test2.abc.utils.Preferences
import com.test2.abc.viewmodel.MainViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment: Fragment(R.layout.fragment_main) {
    private val TAG = MainFragment::class.java.simpleName

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private var isAuthorized = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        updateView()
        attachActions()
    }

    fun updateView() {
        val accessToken = Preferences.getString(requireContext(), Constants.SP_ACCESS_TOKEN)

        isAuthorized = !accessToken.isNullOrBlank()


        Log.d(TAG, accessToken)
        Log.d(TAG, "isAuthorized: " + isAuthorized)

        binding.txtAuthorize.text = "Is Authorized: $isAuthorized"
    }

    fun attachActions() {
        binding.btnAuthorize.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAuthorizeFragment()
            )
        }

        binding.btnUnauthorize.setOnClickListener {
            viewModel.unauthorizeHealthKit(requireContext())
        }

        binding.btnHeartRate.setOnClickListener{
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToHeartRateFragment()
            )
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceived(event: BusEvent) {
        when(event) {
            is BusEvent.UnauthorizeHealthKitEvent -> {
                if(event.success) {
                    updateView()
                    Toast.makeText(requireContext(), "Health kit has been unauthorized", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Health kit unauthorized failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}