package es.miguelromeral.f1.codemasters.livetiming.ui

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming

@BindingAdapter("backgroundByPosition")
fun setBackgroundByPosition(viewGroup: ViewGroup, position: UByte?){
    position?.let {
        viewGroup.setBackgroundColor(
            getColor(
                viewGroup.context,
                if (position.toInt() % 2 == 0)
                /*if(position == 0)
                R.color.colorPrimary
            else*/
                    R.color.liveTimingListEven
                else
                    R.color.liveTimingListOdd
            )
        )
    }
}

@BindingAdapter("timeFormatted")
fun TextView.setTimeFormatted(time: Float?) {
    text = floatToTimeFormatted(time)
}

@BindingAdapter("tyre", "format", "era", requireAll = false)
fun ImageView.setTyreImage(compound: Int?, format: Format, era: Int? = Packet2017.ERA_MODERN){
    var resource = R.drawable.tyre_unknown
    if(compound != null){
        when(format){
            Format.F1_2018 -> {
                if (era != null) {
                    when (era) {
                        CarStatusData.ERA_MODERN.toInt() -> {
                            if (compound != null)
                                resource = when (compound) {
                                    0 -> R.drawable.tyre_pink
                                    1 -> R.drawable.tyre_purple
                                    2 -> R.drawable.tyre_red
                                    3 -> R.drawable.tyre_yellow
                                    4 -> R.drawable.tyre_white
                                    5 -> R.drawable.tyre_cyan
                                    6 -> R.drawable.tyre_orange
                                    7 -> R.drawable.tyre_green
                                    8 -> R.drawable.tyre_blue
                                    else -> R.drawable.tyre_unknown
                                }
                        }
                        CarStatusData.ERA_CLASSIC.toInt() -> {
                            if (compound != null)
                                resource = when (compound) {
                                    in 0..6 -> R.drawable.tyre_yellow
                                    else -> R.drawable.tyre_blue
                                }
                        }
                    }
                }
            }
        }
    }
    setImageResource(resource)
}

@BindingAdapter("teamColor","format", requireAll = true)
fun ImageView.setColorByTeam(team: Int?, format: Format){
    if(team == null){
        setColorFilter(getColor(context, R.color.teamUnknown))
    }else{
        val resource = when(team){
            0 -> R.color.teamMercedes
            1 -> R.color.teamFerrari
            2 -> R.color.teamRedBull
            3 -> R.color.teamWilliams
            4 -> R.color.teamRacingPoint
            5 -> R.color.teamRenault
            6 -> R.color.teamToroRosso
            7 -> R.color.teamHaas
            8 -> R.color.teamMcLaren
            9 -> R.color.teamAlfaRomeo
            /*
            10 -> "McLaren 1988"
            11 -> "McLaren 1991"
            12 -> "Williams 1992"
            13 -> "Ferrari 1995"
            14 -> "Williams 1996"
            15 -> "McLaren 1998"
            16 -> "Ferrari 2002"
            17 -> "Ferrari 2004"
            18 -> "Renault 2006"
            19 -> "Ferrari 2007"
            20 -> "McLaren 2008"
            21 -> "RedBull 2010"
            22 -> "Ferrari 1976"
            34 -> "McLaren 1976"
            35 -> "Lotus 1972"
            36 -> "Ferrari 1979"
            37 -> "McLaren 1982"
            38 -> "Williams 2003"
            39 -> "Brawn 2009"
            40 -> "Lotus 1978"*/
            else -> R.color.teamUnknown
        }
        setColorFilter(getColor(context, resource))
    }
}


@BindingAdapter("weather")
fun ImageView.setWeatherIcon(weather: Int?){
    weather?.let{
        var resource = when(weather){
            0 -> R.drawable.clear_day
            1 -> R.drawable.light_cloud_day
            2 -> R.drawable.overcast
            3 -> R.drawable.light_rain
            4 -> R.drawable.heavy_rain
            5 -> R.drawable.storm
            else -> R.drawable.unknown
        }
        setImageResource(resource)
    }
}


@BindingAdapter("temperature")
fun TextView.setTemperature(temperature: String?){
    temperature?.let{
        text = temperature + "ºC"
        return
    }
    text = "--"
}


fun floatToTimeFormatted(inf : Float?, long: Boolean = false): String? {
    if(inf == null)
        return ""

    val mins = (inf / 60f).toInt()
    val secs = (inf % 60f).toInt()
    val decimals = if(long) 1000 else 10
    val tent = ((inf * decimals) % decimals).toInt()
    var string = ""
    if(mins != 0)
        string += "$mins:"
    if(secs < 10 && mins != 0)
        string += "0"
    return string + "${secs}.$tent"
}
/*
fun getTyreIcon(format: Format, era: UByte?, compound: UByte?): Int{
    w
}

 */


