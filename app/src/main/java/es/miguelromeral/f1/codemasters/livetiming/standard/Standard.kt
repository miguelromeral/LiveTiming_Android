package classes.toplayer

import android.content.Context
import android.os.Build.VERSION_CODES.P
import classes.toplayer.Standard.Companion.UNKNOWN_TEXT
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData.Companion.ERA_CLASSIC
import es.miguelromeral.f1.codemasters.livetiming.packets.p2018.CarStatusData.Companion.ERA_MODERN

class Standard {

    companion object{
        const val UNKNOWN = -1
        const val UNKNOWN_TEXT = "TBD"

        const val MAX_ERS_STORAGE = 4_000_000f
        const val HALF_ERS_STORAGE = MAX_ERS_STORAGE / 2

        const val MAX_RPM = 15_000

        fun getGear(gear: Int?): Int = when(gear){
            -1 -> R.string.gear_rear
            0 -> R.string.gear_n
            1 -> R.string.gear_1
            2 -> R.string.gear_2
            3 -> R.string.gear_3
            4 -> R.string.gear_4
            5 -> R.string.gear_5
            6 -> R.string.gear_6
            7 -> R.string.gear_7
            8 -> R.string.gear_8
            else -> R.string.gear_undefined
        }
    }

    object FORMAT {
        const val F17 = 0
        const val F18 = 1
        const val F19 = 2
        const val F20 = 3


    }


    object SAFETY_CAR {
        const val CLEAR = 0
        const val SC = 1
        const val VSC = 2

        fun getSafetyCarStatusName(status: Int?): Int = when(status){
            CLEAR -> R.string.safety_car_no
            SC -> R.string.safety_car_sc
            VSC -> R.string.safety_car_vsc
            else -> R.string.unknown
        }
    }

    object SESSION {
        const val P1 = 0
        const val P2 = 1
        const val P3 = 2
        const val SP = 3 // SHORT PRACTICE
        const val Q1 = 4
        const val Q2 = 5
        const val Q3 = 6
        const val SQ = 7 // SHORT QUALY
        const val OQ = 8// OUT Q1
        const val RACE = 9
        const val RACE2 = 10
        const val TT = 11

        fun getSessionName(session: Int?): Int = when(session?.toInt()){
            P1 -> R.string.session_p1
            P2 -> R.string.session_p2
            P3 -> R.string.session_p3
            SP -> R.string.session_short_p
            Q1 -> R.string.session_q1
            Q2 -> R.string.session_q2
            Q3 -> R.string.session_q3
            SQ -> R.string.session_short_q
            OQ -> R.string.session_out_q
            RACE -> R.string.session_r
            RACE2 -> R.string.session_r2
            TT -> R.string.session_time_trial
            else -> R.string.unknown
        }
    }

    object EVENT {
        const val START = 0
        const val END = 1
    }

    object AI {
        const val HUMAN = 0
        const val AI = 1
    }

    object WEATHER {
        const val CLEAR = 0
        const val LIGHT_CLOUD = 1
        const val OVERCAST = 2
        const val LIGHT_RAIN = 3
        const val HEAVY_RAIN = 4
        const val STORM = 5

        fun getWeatherName(weather: Int?) : Int = when(weather){
            CLEAR -> R.string.weather_clear
            LIGHT_CLOUD -> R.string.weather_light_cloud
            OVERCAST -> R.string.weather_overcast
            LIGHT_RAIN -> R.string.weather_light_rain
            HEAVY_RAIN -> R.string.weather_heavy_rain
            STORM -> R.string.weather_storm
            else -> R.string.unknown
        }
    }

    object TYRES {
        const val HYPERSOFT = 0
        const val ULTRASOFT = 1
        const val SUPERSOFT = 2
        const val SOFT = 3
        const val MEDIUM = 4
        const val HARD = 5
        const val SUPERHARD = 6
        const val INTER = 7
        const val WET = 8

        fun getTyreName(tyre: Int?): Int {
            tyre?.let{
                return when(tyre){
                    HYPERSOFT -> R.string.tyre_hypersoft
                    ULTRASOFT -> R.string.tyre_ultrasoft
                    SUPERSOFT -> R.string.tyre_supersoft
                    SOFT -> R.string.tyre_soft
                    MEDIUM -> R.string.tyre_medium
                    HARD -> R.string.tyre_hard
                    SUPERHARD -> R.string.tyre_superhard
                    INTER -> R.string.tyre_inter
                    WET -> R.string.tyre_wet
                    else -> R.string.unknown
                }
            }
            return R.string.unknown
        }

