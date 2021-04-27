package by.bstu.vs.stpms.courier_application.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.courier_application.R

class ActiveDetailsFragment : Fragment() {

    private lateinit var activeDetailsViewModel: ActiveDetailsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activeDetailsViewModel =
                ViewModelProvider(this).get(ActiveDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_active_details, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        activeDetailsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}