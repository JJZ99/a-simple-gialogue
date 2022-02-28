package com.example.abcdialogue.Test


class LiveCloseWindowEvent : BaseMetricsEventV2(EVENT) {

    var commodityId: String? = null
    var anchorId: String? = null
    var commodityType: Long = 0
    var roomId: String? = null
    var sourcePage: String? = null
    var carrierType: String? = null

    override fun buildParams() {
        appendParam(KEY_COMMODITY_ID, commodityId, ParamRule.DEFAULT)
        appendParam(KEY_ANCHOR_ID, anchorId, ParamRule.DEFAULT)
        appendParam(KEY_COMMODITY_TYPE, commodityType.toString(), ParamRule.DEFAULT)
        appendParam(KEY_ROOM_ID, roomId, ParamRule.DEFAULT)
        appendParam(KEY_SOURCE_PAGE, sourcePage, ParamRule.DEFAULT)
        appendParam(KEY_CARRIER_TYPE, carrierType, ParamRule.DEFAULT)
    }

    companion object {
        private const val EVENT = "close_window"
    }
}