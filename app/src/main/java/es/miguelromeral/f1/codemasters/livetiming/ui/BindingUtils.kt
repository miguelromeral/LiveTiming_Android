package es.miguelromeral.f1.codemasters.livetiming.ui

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import androidx.preference.PreferenceManager
import classes.toplayer.Standard
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.classes.CarStatus
import es.miguelromeral.f1.codemasters.livetiming.classes.Player
import es.miguelromeral.f1.codemasters.livetiming.classes.Telemetry
import es.miguelromeral.f1.codemasters.livetiming.standard.PIT_STATUS


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
                    R.color.liveTimingListOdd
                else
                    R.color.liveTimingListEven
            )
        )
    }
}

@BindingAdapter("timeFormatted")
fun TextView.setTimeFormatted(time: Float?) {
    text = floatToTimeFormatted(time)
}

@BindingAdapter("tyre", "format", requireAll = false)
fun ImageView.setTyreImage(compound: Int?, format: Int?){
    setImageResource(Standard.TYRES.getTyreDrawable(compound))
}

@BindingAdapter("tyreName")
fun TextView.setTyreName(compound: Int?){
    text = context.getString(Standard.TYRES.getTyreName(compound))
}

@BindingAdapter("teamColor")
fun ImageView.setColorByTeam(team: Int?){
    setColorFilter(getColor(context, Standard.TEAMS.getTeamColor(team)))
}

@BindingAdapter("era")
fun TextView.setEra(era: Int?){
    text = context.getString(Standard.ERA.getEraName(era))
}

@BindingAdapter("track")
fun TextView.setTrack(track: Int?){
    text = context.getString(Standard.TRACKS.getTrackName(track))
}

@BindingAdapter("ersDeployedThisLap")
fun ProgressBar.setERSDeployedLap(deployed: Float?){
    deployed?.let{
        progress = (Standard.MAX_ERS_STORAGE - deployed).toInt()
        return
    }
    progress = 0
}

@BindingAdapter("speed")
fun TextView.setSpeed(speed: Short?){
    speed?.let{
        text = getSpeedValue(context, speed.toInt(), true)
        return
    }
    text = context.getString(R.string.speed_unknown)
}

@BindingAdapter("revs")
fun ProgressBar.configureRevs(telemetry: Telemetry?){
    telemetry?.let{
        max = Standard.MAX_RPM
        progress = telemetry.engineRPM.value?.toInt() ?: 0
        return
    }
    max = 1
    progress = 0
}





@BindingAdapter("revsCircle")
fun CircularProgressBar.setRevsCircle(revs: Short?){
    revs?.let{
        progress = revs.toFloat() % Standard.MAX_RPM
        return
    }
    progress = 0f
}


@BindingAdapter("speed_circle")
fun CircularProgressBar.setSpeedCircle(speed: Short?){
    speed?.let{
        progress = speed.toFloat()
        return
    }
    progress = 0f
}

@BindingAdapter("speed_indicator", "speed_show_unit", requireAll = false)
fun TextView.setSpeedIndicator(speed: Int, showUnit: Boolean = false){
    text = getSpeedValue(context, speed, showUnit)
}


@BindingAdapter("throttle_circle")
fun CircularProgressBar.setThrottleCircle(throttle: Short?){
    progress = throttle?.toFloat() ?: 0f
}




