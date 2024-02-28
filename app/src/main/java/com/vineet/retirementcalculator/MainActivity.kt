package com.vineet.retirementcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.vineet.retirementcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCenter.start(
            application,
            "384d87d3-7f39-4f21-bb0b-8b28f648dea1",
            Analytics::class.java,
            Crashes::class.java
        )
        binding.apply {
            calculateButton.setOnClickListener {
//            Crashes.generateTestCrash()
                try {
                    val interestRate = interestEditText.text.toString().toFloat()
                    val currentAge = ageEditText.text.toString().toInt()
                    val retirementAge = retirementEditText.text.toString().toInt()
                    val monthly = monthlySavingsEditText.text.toString().toFloat()
                    val current = currentEditText.text.toString().toFloat()

                    val properties:HashMap<String, String> = HashMap<String, String>()
                    properties.put("interest_rate", interestRate.toString())
                    properties.put("current_age", currentAge.toString())
                    properties.put("retirement_age", retirementAge.toString())
                    properties.put("monthly_savings", monthly.toString())
                    properties.put("current_savings", current.toString())

                    if (interestRate <= 0) {
                        Analytics.trackEvent("wrong interest rate", properties)
                    }
                    if (retirementAge <= currentAge) {
                        Analytics.trackEvent("wrong age", properties)
                    }
                } catch (e: Exception) {
                    Analytics.trackEvent(e.message)
                }
            }
        }
    }
}