        fun getTyreColor(tyre: Int?): Int {
            tyre?.let{
                return when(tyre){
                    HYPERSOFT -> R.color.tyre_pink
                    ULTRASOFT -> R.color.tyre_purple
                    SUPERSOFT -> R.color.tyre_red
                    SOFT -> R.color.tyre_yellow
                    MEDIUM -> R.color.tyre_white
                    HARD -> R.color.tyre_cyan
                    SUPERHARD -> R.color.tyre_orange
                    INTER -> R.color.tyre_green
                    WET -> R.color.tyre_blue
                    else -> R.color.tyre_unknown
                }
            }
            return R.color.tyre_unknown
        }

        fun getTyreDrawable(tyre: Int?) = when (tyre) {
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

    object FUELMIX {
        const val LEAN = 0
        const val STANDARD = 1
        const val RICH = 2
        const val MAX = 3

        fun getFuelMixName(fuelmix: Int?) = when(fuelmix){
            LEAN -> R.string.fuelmix_lean
            STANDARD -> R.string.fuelmix_standard
            RICH -> R.string.fuelmix_rich
            MAX -> R.string.fuelmix_max
            else -> R.string.unknown
        }
    }

    object DRIVERS {
        const val VANDOORNE = 2
        const val RICCIARDO = 3
        const val VETTEL = 5
        const val RAIKKONEN = 7
        const val GROSJEAN = 8
        const val ERICCSON = 9
        const val GASLY = 10
        const val PEREZ = 11
        const val ALONSO = 14
        const val LECLERC = 16
        const val STROLL = 18
        const val MASSA = 19
        const val MAGNUSSEN = 20
        const val KVYAT = 26
        const val HULKENBERG = 27
        const val HARTLEY = 28
        const val PALMER = 30
        const val OCON = 31
        const val VERSTAPPEN = 33
        const val SIROTKIN = 35
        const val HAMILTON = 44
        const val SAINZ = 55
        const val BOTTAS = 77
        const val WEHRLEIN = 94
        const val BARNES = 200
        const val GILES = 201
        const val MURRAY = 202
        const val ROTH = 203
        const val CORREIA = 204
        const val LEVASSEUR = 205
        const val SCHIFFER = 206
        const val FOREST = 207
        const val LETOURNEAUU = 208
        const val SAARI = 209
        const val ATIYEH = 210
        const val CALABRESI = 211
        const val IZUM = 212
        const val CLARKE = 213
        const val KAUFMANN = 214
        const val LAURSEN = 215
        const val NIEVES = 216
        const val BELOUSOV = 217
        const val MICHALSKI = 218
        const val MORENO = 219
        const val COPPENS = 220
        const val VISSER = 221
        const val WALDMULLER = 222
        const val QUESADA = 223
        const val JONES = 224
        const val MEIJER = 225
        const val NAIR = 226
        const val TREMBLAY = 227

        fun name(driver: Int?): String {
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when (driver) {
                        SAINZ -> R.string.driver_Carlos_Sainz
                        MASSA -> R.string.driver_Felipe_Massa
                        RICCIARDO -> R.string.driver_Daniel_Ricciardo
                        ALONSO -> R.string.driver_Fernando_Alonso
                        RAIKKONEN -> R.string.driver_Kimi_Raikkonen
                        HAMILTON -> R.string.driver_Lewis_Hamilton
                        KVYAT -> R.string.driver_Daniil_Kvyat
                        ERICCSON -> R.string.driver_Marcus_Ericcson
                        VERSTAPPEN -> R.string.driver_Max_Verstappen
                        HULKENBERG -> R.string.driver_Nico_Hulkenberg
                        MAGNUSSEN -> R.string.driver_Kevin_Magnussen
                        GROSJEAN -> R.string.driver_Romain_Grosjean
                        VETTEL -> R.string.driver_Sebastian_Vettel
                        PEREZ -> R.string.driver_Sergio_Perez
                        BOTTAS -> R.string.driver_Valtteri_Bottas
                        OCON -> R.string.driver_Esteban_Ocon
                        VANDOORNE -> R.string.driver_Stoffel_Vandoorne
                        STROLL -> R.string.driver_Lance_Stroll
                        BARNES -> R.string.driver_Arron_Barnes
                        PALMER -> R.string.driver_Jolyon_Palmer
                        WEHRLEIN -> R.string.driver_Pascal_Wehrlein
                        GILES -> R.string.driver_Martin_Giles
                        MURRAY -> R.string.driver_Alex_Murray
                        ROTH -> R.string.driver_Lucas_Roth
                        CORREIA -> R.string.driver_Igor_Correia
                        LEVASSEUR -> R.string.driver_Sophie_Levasseur
                        SCHIFFER -> R.string.driver_Jonas_Schiffer
                        FOREST -> R.string.driver_Alain_Forest
                        LETOURNEAUU -> R.string.driver_Jay_Letourneau
                        SAARI -> R.string.driver_Esto_Saari
                        ATIYEH -> R.string.driver_Yasar_Atiyeh
                        CALABRESI -> R.string.driver_Callisto_Calabresi
                        IZUM -> R.string.driver_Naota_Izum
                        CLARKE -> R.string.driver_Howard_Clarke
                        KAUFMANN -> R.string.driver_Wilheim_Kaufmann
                        LAURSEN -> R.string.driver_Marie_Laursen
                        NIEVES -> R.string.driver_Flavio_Nieves
                        BELOUSOV -> R.string.driver_Peter_Belousov
                        MICHALSKI -> R.string.driver_Klimek_Michalski
                        MORENO -> R.string.driver_Santiago_Moreno
                        COPPENS -> R.string.driver_Benjamin_Coppens
                        VISSER -> R.string.driver_Noah_Visser
                        WALDMULLER -> R.string.driver_Gert_Waldmuller
                        QUESADA -> R.string.driver_Julian_Quesada
                        JONES -> R.string.driver_Daniel_Jones
                        LECLERC -> R.string.driver_Charles_Leclerc
                        GASLY -> R.string.driver_Pierre_Gasly
                        HARTLEY -> R.string.driver_Brendon_Hartley
                        SIROTKIN -> R.string.driver_Sergey_Sirotkin
                        MEIJER -> R.string.driver_Ruben_Meijer
                        NAIR -> R.string.driver_Rashid_Nair
                        TREMBLAY -> R.string.driver_Jack_Tremblay
                        else -> R.string.unknown
                    }
                )
            }
            return UNKNOWN_TEXT
        }
    }

