package com.andreas.fly

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class BusyDialogFragment: DialogFragment() {
    companion object {
        private const val FRAGMENT_TAG = "busy"

        fun newInstance() = BusyDialogFragment()

        fun show(supportFragmentManager: FragmentManager): BusyDialogFragment {
            val dialog = BusyDialogFragment.newInstance()
            // prevent dismiss by user click
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, FRAGMENT_TAG)
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // return super.onCreateView(inflater, container, savedInstanceState)

        // make white background transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return activity!!.layoutInflater.inflate(R.layout.fragment_busy_dialog, container)
    }

    override fun onStart() {
        super.onStart()

        // remove black outer overlay, or change opacity
        dialog?.window?.also { window ->
            window.attributes?.also { attributes ->
                attributes.dimAmount = 0.1f
                window.attributes = attributes
            }
        }
    }
}