@BindingAdapter("drsStatus")
fun TextView.configureDRS(drs: Int?){
    drs?.let{
        setBackgroundResource(when(it){
            1 -> R.color.fullGreen
            else -> R.color.fullGray
        })
        return
    }
    setBackgroundResource(R.color.fullGray)
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

@BindingAdapter("weatherName")
fun TextView.setWeatherName(weather: Int?){
    weather?.let{
        text = context.getString(when(weather){
            Standard.WEATHER.CLEAR -> R.string.weather_clear
            Standard.WEATHER.LIGHT_CLOUD -> R.string.weather_light_cloud
            Standard.WEATHER.OVERCAST -> R.string.weather_overcast
            Standard.WEATHER.LIGHT_RAIN -> R.string.weather_light_rain
            Standard.WEATHER.HEAVY_RAIN -> R.string.weather_heavy_rain
            Standard.WEATHER.STORM -> R.string.weather_storm
            else -> R.string.unknown
        })
    }
}

@BindingAdapter("time_minutes_seconds")
fun TextView.setTimeMinutesSeconds(time: Int?){
    time?.let{
        val minutes = time / 60
        val seconds = time % 60
        //text = String.format("%d:%02d", minutes, seconds)
        text = context.getString(R.string.time_minutes_seconds, minutes, seconds)
        return
    }
    text = context.getString(R.string.time_minutes_seconds_undefined)
}



@BindingAdapter("temperature")
fun TextView.setTemperature(temperature: Int?){
    temperature?.let{
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(context.getString(R.string.preference_key_temperature_unit), context.getString(R.string.temperature_unit_c))

        when(preference){
            context.getString(R.string.temperature_unit_f) ->
                text = context.getString(R.string.temperature_f, convertCentigradesToFarenheit(it))
            else ->
                text = context.getString(R.string.temperature_c, it)
        }
        return
    }
    text = context.getString(R.string.temperature_undefined)
}

fun convertCentigradesToFarenheit(temperature: Int) = (temperature * 9 / 5) + 32

@BindingAdapter("gear")
fun TextView.setGear(gear: Int?){
    text = context.getString(Standard.getGear(gear))
}


@BindingAdapter("safetyCarStatus")
fun ImageView.setSafetyCarStatus(sc: Int?){
    when(sc) {
        Standard.SAFETY_CAR.SC, Standard.SAFETY_CAR.VSC -> setColorFilter(ContextCompat.getColor(context, R.color.rededYellow))
        else -> setColorFilter(ContextCompat.getColor(context, R.color.fullBlack))
    }
}

@BindingAdapter("sessionName")
fun TextView.setSessionName(session: Int?){
    text = context.getString(Standard.SESSION.getSessionName(session))
}

@BindingAdapter("safetyCarStatusName")
fun TextView.setSafetyCarStatusName(status: Int?){
    text = context.getString(Standard.SAFETY_CAR.getSafetyCarStatusName(status))
}


@BindingAdapter("length")
fun TextView.setLenght(length: Int?){
    length?.let{
        text = context.getString(R.string.length_meters, it)
        return
    }
    text = context.getString(R.string.length_undefined)
}

@BindingAdapter("ersPercentage")
fun TextView.setERSPercentage(value: Float?){
    value?.let{
        val tmp = ((value / 4000000f) * 100).toInt()
        text = context.getString(R.string.percentage, tmp)
        return
    }
    text = context.getString(R.string.percentage, 0)
}

@BindingAdapter("percentage")
fun TextView.setPercentageText(value: Int?){
    text = context.getString(R.string.percentage, value ?: 0)
}


@BindingAdapter("ersTotalHarvested")
fun ProgressBar.setERSHarvested(status: CarStatus?){
    status?.let{
        val sum = (it.ersHarvestedThisLapMGUH.value!! + it.ersHarvestedThisLapMGUK.value!!)
        progress = sum.toInt()
        return
    }
    progress = 0
}


@BindingAdapter("colorPitStatus")
fun TextView.setColorPitStatus(pitStatus: Int?){
    setTextColor(context.getColor(when(pitStatus){
        PIT_STATUS.NONE -> R.color.fullWhite
        else -> R.color.liveTimingBoxes
    }))
}



@BindingAdapter("sector1Time")
fun TextView.setSector1Time(player: Player){

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

fun getSpeedValue(context: Context, speed: Int, showUnit: Boolean = false): String {

    val preference = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(context.getString(R.string.preference_key_speed_unit), context.getString(R.string.speed_unit_kmh))

    return when(preference){
        context.getString(R.string.speed_unit_mph) -> {
            val valor = (speed * 0.621371).toShort()
            if (showUnit) {
                context.getString(R.string.speed_mph, valor)
            } else {
                valor.toString()
            }
        }
        else ->
            if(showUnit)
                context.getString(R.string.speed_kmh, speed)
            else
                speed.toString()
    }
}
/*
fun getTyreIcon(format: Format, era: UByte?, compound: UByte?): Int{
    w
}

 */


