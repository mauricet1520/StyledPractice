package com.coolreecedev.styledpractice.data.appointment

data class StaffTimeSlots(val staff: Staff,
                          val slots: List<String>,
                          var sevenToNineTimeSlotsAvailable: Boolean = false,
                          var eightToTenTimeSlotsAvailable: Boolean = false,
                          var nineToElevenTimeSlotsAvailable: Boolean= false,
                          var tenToTwelveTimeSlotsAvailable: Boolean= false,
                          var elevenToThirteenTimeSlotsAvailable: Boolean= false,
                          var twelveToFourteenTimeSlotsAvailable: Boolean= false,
                          var thirteenToFifteenTimeSlotsAvailable: Boolean= false,
                          var fourteenToSixteenTimeSlotsAvailable: Boolean= false)