    object TRACKS {
        const val MELBOURNE = 0
        const val PAUL_RICARD = 1
        const val SHANGHAI = 2
        const val SAKHIR = 3
        const val CATALUNYA = 4
        const val MONACO = 5
        const val MONTREAL = 6
        const val SILVERSTONE = 7
        const val HOCKENHEIM = 8
        const val HUNGARORING = 9
        const val SPA = 10
        const val MONZA = 11
        const val SINGAPORE = 12
        const val SUZUKA = 13
        const val ABU_DHABI = 14
        const val TEXAS = 15
        const val BRAZIL = 16
        const val AUSTRIA = 17
        const val SOCHI = 18
        const val MEXICO = 19
        const val BAKU = 20
        const val SEPANG = 21
        const val SAKHIR_SHORT = 31
        const val SILVERSTONE_SHORT = 32
        const val TEXAS_SHORT = 33
        const val SUZUKA_SHORT = 34

        fun getTrackName(trackId: Int?): Int = when (trackId) {
                MELBOURNE -> R.string.track_melbourne
                PAUL_RICARD -> R.string.track_paul_ricard
                SHANGHAI -> R.string.track_shangai
                SAKHIR -> R.string.track_sakhir
                CATALUNYA -> R.string.track_catalunya
                MONACO -> R.string.track_monaco
                MONTREAL -> R.string.track_montreal
                SILVERSTONE -> R.string.track_silverstone
                HOCKENHEIM -> R.string.track_hockenheim
                HUNGARORING -> R.string.track_hungaroring
                SPA -> R.string.track_spa
                MONZA -> R.string.track_monza
                SINGAPORE -> R.string.track_singapore
                SUZUKA -> R.string.track_suzuka
                SEPANG -> R.string.track_sepang
                ABU_DHABI -> R.string.track_abu_dhabi
                TEXAS -> R.string.track_texas
                BRAZIL -> R.string.track_brazil
                AUSTRIA -> R.string.track_austria
                SOCHI -> R.string.track_sochi
                MEXICO -> R.string.track_mexico
                BAKU -> R.string.track_baku
                SAKHIR_SHORT -> R.string.track_sakhir_short
                SILVERSTONE_SHORT -> R.string.track_silverstone_short
                TEXAS_SHORT -> R.string.track_texas_short
                SUZUKA_SHORT -> R.string.track_suzuka_short
                else -> R.string.unknown
            }

    }

