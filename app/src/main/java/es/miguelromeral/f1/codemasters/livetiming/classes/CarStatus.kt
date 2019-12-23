package es.miguelromeral.f1.codemasters.livetiming.classes

import androidx.lifecycle.MutableLiveData
import classes.toplayer.Standard
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.CarUDPData
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData
import es.miguelromeral.f1.codemasters.livetiming.standard.TYRES_POSITION
import java.text.Format

class CarStatus {

    var format = Standard.UNKNOWN

    // ERS

    var ersStoreEnergy = MutableLiveData<Float>(0f)
    var ersDeployMode = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())
    var ersHarvestedThisLapMGUK = MutableLiveData<Float>(0f)
    var ersHarvestedThisLapMGUH = MutableLiveData<Float>(0f)
    var ersDeployedThisLap = MutableLiveData<Float>(0f)

    // Damage

    var frontLeftWingDamage = MutableLiveData<Byte>(0)
    var frontRightWingDamage = MutableLiveData<Byte>(0)
    var rearWingDamage = MutableLiveData<Byte>(0)
    var tyresDamage = MutableLiveData<List<Byte>>(mutableListOf())
    var engineDamage = MutableLiveData<UByte>(0u)
    var gearBoxDamage = MutableLiveData<UByte>(0u)
    var exhaustDamage = MutableLiveData<UByte>(0u)

    var tractionControl = MutableLiveData<UByte>(0u)
    var antiLockBrakes = MutableLiveData<UByte>(0u)
    var fuelMix = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())
    var frontBrakeBias = MutableLiveData<UByte>(0u)
    var pitLimiterStatus = MutableLiveData<UByte>(0u)
    var fuelInTank = MutableLiveData<Float>(0f)
    var fuelCapacity = MutableLiveData<Float>(0f)
    var maxRPM = MutableLiveData<Short>(0)
    var idleRPM = MutableLiveData<Short>(0)
    var maxGears = MutableLiveData<UByte>(0u)
    var drsAllowed = MutableLiveData<UByte>(0u)
    var tyresWear = MutableLiveData<List<UByte>>(mutableListOf())
    var tyreCompound = MutableLiveData<Byte>(Standard.UNKNOWN.toByte())

    var vehicleFiaFlags = MutableLiveData<Byte>(0)



    @Synchronized
    fun updateFrom2018(info: CarStatusData){
        format = Standard.FORMAT.F18
        tractionControl.postValue(info.tractionControl)
        antiLockBrakes.postValue(info.antiLockBrakes)
        fuelMix.postValue(info.getStandardFuelMix().toByte())
        frontBrakeBias.postValue(info.frontBrakeBias)
        pitLimiterStatus.postValue(info.pitLimiterStatus)
        fuelInTank.postValue(info.fuelInTank)
        fuelCapacity.postValue(info.fuelCapacity)
        maxRPM.postValue(info.maxRPM)
        idleRPM.postValue(info.idleRPM)
        maxGears.postValue(info.maxGears)
        drsAllowed.postValue(info.drsAllowed)
        tyresWear.postValue(info.tyresWear)
        tyreCompound.postValue(info.getStandardTyreCompound().toByte())
        tyresDamage.postValue(info.tyresDamage.map{
            it.toByte()
        })
        frontLeftWingDamage.postValue(info.frontLeftWingDamage.toByte())
        frontRightWingDamage.postValue(info.frontRightWingDamage.toByte())
        rearWingDamage.postValue(info.rearWingDamage.toByte())
        engineDamage.postValue(info.engineDamage)
        gearBoxDamage.postValue(info.gearBoxDamage)
        exhaustDamage.postValue(info.exhaustDamage)
        vehicleFiaFlags.postValue(info.vehicleFiaFlags)
        ersStoreEnergy.postValue(info.ersStoreEnergy)
        ersDeployMode.postValue(info.ersDeployMode.toByte())
        ersHarvestedThisLapMGUK.postValue(info.ersHarvestedThisLapMGUK)
        ersHarvestedThisLapMGUH.postValue(info.ersHarvestedThisLapMGUH)
        ersDeployedThisLap.postValue(info.ersDeployedThisLap)
    }

    @Synchronized
    fun updateFrom2017(info: CarUDPData){
        format = Standard.FORMAT.F17
        tyreCompound.postValue(info.getStandardTyreCompound().toByte())
        pitLimiterStatus.postValue(info.inPits.toUByte())
    }

    fun getTyreDamage(position: Int): Byte = when(position){
        TYRES_POSITION.RL, TYRES_POSITION.RR,
        TYRES_POSITION.FL, TYRES_POSITION.FR ->
        {
             tyresDamage.value?.let{
                it[position]
             }
            Standard.UNKNOWN.toByte()
        }
        else -> Standard.UNKNOWN.toByte()
    }
}