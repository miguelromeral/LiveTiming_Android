package es.miguelromeral.f1.codemasters.livetiming.packets.p2017

import es.miguelromeral.f1.codemasters.livetiming.packets.*

@kotlin.ExperimentalUnsignedTypes
class Packet2017 private constructor(content: ByteArray){

    val time = floatFromPacket(content.sliceArray(0..3))
    val lapTime = floatFromPacket(content.sliceArray(4..7))
    val lapDistance = floatFromPacket(content.sliceArray(8..11))
    val totalDistance = floatFromPacket(content.sliceArray(12..15))
    val x = floatFromPacket(content.sliceArray(16..19))
    val y = floatFromPacket(content.sliceArray(20..23))
    val z = floatFromPacket(content.sliceArray(24 until 28))
    val speed = floatFromPacket(content.sliceArray(28 until 32))
    val xv = floatFromPacket(content.sliceArray(32 until 36))
    val yv = floatFromPacket(content.sliceArray(36 until 40))
    val zv = floatFromPacket(content.sliceArray(40 until 44))
    val xr = floatFromPacket(content.sliceArray(44 until 48))
    val yr = floatFromPacket(content.sliceArray(48 until 52))
    val zr = floatFromPacket(content.sliceArray(52 until 56))
    val xd = floatFromPacket(content.sliceArray(56 until 60))
    val yd = floatFromPacket(content.sliceArray(60 until 64))
    val zd = floatFromPacket(content.sliceArray(64 until 68))
    val susp_pos = createFloatArray(content, 68)
    val susp_vel = createFloatArray(content, 84)
    val wheel_speed = createFloatArray(content, 100)
    val throttle = floatFromPacket(content.sliceArray(116 until 120))
    val steer = floatFromPacket(content.sliceArray(120 until 124))
    val brake = floatFromPacket(content.sliceArray(124 until 128))
    val clutch = floatFromPacket(content.sliceArray(128 until 132))
    val gear = floatFromPacket(content.sliceArray(132 until 136))
    val gforce_lat = floatFromPacket(content.sliceArray(136 until 140))
    val gforce_lon = floatFromPacket(content.sliceArray(144 until 148))
    val lap = floatFromPacket(content.sliceArray(148 until 152))
    val engineRate = floatFromPacket(content.sliceArray(152 until 156))
    val sli_pro_native_support = floatFromPacket(content.sliceArray(156 until 160))
    val car_position = floatFromPacket(content.sliceArray(164 until 168))
    val kers_level = floatFromPacket(content.sliceArray(168 until 172))
    val kers_max_level = floatFromPacket(content.sliceArray(172 until 176))
    val drs = floatFromPacket(content.sliceArray(176 until 180))
    val traction_control = floatFromPacket(content.sliceArray(180 until 184))
    val anti_lock_brakes = floatFromPacket(content.sliceArray(184 until 188))
    val fuel_in_tank = floatFromPacket(content.sliceArray(188 until 192))
    val fuel_capacity = floatFromPacket(content.sliceArray(192 until 196))
    val in_pits = floatFromPacket(content.sliceArray(196 until 200))
    val sector = floatFromPacket(content.sliceArray(200 until 204))
    val sector1_time = floatFromPacket(content.sliceArray(204 until 208))
    val sector2_time = floatFromPacket(content.sliceArray(208 until 212))
    val brakes_temp = createFloatArray(content, 212)
    val tyres_pressure = createFloatArray(content, 228)
    val team_info = floatFromPacket(content.sliceArray(244 until 248))
    val total_laps = floatFromPacket(content.sliceArray(248 until 252))
    val track_size = floatFromPacket(content.sliceArray(252 until 256))
    val last_lap_time = floatFromPacket(content.sliceArray(256 until 260))
    val max_rpm = floatFromPacket(content.sliceArray(260 until 264))
    val idle_rpm = floatFromPacket(content.sliceArray(264 until 268))
    val max_gears = floatFromPacket(content.sliceArray(268 until 272))
    val sessionType = floatFromPacket(content.sliceArray(272 until 276))
    val drsAllowed = floatFromPacket(content.sliceArray(276 until 280))
    val track_number = floatFromPacket(content.sliceArray(280 until 284))
    val vehicleFIAFlags = floatFromPacket(content.sliceArray(284 until 288))
    val era = floatFromPacket(content.sliceArray(288 until 292))
    val engine_temperature = floatFromPacket(content.sliceArray(292 until 296))
    val gforce_vert = floatFromPacket(content.sliceArray(296 until 300))
    val ang_vel_x = floatFromPacket(content.sliceArray(300 until 304))
    val ang_vel_y = floatFromPacket(content.sliceArray(304 until 308))
    val ang_vel_z = floatFromPacket(content.sliceArray(308 until 312))
    val tyres_temperature = createByteArray(content, 312)
    val tyres_wear = createByteArray(content, 316)
    val tyre_compound = content[320]
    val front_brake_bias = content[321]
    val fuel_mix = content[322]
    val currentLapInvalid = content[323]
    val tyres_damage = createByteArray(content, 324)
    val front_left_wing_damage = content[328]
    val front_right_wing_damage = content[329]
    val rear_wing_damage = content[330]
    val engine_damage = content[331]
    val gear_box_damage = content[332]
    val exhaust_damage = content[333]
    val pit_limiter_status = content[334]
    val pit_speed_limit = content[335]
    val session_time_left = floatFromPacket(content.sliceArray(336 until 340))
    val rev_lights_percent = content[340]
    val is_espectating = content[341]
    val spectator_car_index = content[342]

