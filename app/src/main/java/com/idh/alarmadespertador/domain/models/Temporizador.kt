package com.idh.alarmadespertador.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.idh.alarmadespertador.core.constants.Constantes.Companion.TEMPORIZADOR_TABLE
@Entity (tableName = TEMPORIZADOR_TABLE)
data class Temporizador(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val horas : String,
    val minutos : String,
    val segundos : String,
    val estado : Boolean
)