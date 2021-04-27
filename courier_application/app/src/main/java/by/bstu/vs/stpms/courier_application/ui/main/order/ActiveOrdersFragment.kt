package by.bstu.vs.stpms.courier_application.ui.main.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.bstu.vs.stpms.courier_application.R

class ActiveOrdersFragment : Fragment() {

    private lateinit var viewModel: ActiveOrdersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.active_orders_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActiveOrdersViewModel::class.java)

    }

}