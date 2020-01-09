package com.versilistyson.welldone.ui.dashboard.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.versilistyson.welldone.R
import com.versilistyson.welldone.adapter.OperatorLogAdapter
import com.versilistyson.welldone.data.local.model.OperatorLog
import com.versilistyson.welldone.data.remote.dto.SensorRecentResponse
import kotlinx.android.synthetic.main.fragment_dialog_pump_detail.*

class PumpDialogDetailFragment : DialogFragment() {

    private lateinit var viewModel: PumpDialogViewModel
    private lateinit var sensor: SensorRecentResponse
    private lateinit var logAdapter: OperatorLogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_pump_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensor = arguments!!.getSerializable("sensor") as SensorRecentResponse
        initViewModel()
        toolbar_pump_details.setNavigationOnClickListener{
            dismiss()
        }
        bindSensor()
        viewModel.listOfLogs.add(
                OperatorLog(
                    "13/05/2018", "13/06/2019",
                    ContextCompat.getDrawable(context!!, R.drawable.pump_non_functioning)!!, "HIMAN"))
        viewModel.listOfLogs.add(
                OperatorLog("14/05/2020", "15/06/2020",
                    ContextCompat.getDrawable(context!!, R.drawable.pump_no_data)!!, "The pump was not working and it needed repairs"))
        initRecyclerView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(PumpDialogViewModel::class.java)
    }

    private fun initRecyclerView() {
        logAdapter = OperatorLogAdapter(viewModel.listOfLogs)
        rv_logs.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = logAdapter
        }
    }

    private fun bindSensor() {
        tv_pump_id.text = "Pump #${sensor.pump_index}"
        tv_last_upload_date.text = sensor.data_finished
        tv_well_depth.text = "${sensor.depth} feet"
        tv_province.text = sensor.province_name
        tv_district.text = sensor.district_name
        tv_commune_value.text = sensor.commune_name
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }
}