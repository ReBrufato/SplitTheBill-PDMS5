package com.example.splitthebill.adapters

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.splitthebill.R
import com.example.splitthebill.model.Integral

class IntegralAdapter(context: Context, private val integralList: MutableList<Integral>): ArrayAdapter<Integral>(context, R.layout.tile_integral, integralList){

    //cria classe do holder
    private data class TileIntegralHolder(val nomeTile: TextView, val valorPagoTile: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val integral: Integral = integralList[position]

        var integralTileView = convertView

        //infla nova célula
        if(integralTileView == null) {
            integralTileView =
                (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.tile_integral,parent, false)

            //instancia o holder
            val tileIntegralHolder = TileIntegralHolder(
                integralTileView.findViewById(R.id.nomeTile),
                integralTileView.findViewById(R.id.valorPagoTile),)

            integralTileView.tag = tileIntegralHolder
        }

        //atribuição do nome e valorPago para a célula
        with(integralTileView?.tag as TileIntegralHolder){
            nomeTile.text = integral.nome
            valorPagoTile.text = "Valor pago: " + integral.valorPago.toString()
        }

        return integralTileView
    }
}