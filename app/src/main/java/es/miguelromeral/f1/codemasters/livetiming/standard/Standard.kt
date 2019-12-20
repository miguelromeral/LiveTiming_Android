package classes.toplayer

import android.content.Context
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.packets.p2017.Packet2017

class Standard {

    companion object{
        const val UNKNOWN = -1
        const val UNKNOWN_TEXT = "TBD"
    }

    object AI {
        const val HUMAN = 0
        const val AI = 1
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
        const val MAGNUSSEN = 20
        const val HULKENBERG = 27
        const val HARTLEY = 28
        const val OCON = 31
        const val VERSTAPPEN = 33
        const val SIROTKIN = 35
        const val HAMILTON = 44
        const val SAINZ = 55
        const val BOTTAS = 77
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
                        RICCIARDO -> R.string.driver_Daniel_Ricciardo
                        ALONSO -> R.string.driver_Fernando_Alonso
                        RAIKKONEN -> R.string.driver_Kimi_Raikkonen
                        HAMILTON -> R.string.driver_Lewis_Hamilton
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


        fun getStandardName2017(teamId: Byte?, era: Byte?): Int {
            era?.let {
                return when (it.toInt()) {
                    Packet2017.ERA_MODERN -> {
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
                    Packet2017.ERA_CLASSIC -> {
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