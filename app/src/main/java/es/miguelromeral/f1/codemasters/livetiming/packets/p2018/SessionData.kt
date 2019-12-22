package es.miguelromeral.f1.codemasters.livetiming.packets

import android.content.res.Resources
import classes.toplayer.Standard
import classes.toplayer.Standard.TRACKS
import classes.toplayer.Standard.TRACKS.ABU_DHABI
import classes.toplayer.Standard.TRACKS.AUSTRIA
import classes.toplayer.Standard.TRACKS.BAKU
import classes.toplayer.Standard.TRACKS.BRAZIL
import classes.toplayer.Standard.TRACKS.CATALUNYA
import classes.toplayer.Standard.TRACKS.HOCKENHEIM
import classes.toplayer.Standard.TRACKS.HUNGARORING
import classes.toplayer.Standard.TRACKS.MELBOURNE
import classes.toplayer.Standard.TRACKS.MEXICO
import classes.toplayer.Standard.TRACKS.MONACO
import classes.toplayer.Standard.TRACKS.MONTREAL
import classes.toplayer.Standard.TRACKS.MONZA
import classes.toplayer.Standard.TRACKS.PAUL_RICARD
import classes.toplayer.Standard.TRACKS.SAKHIR
import classes.toplayer.Standard.TRACKS.SAKHIR_SHORT
import classes.toplayer.Standard.TRACKS.SHANGHAI
import classes.toplayer.Standard.TRACKS.SILVERSTONE
import classes.toplayer.Standard.TRACKS.SILVERSTONE_SHORT
import classes.toplayer.Standard.TRACKS.SINGAPORE
import classes.toplayer.Standard.TRACKS.SOCHI
import classes.toplayer.Standard.TRACKS.SPA
import classes.toplayer.Standard.TRACKS.SUZUKA
import classes.toplayer.Standard.TRACKS.SUZUKA_SHORT
import classes.toplayer.Standard.TRACKS.TEXAS
import classes.toplayer.Standard.TRACKS.TEXAS_SHORT
import classes.toplayer.Standard.WEATHER.LIGHT_CLOUD
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.Packet
import java.nio.ByteBuffer
import java.nio.ByteOrder


@kotlin.ExperimentalUnsignedTypes
class SessionData private constructor(header: PacketHeader, content: ByteArray) : Packet(header) {

    var weather: UByte
    var trackTemperature: Byte
    var airTemperature: Byte
    var totalLaps: UByte
    var trackLength: Short
    var sessionType: UByte
    var trackId: Byte
    var era: UByte
    var sessionTimeLeft: Short
    var sessionDuration: Short
    var pitSpeedLimit: UByte
    var gamePaused: UByte
    var isSpectating: UByte
    var spectatorCardIndex: UByte
    var sliProNativeSupport: UByte
    var numMarshalZones: UByte
    var marshalZones : List<MarshalZone>
    var safetyCarStatus: UByte
    var networkGame: UByte

    init{
        val byteBuffer = ByteBuffer.allocate(4)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

        weather = content.get(0).toUByte()
        trackTemperature = content.get(1)
        airTemperature = content.get(2)
        totalLaps = content.get(3).toUByte()
        trackLength = shortFromPacket(content.sliceArray(4..5))
        sessionType = content.get(6).toUByte()
        trackId = content[7]
        era = content[8].toUByte()
        sessionTimeLeft = shortFromPacket(content.sliceArray(9..10))
        sessionDuration = shortFromPacket(content.sliceArray(11..12))
        pitSpeedLimit = content[13].toUByte()
        gamePaused = content[14].toUByte()
        isSpectating = content[15].toUByte()
        spectatorCardIndex = content[16].toUByte()
        sliProNativeSupport = content[17].toUByte()
        numMarshalZones = content[18].toUByte()
        val finaloffset = MAX_MARSHAL_ZONES * MarshalZone.SIZE + 19
        marshalZones = createMarshalZones(
            content.sliceArray(
                19 until finaloffset)
            , 0)
        safetyCarStatus = content[finaloffset].toUByte()
        networkGame = content[finaloffset + 1].toUByte()
    }

    private fun createMarshalZones(content: ByteArray, iteration: Int): List<MarshalZone> {
        if(iteration >= MAX_MARSHAL_ZONES)
            return emptyList()

        val init = iteration * MarshalZone.SIZE
        val end = init + MarshalZone.SIZE

        return listOf(
            MarshalZone(content.sliceArray(init until end))
        ) + createMarshalZones(content, iteration + 1)

    }

