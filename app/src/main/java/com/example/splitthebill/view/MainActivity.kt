package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.splitthebill.R
import com.example.splitthebill.adapters.IntegralAdapter
import com.example.splitthebill.databinding.ActivityMainBinding
import com.example.splitthebill.model.Constants.EXTRA_INTEGRAL
import com.example.splitthebill.model.Constants.VIEW_CRACK
import com.example.splitthebill.model.Constants.VIEW_INTEGRAL
import com.example.splitthebill.model.Integral

class MainActivity : AppCompatActivity() {
    //binding da activity_main.xml
    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //lista de integrantes
    private val integralList: MutableList<Integral> = mutableListOf()

    //lista usada para passar pela intent de details
    private val integralListDetail: ArrayList<Integral> = integralList as ArrayList<Integral>

    private lateinit var integralAdapter: IntegralAdapter

    private lateinit var iarl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        supportActionBar?.subtitle = "Lista de Integrantes"

        populaListaIntegral()

        integralAdapter = IntegralAdapter(this, integralList)
        amb.integrantesLv.adapter = integralAdapter

        //trata retorno da IntegralActivity
        iarl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ){result ->
            if(result.resultCode == RESULT_OK){
                val integral = result.data?.getParcelableExtra<Integral>(EXTRA_INTEGRAL)

                integral?.let {_integral->
                    //verifica se integrante já existe na lista (caso de edição)
                    val position = integralList.indexOfFirst { it.id == _integral.id }
                    if(position != -1) {
                        integralList[position] = _integral
                    }
                    //cria integrante novo
                    else{
                        integralList.add(_integral)
                    }
                    integralAdapter.notifyDataSetChanged()
                }
            }
        }

        //referencia o menu de contexto à activity_main
        registerForContextMenu(amb.integrantesLv)

        //trata clique curto de item da lista
        amb.integrantesLv.onItemClickListener = AdapterView.OnItemClickListener{_, _, position, _ ->
            val integral = integralList[position]
            val integralIntent = Intent(this@MainActivity, IntegralActivity::class.java)
            integralIntent.putExtra(EXTRA_INTEGRAL, integral)
            integralIntent.putExtra(VIEW_INTEGRAL, true)
            startActivity(integralIntent)
        }
    }

    //inflação do menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //seleção do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addIntegral -> {
                val intentIntegralActivity = Intent(this,IntegralActivity::class.java)
                iarl.launch(intentIntegralActivity)
                true
            }
            R.id.integralDetail -> {
                val intentIntegralDetail = Intent(this, IntegralActivityDetail::class.java)
                intentIntegralDetail.putParcelableArrayListExtra(VIEW_CRACK, integralListDetail)
                startActivity(intentIntegralDetail)
                true
            }
            else -> {false}
        }
    }

    //infla menu de contexto
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    //trata seleção do menu de contexto
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position

        return when(item.itemId) {
            R.id.removeIntegral -> {
                integralList.removeAt(position)
                integralAdapter.notifyDataSetChanged()
                true
            }
            R.id.editIntegral ->{
                val integral = integralList[position]
                val integralIntent = Intent(this, IntegralActivity::class.java)
                integralIntent.putExtra(EXTRA_INTEGRAL, integral)
                integralIntent.putExtra(VIEW_INTEGRAL, false)
                iarl.launch(integralIntent)
                true
            }
            else -> {false}
        }
    }

    private fun populaListaIntegral(){
        integralList.add(
            Integral(1,"Renan", 20.00, "Pepino, Laranja e Maçã")
        )
        integralList.add(
            Integral(2,"Girliene", 30.00, "Cereal e Ovo")
        )
        integralList.add(
            Integral(3,"Vitor", 192.00, "Cerveja,Alface e Picanha")
        )
        integralList.add(
            Integral(4,"Pedro", 104.00, "Contra Filé,Agrião e Ponta de peito")
        )
        integralList.add(
            Integral(6,"Lucia", 0.00, "")
        )

    }


}



