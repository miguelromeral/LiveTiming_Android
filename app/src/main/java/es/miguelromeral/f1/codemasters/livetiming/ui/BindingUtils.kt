package es.miguelromeral.f1.codemasters.livetiming.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.classes.Player
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData
import timber.log.Timber


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
            else -> R.color.colorAccent
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
    if(secs < 10)
        string += "0"
    return string + "${secs}.$tent"
}

fun getTyreIcon(format: Format, era: UByte?, compound: UByte?): Int{
    when(format){
        Format.F1_2018 -> {
            if(era != null) {
                when (era) {
                    CarStatusData.ERA_MODERN -> {
                        if (compound != null)
                            when (compound.toInt()) {
                                0 -> return R.drawable.tyre_pink
                                1 -> return R.drawable.tyre_purple
                                2 -> return R.drawable.tyre_red
                                3 -> return R.drawable.tyre_yellow
                                4 -> return R.drawable.tyre_white
                                5 -> return R.drawable.tyre_cyan
                                6 -> return R.drawable.tyre_orange
                                7 -> return R.drawable.tyre_green
                                8 -> return R.drawable.tyre_blue
                            }
                    }
                    CarStatusData.ERA_CLASSIC -> {
                        if (compound != null)
                            when (compound.toInt()) {
                                in 0..6 -> return R.drawable.tyre_yellow
                                else -> return R.drawable.tyre_blue
                            }
                    }
                }
            }

        }
    }
    return R.drawable.tyre_unknown
}