    fun getStandardEra() = when(era.toInt()){
        0 -> Standard.ERA.MODERN_2018
        1 -> Standard.ERA.CLASSIC_2018
        else -> Standard.UNKNOWN
    }

    fun getStandardWeather(): Int =
        when (weather.toInt()) {
            0 -> Standard.WEATHER.CLEAR
            1 -> Standard.WEATHER.LIGHT_CLOUD
            2 -> Standard.WEATHER.OVERCAST
            3 -> Standard.WEATHER.LIGHT_RAIN
            4 -> Standard.WEATHER.HEAVY_RAIN
            5 -> Standard.WEATHER.STORM
            else -> Standard.UNKNOWN
        }

    fun getStandardSafetyCarStatus(): Int = when(safetyCarStatus?.toInt()){
        0 -> Standard.SAFETY_CAR.CLEAR
        1 -> Standard.SAFETY_CAR.SC
        2 -> Standard.SAFETY_CAR.VSC
        else -> Standard.UNKNOWN
    }

    fun getStandardTrackId(): Int = when(trackId.toInt()){
        0 -> MELBOURNE
        1 -> PAUL_RICARD
        2 -> SHANGHAI
        3 -> SAKHIR
        4 -> CATALUNYA
        5 -> MONACO
        6 -> MONTREAL
        7 -> SILVERSTONE
        8 -> HOCKENHEIM
        9 -> HUNGARORING
        10 -> SPA
        11 -> MONZA
        12 -> SINGAPORE
        13 -> SUZUKA
        14 -> ABU_DHABI
        15 -> TEXAS
        16 -> BRAZIL
        17 -> AUSTRIA
        18 -> SOCHI
        19 -> MEXICO
        20 -> BAKU
        21 -> SAKHIR_SHORT
        22 -> SILVERSTONE_SHORT
        23 -> TEXAS_SHORT
        24 -> SUZUKA_SHORT
        else -> Standard.UNKNOWN
    }


    fun getStandardSessionType(): Int = when(sessionType.toInt()){
        1 -> Standard.SESSION.P1
        2 -> Standard.SESSION.P2
        3 -> Standard.SESSION.P3
        4 -> Standard.SESSION.SP
        5 -> Standard.SESSION.Q1
        6 -> Standard.SESSION.Q2
        7 -> Standard.SESSION.Q3
        8 -> Standard.SESSION.SQ
        9 -> Standard.SESSION.OQ
        10 -> Standard.SESSION.RACE
        11 -> Standard.SESSION.RACE2
        12 -> Standard.SESSION.TT
        else -> Standard.UNKNOWN
    }

    companion object {
        const val PACKET_ID = 1
        const val MAX_MARSHAL_ZONES = 21

        fun getNateworkGame(networkGame: UByte):String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (networkGame.toInt()) {
                        0 -> R.string.game_mode_offline
                        1 -> R.string.game_mode_online
                        else -> R.string.unknown
                    }
                )
            }
            return "Unknown"
        }

        fun getSafetyCarStatus(safetyCarStatus: UByte?): String {
            MyApplication.getContext()?.resources?.let {resources ->
                safetyCarStatus?.let {
                    return resources.getString(
                        when (it.toInt()) {
                            0 -> R.string.safety_car_no
                            1 -> R.string.safety_car_sc
                            2 -> R.string.safety_car_vsc
                            else -> R.string.unknown
                        }
                    )
                }
            }
            return "Unknown"
        }



        fun getSessionType(session: UByte):String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (session.toInt()) {
                        1 -> R.string.session_p1
                        2 -> R.string.session_p2
                        3 -> R.string.session_p3
                        4 -> R.string.session_short_p
                        5 -> R.string.session_q1
                        6 -> R.string.session_q2
                        7 -> R.string.session_q3
                        8 -> R.string.session_short_q
                        9 -> R.string.session_out_q
                        10 -> R.string.session_r
                        11 -> R.string.session_r2
                        12 -> R.string.session_time_trial
                        else -> R.string.unknown
                    }
                )
            }
            return "Unknown"
        }

        fun create(header: PacketHeader, content: ByteArray): SessionData{
            return SessionData(header, content.sliceArray(PacketHeader.HEADER_SIZE until content.size))
        }
    }
}

class MarshalZone internal constructor(content: ByteArray)
{
    var zoneStart: Float
    var zoneFlag: Byte

    init{
        zoneStart = floatFromPacket(content.sliceArray(0..3))
        zoneFlag = content[4]
    }

    companion object{
        const val SIZE = 5
    }
}