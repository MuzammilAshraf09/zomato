package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class PaymentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize payment method layouts
        val zomatoMoneyLayout = view.findViewById<LinearLayout>(R.id.zomatoMoneyLayout)
        val addCardLayout = view.findViewById<LinearLayout>(R.id.addCardLayout)
        val addHBLLayout = view.findViewById<LinearLayout>(R.id.addHBLLayout)
        val addCashOnDeliveryLayout = view.findViewById<LinearLayout>(R.id.addCashOnDeliveryLayout)

        // Set click listeners for payment methods
        zomatoMoneyLayout.setOnClickListener {
            showConfirmationDialog("Zomato Money")
        }

        addCardLayout.setOnClickListener {
            showConfirmationDialog("MCB Card")
        }

        addHBLLayout.setOnClickListener {
            showConfirmationDialog("Askari Bank")
        }

        addCashOnDeliveryLayout.setOnClickListener {
            showConfirmationDialog("Cash on Delivery")
        }
    }

    /**
     * Show a confirmation dialog for the selected payment method
     */
    private fun showConfirmationDialog(paymentMethod: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Payment Method")
            .setMessage("Are you sure you want to proceed with $paymentMethod?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Handle payment confirmation
                dialog.dismiss()
                proceedWithPayment(paymentMethod)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Handle the payment process
     */
    private fun proceedWithPayment(paymentMethod: String) {
        // Logic for proceeding with the selected payment method
        // For example, navigate to the payment processing screen or update UI
    }
}
