package com.test2.abc.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test2.abc.Constants
import com.test2.abc.R
import com.test2.abc.databinding.FragmentAuthorizeBinding
import com.test2.abc.utils.AC2DMUtil
import com.test2.abc.utils.Preferences
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class OpenAuthorizeFragment : Fragment(R.layout.fragment_open_authorize) {
    private val TAG = OpenAuthorizeFragment::class.java.simpleName

    private var _binding: FragmentAuthorizeBinding? = null
    private val binding get() = _binding!!

    private val cookieManager = CookieManager.getInstance()

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAuthorizeBinding.bind(view)

        val baseUrl = Constants.HUAWEI_AUTHORIZE_URL
        val urlBuilder = baseUrl.toHttpUrlOrNull()?.newBuilder()

        val params = mapOf<String, String>(
            "client_id" to Constants.APP_ID.toString(),
            "response_type" to "code",
            "redirect_uri" to Constants.FIREBASE_AUTHORIZE_HEALTH_REDIRECT_URL,
            "scope" to Constants.HUAWEI_HEALTH_STEP_SCOPE + " "+
                    Constants.HUAWEI_HEART_RATE_READ_SCOPE + " " +
                    Constants.HUAWEI_HEART_RATE_WRITE_SCOPE + " " +
                    Constants.HUAWEI_HEALTH_DATA_ONE_YEAR_SCOPE
            ,
            "access_type" to "offline"
        )

        for ((key, value) in params) {
            urlBuilder?.addQueryParameter(key, value)
        }

        val url = urlBuilder?.build().toString()

        Log.d(TAG, url + params.toString())

        binding.authorizeWebView.apply {

            cookieManager.removeAllCookies(null)
            cookieManager.acceptThirdPartyCookies(this)
            cookieManager.setAcceptThirdPartyCookies(this, true)

            settings.apply {
                allowContentAccess = true
                databaseEnabled = true
                domStorageEnabled = true
                javaScriptEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) safeBrowsingEnabled = false
            }

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    val cookies = CookieManager.getInstance().getCookie(url)
                    // cookies can be null if there is an error
                    if (cookies != null) {
                        val cookieMap = AC2DMUtil.parseCookieString(cookies)
                        if (cookieMap.isNotEmpty() && cookieMap[ACCESS_TOKEN] != null) {
                            val accessToken = URLDecoder.decode(cookieMap[ACCESS_TOKEN], StandardCharsets.UTF_8.toString())
                            Log.d(TAG, "ACCESS TOKEN: " + URLDecoder.decode(accessToken, StandardCharsets.UTF_8.toString()))

                            //Save data to local db or shared prefs
                            Preferences.putString(requireContext(), Constants.SP_ACCESS_TOKEN, accessToken)

                            //After authorization back to previous page
                            findNavController().navigate(
                                AuthorizeFragmentDirections.actionAuthorizeFragmentToMainFragment()
                            )
                        }
                    }
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                    if (newProgress != 0) {
                        binding.authorizeProgressBar.also {
                            it.isVisible = newProgress < 100
                            it.isIndeterminate = false
                            it.max = 100
                            it.progress = newProgress
                        }
                    } else {
                        binding.authorizeProgressBar.isIndeterminate = true
                    }
                }
            }

            loadUrl(url)
        }
    }
}