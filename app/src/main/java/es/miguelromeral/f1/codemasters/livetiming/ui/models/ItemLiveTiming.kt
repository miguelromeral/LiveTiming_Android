package es.miguelromeral.f1.codemasters.livetiming.ui.models

import androidx.lifecycle.MutableLiveData
import es.miguelromeral.f1.codemasters.livetiming.packets.Format

data class ItemLiveTiming (
    var position: UByte?,
    var name: String?,
    var team: UByte?,
    var time: Float?,
    var compound: UByte?,
    var era: UByte?,
    var format: Format = Format.UNKNOWN)