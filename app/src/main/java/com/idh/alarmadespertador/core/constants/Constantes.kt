package com.idh.alarmadespertador.core.constants


class Constantes {


    companion object{
        //Room
        const val TEMPORIZADOR_TABLE = "temporizador_table"
        const val ALARMA_TABLE = "alarma_table"

        //Screens
        const val TEMPORIZADOR_SCREEN = "Temporizador"
        const val UPDATE_TEMPORIZADOR_SCREEN = "Update Temporizador"
        const val ALARMA_SCREEN = "Alarma"
        const val RADIO_SCREEN = "Radio"
        const val CLIMA_SCREEN = "Clima"
        const val CONFIGURAR_APARIENCIA = "Configurar Apariencia"
        const val CONFIGURAR_FORMATO = "Configurar Formato"

        //Arguments
        const val  TEMPORIZADOR_ID = "temporizadorId"

        //Actions
        const val ADD_TEMPORIZADOR = "Agregar temporizador"
        const val DELETE_TEMPORIZADOR = "Borrar un temporizador"

        //Buttons
        const val ADD = "Agregar"
        const val DISMISS ="Cancelar"
        const val UPDATE = "Modificar"

        // Placeholders
        const val NOMBRE_TEMPORIZADOR = "nombre..."
        const val HORAS = "Horas..."
        const val MINUTOS = "Minutos..."
        const val SEGUNDOS = "Segundos..."
        const val  NO_VALUE = ""
    }
}