    object ERA {
        const val CLASSIC_2017 = 0
        const val CLASSIC_2018 = 1
        const val MODERN_2017 = 10
        const val MODERN_2018 = 11

        fun getEraName(era: Int?) = when(era){
            CLASSIC_2017, CLASSIC_2018 -> R.string.era_classic
            MODERN_2017, MODERN_2018 -> R.string.era_modern
            else -> R.string.unknown
        }
    }

    object TEAMS {
        const val MERCEDES = 0
        const val FERRARI = 1
        const val REDBULL = 2
        const val WILLIAMS = 3
        const val FORCE_INDIA = 4
        const val RENAULT = 5
        const val TORO_ROSSO = 6
        const val HAAS = 7
        const val MCLAREN = 8
        const val SAUBER = 9
        const val RACING_POINT = 10
        const val ALFA_ROMEO = 11
        const val MCLAREN_1988 = 30
        const val MCLAREN_1991 = 31
        const val WILLIAMS_1992 = 32
        const val FERRARI_1995 = 33
        const val WILLIAMS_1996 = 34
        const val MCLAREN_1998 = 35
        const val FERRARI_2002 = 36
        const val FERRARI_2004 = 37
        const val RENAULT_2006 = 38
        const val FERRARI_2007 = 39
        const val MCLAREN_2008 = 40
        const val REDBULL_2010 = 41
        const val FERRARI_1976 = 42
        const val MCLAREN_1976 = 43
        const val LOTUS_1972 = 44
        const val FERRARI_1979 = 45
        const val MCLAREN_1982 = 46
        const val WILLIAMS_2003 = 47
        const val BRAWN_2009 = 48
        const val LOTUS_1978 = 49

        fun getStandardName2018(team: UByte?) = when(team?.toInt()){
            0 -> Standard.TEAMS.MERCEDES
            1 -> Standard.TEAMS.FERRARI
            2 -> Standard.TEAMS.REDBULL
            3 -> Standard.TEAMS.WILLIAMS
            4 -> Standard.TEAMS.FORCE_INDIA
            5 -> Standard.TEAMS.RENAULT
            6 -> Standard.TEAMS.TORO_ROSSO
            7 -> Standard.TEAMS.HAAS
            8 -> Standard.TEAMS.MCLAREN
            9 -> Standard.TEAMS.SAUBER
            10 -> Standard.TEAMS.MCLAREN_1988
            11 -> Standard.TEAMS.MCLAREN_1991
            12 -> Standard.TEAMS.WILLIAMS_1992
            13 -> Standard.TEAMS.FERRARI_1995
            14 -> Standard.TEAMS.WILLIAMS_1996
            15 -> Standard.TEAMS.MCLAREN_1998
            16 -> Standard.TEAMS.FERRARI_2002
            17 -> Standard.TEAMS.FERRARI_2004
            18 -> Standard.TEAMS.RENAULT_2006
            19 -> Standard.TEAMS.FERRARI_2007
            20 -> Standard.TEAMS.MCLAREN_2008
            21 -> Standard.TEAMS.REDBULL_2010
            22 -> Standard.TEAMS.FERRARI_1976
            34 -> Standard.TEAMS.MCLAREN_1976
            35 -> Standard.TEAMS.LOTUS_1972
            36 -> Standard.TEAMS.FERRARI_1979
            37 -> Standard.TEAMS.MCLAREN_1982
            38 -> Standard.TEAMS.WILLIAMS_2003
            39 -> Standard.TEAMS.BRAWN_2009
            40 -> Standard.TEAMS.LOTUS_1978
            else -> Standard.UNKNOWN
        }


