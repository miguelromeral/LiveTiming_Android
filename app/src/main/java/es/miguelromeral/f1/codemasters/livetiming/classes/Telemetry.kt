package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarTelemetryData
import es.miguelromeral.f1.codemasters.livetiming.standard.Format


@ExperimentalUnsignedTypes
class Telemetry {

    var format = Format.UNKNOWN

    var speed = MutableLiveData<Short>(0)
    var throttle = MutableLiveData<Byte>(0)
    var steer = MutableLiveData<Byte>(0)
    var brake = MutableLiveData<Byte>(0)
    var clutch = MutableLiveData<UByte>(0u)
    var gear = MutableLiveData<Byte>(0)
    var engineRPM = MutableLiveData<Short>(0)
    var drs = MutableLiveData<Byte>(0)
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
        throttle.postValue(info.throttle.toByte())
        steer.postValue(info.steer)
        brake.postValue(info.brake.toByte())
        clutch.postValue(info.clutch)
        gear.postValue(info.gear)
        engineRPM.postValue(info.engineRPM)
        drs.postValue(info.drs.toByte())
        revLightsPercent.postValue(info.revLightsPercent)
        brakesTemperature.postValue(info.brakesTemperature)
        tyresSurfaceTemperature.postValue(info.tyresSurfaceTemperature)
        tyresInnerTemperature.postValue(info.tyresInnerTemperature)
        engineTemperature.postValue(info.engineTemperature)
        tyresPressure.postValue(info.tyresPressure)
    }
}