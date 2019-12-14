package es.miguelromeral.f1.codemasters.livetiming.ui.main

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.classes.Player


@BindingAdapter("timeFormatted")
fun TextView.setTimeFormatted(player: Player) {
    if(player.currentLap.value != null){
        text = player.currentLap.value?.currentLapTime.toString()
    }else{
        text = "..."
    }
}


@BindingAdapter("teamColor")
fun ImageView.setColorByTeam(teamId: UByte?){
    teamId?.let{
        setBackgroundColor(when(it){
            else -> R.color.fullWhite
        })
    }
}


fun floatToTimeFormatted(inf : Float, long: Boolean = false): String {
    val mins = (inf / 60f).toInt()
    val secs = (inf % 60f).toInt()
    val decimals = if(long) 1000 else 10
    val tent = ((inf * decimals) % decimals).toInt()
    var string = ""
    if(mins.toInt() != 0)
        string += "$mins:"
    return string + "${secs}.$tent"
}