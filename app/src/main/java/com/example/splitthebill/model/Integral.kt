package com.example.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Integral(
    val id: Int,
    var nome: String,
    var valorPago: Double,
    var listaCompra: String,
): Parcelable
