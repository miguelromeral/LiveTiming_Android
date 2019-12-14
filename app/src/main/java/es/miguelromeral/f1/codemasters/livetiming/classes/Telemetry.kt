package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.Format
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarTelemetryData


@ExperimentalUnsignedTypes
class Telemetry {

    var format = Format.UNKNOWN

    var speed = MutableLiveData<Short>(0)
    var throttle = MutableLiveData<UByte>(0u)
    var steer = MutableLiveData<Byte>(0)
    var brake = MutableLiveData<UByte>(0u)
    var clutch = MutableLiveData<UByte>(0u)
    var gear = MutableLiveData<Byte>(0)
    var engineRPM = MutableLiveData<Short>(0)
    var drs = MutableLiveData<UByte>(0u)
    var revLightsPercent = MutableLiveData<UByte>(0u)
    var brakesTemperature = MutableLiveData<List<Short>>()
    var tyresSurfaceTemperature = MutableLiveData<List<Short>>()
    var tyresInnerTemperature = MutableLiveData<List<Short>>()
    var engineTemperature = MutableLiveData<Short>(0)
    var tyresPressure = MutableLiveData<List<Float>>()

    @Synchronized
    fun updateFrom2018(info: CarTelemetryData){
        format = Format.F1_2018
        speed.postValue(info.speed)
        throttle.postValue(info.throttle)
        steer.postValue(info.steer)
        brake.postValue(info.brake)
        clutch.postValue(info.clutch)
        gear.postValue(info.gear)
        engineRPM.postValue(info.engineRPM)
        drs.postValue(info.drs)
        revLightsPercent.postValue(info.revLightsPercent)
        brakesTemperature.postValue(info.brakesTemperature)
        tyresSurfaceTemperature.postValue(info.tyresSurfaceTemperature)
        tyresInnerTemperature.postValue(info.tyresInnerTemperature)
        engineTemperature.postValue(info.engineTemperature)
        tyresPressure.postValue(info.tyresPressure)
    }
}