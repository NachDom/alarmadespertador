package com.idh.alarmadespertador.screens.alarmascreens.components

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.idh.alarmadespertador.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmaReceiver : BroadcastReceiver() {

    // La clase AlarmaReceiver extiende de BroadcastReceiver para recibir eventos de broadcast (difusión).
    // Específicamente, esta clase se utiliza para manejar eventos relacionados con alarmas en la aplicación.

    //  private var ringtone: Ringtone? = null
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AlarmaReceiver", "onReceive: Acción recibida - ${intent.action}")
        // Este método se llama cuando el AlarmaReceiver recibe un Intent (intención).
        when (intent.action) {
            "com.idh.alarmadespertador.ALARMA_ACTIVADA" -> {
                // Dependiendo de la acción del intent, se ejecutan diferentes bloques de código.
                val serviceIntent = Intent(context, AlarmaService::class.java).apply {
                    action = "com.idh.alarmadespertador.ALARMA_ACTIVADA"
                    // Cuando se recibe la acción ALARMA_ACTIVADA, se inicia un servicio en primer plano para manejar la alarma.
                    // Se crea un Intent para iniciar AlarmaService.
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
                val label = intent.getStringExtra("EXTRA_LABEL") ?: "Alarma"

                /*
                                val alarmaId = intent.getIntExtra("EXTRA_ID_ALARMA", -1)
                                val soundUri = intent.getStringExtra("EXTRA_SOUND_URI")
                                val vibrate = intent.getBooleanExtra("EXTRA_VIBRATE", false)
                                val label = intent.getStringExtra("EXTRA_LABEL") ?: "Alarma"

                                Log.d("AlarmaReceiver", "Alarma activada con ID: $alarmaId")
                                Log.d("AlarmaReceiver", "ID de Alarma: $alarmaId")
                                Log.d("AlarmaReceiver", "URI del Sonido: $soundUri")
                                Log.d("AlarmaReceiver", "Vibrar: $vibrate")
                                Log.d("AlarmaReceiver", "Etiqueta de la Alarma: $label")

                                // Reproducir sonido de la alarma
                                ringtone = if (soundUri != null) {
                                    RingtoneManager.getRingtone(context, Uri.parse(soundUri))
                                } else {
                                    RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                                }
                                ringtone?.play()

                 */
                // Mostrar notificación con acciones
                mostrarNotificacionAlarma(context, "Alarma Activada", label)
            }

            "com.idh.alarmadespertador.SNOOZE_ALARM" -> {
                val timestamp = intent.getLongExtra("EXTRA_NEW_ALARM_TIME", -1L)
                if (timestamp != -1L) {
                    val newAlarmTime = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timestamp),
                        ZoneId.systemDefault()
                    )
                    // Aquí implementar la lógica para reprogramar la alarma con newAlarmTime
                }
            }
        }
    }

    private fun mostrarNotificacionAlarma(context: Context, title: String, content: String) {
        // Esta función privada se utiliza para mostrar una notificación de alarma en el dispositivo.
        val notificationManager = NotificationManagerCompat.from(context)
        // Se obtiene una instancia de NotificationManager para enviar notificaciones.

        // Se construye la notificación con un título, texto y un icono pequeño.
        Log.d("AlarmaReceiver", "Preparando para mostrar notificación")
        val notificationBuilder = NotificationCompat.Builder(context, "alarma_channel_id")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(androidx.core.R.drawable.ic_call_answer)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Acción para detener la alarma
        val stopIntent = Intent(context, AlarmaReceiver::class.java).apply {
            action = "DETENER_ALARMA"
        }
        val stopPendingIntent = PendingIntent.getBroadcast(
            context, 0, stopIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        notificationBuilder.addAction(
            androidx.core.R.drawable.ic_call_answer,
            "Detener",
            stopPendingIntent
        )

        // Acción para posponer la alarma
        val snoozeIntent = Intent(context, AlarmaReceiver::class.java).apply {
            action = "POSTPONER_ALARMA"
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context, 1, snoozeIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        notificationBuilder.addAction(
            androidx.core.R.drawable.ic_call_answer_low,
            "Snooze",
            snoozePendingIntent
        )

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // El permiso no está concedido. Si es necesario, puedes solicitar el permiso aquí.
            Log.d("AlarmaReceiver", "Permiso POST_NOTIFICATIONS no concedido")
        } else {
            // El permiso está concedido. Puedes mostrar la notificación.
            notificationManager.notify(1, notificationBuilder.build());
            Log.d("AlarmaReceiver", "Notificación enviada al sistema")
        }
    }

    private fun reprogramarAlarmasActivas(context: Context) {
        // Esta función se encarga de reprogramar alarmas activas.
        // Por ejemplo, puede ser útil cuando se reinicia el dispositivo y se deben restablecer las alarmas.
        Log.d("AlarmaReceiver", "Reprogramando alarmas activas")
    }

}

