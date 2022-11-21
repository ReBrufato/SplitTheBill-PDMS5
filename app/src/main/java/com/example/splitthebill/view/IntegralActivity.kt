package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.splitthebill.databinding.ActivityIntegralBinding
import com.example.splitthebill.model.Constants.EXTRA_INTEGRAL
import com.example.splitthebill.model.Constants.VIEW_INTEGRAL
import com.example.splitthebill.model.Integral

import java.lang.Double
import kotlin.random.Random

class IntegralActivity : AppCompatActivity() {
    private val aib: ActivityIntegralBinding by lazy{
        ActivityIntegralBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aib.root)

        //recebe a intent para editar integrante e seta os dados dele na tela
        val receivedIntegral = intent.getParcelableExtra<Integral>(EXTRA_INTEGRAL)
        receivedIntegral?.let{_receivedIntegral ->
            with(_receivedIntegral){
                aib.nomeEt.setText(nome)
                aib.valorPagoEt.setText(valorPago.toString())
                aib.listaComprasEt.setText(listaCompra.toString())
            }
        }

        //desativa a edição do editText e 'esconde' nome e valorPago, permitindo apenas visualização da lista de compras
        val viewIntegral = intent.getBooleanExtra(VIEW_INTEGRAL,false)
        if(viewIntegral){
            aib.nomeEt.visibility = View.GONE
            aib.valorPagoEt.visibility = View.GONE
            aib.salvarIntegranteBt.visibility = View.GONE

            //verifica se algum item foi comprado pelo integrante em questão
            if(receivedIntegral != null){
                if (aib.listaComprasEt.text.toString() == "") {
                    aib.listaComprasEt.setText("Nenhum item foi comprado pelo(a) ${receivedIntegral.nome}")
                }
            }
            aib.listaComprasEt.isEnabled = false
        }

        //salva o integrante na lista de integrantes
        aib.salvarIntegranteBt.setOnClickListener {
            val integral = Integral(
                id = receivedIntegral?.id?: Random(System.currentTimeMillis()).nextInt(),
                nome = aib.nomeEt.text.toString(),
                valorPago = aib.valorPagoEt.text.toString().toDouble(),
                listaCompra = aib.listaComprasEt.text.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_INTEGRAL, integral)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}