        fun getStandardTeamName2017(teamId: Byte?, era: Byte?): Int {
            era?.let {
                return when (it.toInt()) {
                    ERA.MODERN_2017 -> {
                        when (teamId?.toInt()) {
                            0 -> Standard.TEAMS.REDBULL
                            1 -> Standard.TEAMS.FERRARI
                            2 -> Standard.TEAMS.MCLAREN
                            3 -> Standard.TEAMS.RENAULT
                            4 -> Standard.TEAMS.MERCEDES
                            5 -> Standard.TEAMS.SAUBER
                            6 -> Standard.TEAMS.FORCE_INDIA
                            7 -> Standard.TEAMS.WILLIAMS
                            8 -> Standard.TEAMS.TORO_ROSSO
                            11 -> Standard.TEAMS.HAAS
                            else -> Standard.UNKNOWN
                        }
                    }
                    ERA.CLASSIC_2017 -> {
                        when (teamId?.toInt()) {
                            0 -> Standard.TEAMS.WILLIAMS_1992
                            1 -> Standard.TEAMS.MCLAREN_1988
                            2 -> Standard.TEAMS.MCLAREN_2008
                            3 -> Standard.TEAMS.FERRARI_2004
                            4 -> Standard.TEAMS.FERRARI_1995
                            5 -> Standard.TEAMS.FERRARI_2007
                            6 -> Standard.TEAMS.MCLAREN_1998
                            7 -> Standard.TEAMS.WILLIAMS_1996
                            8 -> Standard.TEAMS.RENAULT_2006
                            10 -> Standard.TEAMS.FERRARI_2002
                            11 -> Standard.TEAMS.REDBULL_2010
                            12 -> Standard.TEAMS.MCLAREN_1991
                            else -> Standard.UNKNOWN
                        }
                    }
                    else -> Standard.UNKNOWN
                }
            }
            return Standard.UNKNOWN
        }

        fun getTeamName(team: Int?): String{
            MyApplication.getContext()?.resources?.let {
                return it.getString(
                    when(team){
                        MERCEDES -> R.string.team_mercedes
                        FERRARI ->R.string.team_ferrari
                        REDBULL ->R.string.team_redbull
                        WILLIAMS ->R.string.team_williams
                        FORCE_INDIA ->R.string.team_force_india
                        RENAULT ->R.string.team_renault
                        TORO_ROSSO ->R.string.team_toro_rosso
                        HAAS ->R.string.team_haas
                        MCLAREN ->R.string.team_mclaren
                        SAUBER ->R.string.team_sauber
                        RACING_POINT ->R.string.team_racing_point
                        ALFA_ROMEO ->R.string.team_alfa_romeo
                        MCLAREN_1988 ->R.string.team_mclaren_1988
                        MCLAREN_1991 ->R.string.team_mclaren_1991
                        WILLIAMS_1992 ->R.string.team_williams_1992
                        FERRARI_1995 -> R.string.team_ferrari_1995
                        WILLIAMS_1996 ->R.string.team_williams_1996
                        MCLAREN_1998 ->R.string.team_mclaren_1998
                        FERRARI_2002 ->R.string.team_ferrari_2002
                        FERRARI_2004 ->R.string.team_ferrari_2004
                        RENAULT_2006 ->R.string.team_renault_2006
                        FERRARI_2007 ->R.string.team_ferrari_2007
                        MCLAREN_2008 -> R.string.team_mclaren_2008
                        REDBULL_2010 ->R.string.team_redbull_2010
                        FERRARI_1976 -> R.string.team_ferrari_1976
                        MCLAREN_1976 -> R.string.team_mclaren_1976
                        LOTUS_1972 ->R.string.team_lotus_1972
                        FERRARI_1979 ->R.string.team_ferrari_1979
                        MCLAREN_1982 -> R.string.team_mclaren_1982
                        WILLIAMS_2003 -> R.string.team_williams_2003
                        BRAWN_2009 -> R.string.team_brawn_2009
                        LOTUS_1978 -> R.string.team_lotus_1978
                        else -> R.string.unknown
                    }
                )
            }
            return UNKNOWN_TEXT
        }
        
        fun getTeamColor(team: Int?): Int {
            team?.let{
                return when(team){
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
            }
            return R.color.teamUnknown
        }
    }
}