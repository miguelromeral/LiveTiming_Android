package es.miguelromeral.f1.codemasters.livetiming.ui

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.standard.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData


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
                    es.miguelromeral.f1.codemasters.livetiming.R.color.liveTimingListEven
                else
                    es.miguelromeral.f1.codemasters.livetiming.R.color.liveTimingListOdd
            )
        )
    }
}

@BindingAdapter("timeFormatted")
fun TextView.setTimeFormatted(time: Float?) {
    text = floatToTimeFormatted(time)
}

@BindingAdapter("tyre", "format", requireAll = false)
fun ImageView.setTyreImage(compound: Int?, format: Format){
    setImageResource(Standard.TYRES.getTyreDrawable(compound))
}

@BindingAdapter("teamColor")
fun ImageView.setColorByTeam(team: Int?){
    setColorFilter(getColor(context, Standard.TEAMS.getTeamColor(team)))
}

@BindingAdapter("era")
fun TextView.setEra(era: Int?){
    text = context.getString(Standard.ERA.getEraName(era))
}


@BindingAdapter("weather")
fun ImageView.setWeatherIcon(weather: Int?){
    weather?.let{
        var resource = when(weather){
            0 -> es.miguelromeral.f1.codemasters.livetiming.R.drawable.clear_day
            1 -> es.miguelromeral.f1.codemasters.livetiming.R.drawable.light_cloud_day
            2 -> es.miguelromeral.f1.codemasters.livetiming.R.drawable.overcast
            3 -> es.miguelromeral.f1.codemasters.livetiming.R.drawable.light_rain
            4 -> es.miguelromeral.f1.codemasters.livetiming.R.drawable.heavy_rain
            5 -> es.miguelromeral.f1.codemasters.livetiming.R.drawable.storm
            else -> es.miguelromeral.f1.codemasters.livetiming.R.drawable.unknown
        }
        setImageResource(resource)
    }
}

@BindingAdapter("time_minutes_seconds")
fun TextView.setTimeMinutesSeconds(time: Int?){
    time?.let{
        val minutes = time / 60
        val seconds = time % 60
        text = String.format("%d:%02d", minutes, seconds)
        return
    }
    text = "-.--"
}

@BindingAdapter("temperature")
fun TextView.setTemperature(temperature: String?){
    temperature?.let{
        text = temperature + "ºC"
        return
    }
    text = "--"
}

/////////////////////////////////

        //DOESN'T WORK YET

/////////////////////////////////
@BindingAdapter("safetyCarStatus")
fun ImageView.setSafetyCarStatus(sc: Int?){
    if(sc != null) {
        if (sc in 1..2) {
            setColorFilter(R.color.colorAccent)
            return
        }
    }
    setColorFilter(R.color.colorAccent)
}


@BindingAdapter("length")
fun TextView.setLenght(length: Int?){
    length?.let{
        text = "$it m."
        return
    }
    text = "- m."
}

@BindingAdapter("test")
fun TextView.setTest(value: Int?){
    value?.let{
        text = value.toString()
        return
    }
    text = "---"
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


