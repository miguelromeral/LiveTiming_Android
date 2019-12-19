package classes.toplayer

import android.content.Context
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R

class Standard {

    companion object{
        const val UNKNOWN = -1
        const val UNKNOWN_TEXT = "TBD"
    }

    object AIMode {
        const val HUMAN = 0
        const val AI = 1
    }

    object Drivers {
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