    val num_cars = content[343]
    val player_car_index = content[344]
    val car_data = createCarDataArray(content, 345)

    val yaw = floatFromPacket(content.sliceArray(1245 until 1249))
    val pitch = floatFromPacket(content.sliceArray(1249 until 1253))
    val roll = floatFromPacket(content.sliceArray(1253 until 1257))
    val x_local_velocity = floatFromPacket(content.sliceArray(1257 until 1261))
    val y_local_velocity = floatFromPacket(content.sliceArray(1261 until 1265))
    val z_local_velocity = floatFromPacket(content.sliceArray(1265 until 1269))
    val susp_acceleration = createFloatArray(content, 1269)
    //val ang_acc_x = floatFromPacket(content.sliceArray(1285 until 1289))
    //val ang_acc_y
    //val ang_acc_z


    private fun createCarDataArray(content: ByteArray, begining: Int, iteration: Int = 0): List<CarUDPData>{
        if(iteration >= MAX_CAR_NUMBER)
            return emptyList()

        val start = begining + (CarUDPData.SIZE * iteration)
        val end = begining + CarUDPData.SIZE

        return listOf(CarUDPData(content.sliceArray(start until end))) + createCarDataArray(content, begining, iteration + 1)
    }

    companion object {
        const val SIZE = 1289
        const val MAX_CAR_NUMBER = 20

        fun create(content: ByteArray): Packet2017 {
            return Packet2017(content.sliceArray(0 until SIZE))
        }


        fun getTrack(trackId: Byte) = when(trackId.toInt()){
            0 -> "Melbourne"
            1 -> "Sepang"
            2 -> "Shanghai"
            3 -> "Sakhir (Bahrain)"
            4 -> "Catalunya"
            5 -> "Monaco"
            6 -> "Montreal"
            7 -> "Silverstone"
            8 -> "Hockenheim"
            9 -> "Hungaroring"
            10 -> "Spa"
            11 -> "Monza"
            12 -> "Singapore"
            13 -> "Suzuka"
            14 -> "Abu Dhabi"
            15 -> "Texas"
            16 -> "Brazil"
            17 -> "Austria"
            18 -> "Sochi"
            19 -> "Mexico"
            20 -> "Baku (Azerbaijan)"
            21 -> "Sakhir Short"
            22 -> "Silverstone Short"
            23 -> "Texas Short"
            24 -> "Suzuka Short"
            else -> "Unknown"
        }
    }
}