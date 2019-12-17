package es.miguelromeral.f1.codemasters.livetiming.packets

import android.content.res.Resources
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


    companion object {
        const val PACKET_ID = 1
        const val MAX_MARSHAL_ZONES = 21

        fun getTrack(trackId: Byte):String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (trackId.toInt()) {
                        0 -> R.string.track_melbourne
                        1 -> R.string.track_paul_ricard
                        2 -> R.string.track_shangai
                        3 -> R.string.track_sakhir
                        4 -> R.string.track_catalunya
                        5 -> R.string.track_monaco
                        6 -> R.string.track_montreal
                        7 -> R.string.track_silverstone
                        8 -> R.string.track_hockenheim
                        9 -> R.string.track_hungaroring
                        10 -> R.string.track_spa
                        11 -> R.string.track_monza
                        12 -> R.string.track_singapore
                        13 -> R.string.track_suzuka
                        14 -> R.string.track_abu_dhabi
                        15 -> R.string.track_texas
                        16 -> R.string.track_brazil
                        17 -> R.string.track_austria
                        18 -> R.string.track_sochi
                        19 -> R.string.track_mexico
                        20 -> R.string.track_baku
                        21 -> R.string.track_sakhir_short
                        22 -> R.string.track_silverstone_short
                        23 -> R.string.track_texas_short
                        24 -> R.string.track_suzuka_short
                        else -> R.string.unknown
                    }
                )
            }
            return "Unknown"
        }

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

        fun getSafetyCarStatus(safetyCarStatus: UByte) :String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (safetyCarStatus.toInt()) {
                        0 -> R.string.safety_car_no
                        1 -> R.string.safety_car_sc
                        2 -> R.string.safety_car_vsc
                        else -> R.string.unknown
                    }
                )
            }
            return "Unknown"
        }

        fun getEra(era: UByte):String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (era.toInt()) {
                        0 -> R.string.era_modern
                        1 -> R.string.era_classic
                        else -> R.string.unknown

                    }
                )
            }
            return "Unknown"
        }

        fun getWeather(weather: UByte):String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(when (weather.toInt()) {
                    0 -> R.string.weather_clear
                    1 -> R.string.weather_light_cloud
                    2 -> R.string.weather_overcast
                    3 -> R.string.weather_light_rain
                    4 -> R.string.weather_heavy_rain
                    5 -> R.string.weather_storm
                    else -> R.string.unknown
                }
            )}
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