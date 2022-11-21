package com.example.splitthebill.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.splitthebill.R
import com.example.splitthebill.model.Integral
import kotlin.math.roundToInt

class IntegralAdapterDetail(context: Context, private val integralList: MutableList<Integral>): ArrayAdapter<Integral>(context, R.layout.tile_integral_detail, integralList){

    //cria classe do holder
    private data class TileIntegraDetailHolder(val nomeTileDetail: TextView, val aPagar: TextView, val aReceber: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val integral: Integral = integralList[position]

        var integralTileDetailView = convertView

        //infla nova célula
        if(integralTileDetailView == null) {
            integralTileDetailView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_integral_detail,parent, false)

            //instancia o holder
            val tileIntegralDetailHolder = TileIntegraDetailHolder(
                integralTileDetailView.findViewById(R.id.nomeTileDetail),
                integralTileDetailView.findViewById(R.id.aPagar),
                integralTileDetailView.findViewById(R.id.aReceber),)

            integralTileDetailView.tag = tileIntegralDetailHolder
        }

        //atribuição do nome e valorPago para a célula
        with(integralTileDetailView?.tag as TileIntegraDetailHolder){
            nomeTileDetail.text = "Integrante: " + integral.nome

            var diferenca = calculaDiferenca(mediaValores(), integral)
            var diferencaOff = (diferenca * 100).roundToInt() / 100.0

            if(diferenca < 0){
                diferencaOff *= -1
                aReceber.text = "Valor a receber: " + diferencaOff.toString()
                aPagar.visibility = View.GONE
            }else if(diferenca > 0){
                aPagar.text = "Valor a pagar: " + diferencaOff.toString()
                aReceber.visibility = View.GONE
            }else{
                aPagar.text = "Nada a pagar ou a receber"
                aReceber.visibility = View.GONE
            }
        }
        return integralTileDetailView
    }

    fun mediaValores(): Double{
        var media: Double
        var soma: Double = 0.0

        for(integral in integralList){
            soma += integral.valorPago
        }
        media = soma/integralList.size

        return media
    }

    fun calculaDiferenca(media: Double, integrante: Integral): Double {
        return media - integrante.valorPago
    }
}