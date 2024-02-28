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
            "f82097f8-10cc-4268-887b-8eaa1d1fa513",
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

                    resultTextView.text = "At the current rate of $interestRate%, saving \$$monthly a month you will have \$X by $retirementAge."


                } catch (e: Exception) {
                    Analytics.trackEvent(e.message)
                }
            }
        }
    }
}
