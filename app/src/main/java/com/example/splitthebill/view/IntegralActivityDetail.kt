package com.example.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.splitthebill.adapters.IntegralAdapter
import com.example.splitthebill.adapters.IntegralAdapterDetail
import com.example.splitthebill.databinding.ActivityIntegralDetailBinding
import com.example.splitthebill.model.Constants.VIEW_CRACK
import com.example.splitthebill.model.Integral

class IntegralActivityDetail : AppCompatActivity() {
    private val aidb: ActivityIntegralDetailBinding by lazy{
        ActivityIntegralDetailBinding.inflate(layoutInflater)
    }

    private var integralList: ArrayList<Integral> = arrayListOf()

    private lateinit var integralDetailAdapter: IntegralAdapterDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aidb.root)

        supportActionBar?.subtitle = "Detalhes do racha"

        val receivedIntegralDetail = intent.getParcelableArrayListExtra<Integral>(VIEW_CRACK)
        if(receivedIntegralDetail != null){
            integralList = receivedIntegralDetail
        }

        integralDetailAdapter = IntegralAdapterDetail(this, integralList)
        aidb.integrantesDetalhesLv.adapter = integralDetailAdapter
    }
}