package com.example.abcdialogue.Test

import android.text.TextUtils
import android.util.Log
import org.json.JSONObject
import java.util.HashMap


abstract class BaseMetricsEventV2(private val event: String) : MetricsEvent {
    private val params = HashMap<String, String>()

    override fun post() {
        appendParam(KEY_DATA_TYPE, VALUE_DATA_TYPE, ParamRule.DEFAULT) // all e commerce mobs will have this field
        appendParam(KEY_EVENT_ORIGIN_FEATURE, VALUE_TEMAI, ParamRule.DEFAULT)
        buildParams()
        params.putAll(extraParams)
        println("BaseMetricsEventV2.post")
        println(params.toString())
    }

    fun getParams(): Map<String, String> = mutableMapOf<String, String>().also {
        appendParam(KEY_DATA_TYPE, VALUE_DATA_TYPE, ParamRule.DEFAULT) // all e commerce mobs will have this field
        appendParam(KEY_EVENT_ORIGIN_FEATURE, VALUE_TEMAI, ParamRule.DEFAULT)
        buildParams()
        params.putAll(extraParams)
        it.putAll(params)
    }

    /**
     * 拥有比params更高的优先级
     */
    private val extraParams = HashMap<String, String>()

    /**
     * 一般用于append一些场景下多种event拥有共同的字段 ，如搜索、发现、转发等。
     * 应该配合使用 ExtraMetricsParam
     *
     * @see ExtraMetricsParam
     */
    fun appendExtraParams(extraParams: Map<String, String>): BaseMetricsEventV2 {
        this.extraParams.putAll(extraParams)
        return this
    }

    fun appendBaseParams(baseParams: Map<String, String>): BaseMetricsEventV2 {
        params.putAll(baseParams)
        return this
    }

    protected abstract fun buildParams()

    fun appendParam(key: String, value: String?, rule: ParamRule) {
        value?.let {
            params.put(key, rule.normalize(value))
        }
    }

    fun appendStagingFlagParam(appendStaging: Boolean): BaseMetricsEventV2 {
        if (appendStaging) {
            params[KEY_STAGING_FLAG] = "1"
        }
        return this
    }

    /**
     * 埋点参数校验规则.
     */
    interface ParamRule {

        /**
         * 校验参数的合法性，并返回合规的值.
         */
        fun normalize(param: String): String

        companion object {
            /**
             * 默认规则，对于空参数，使用空字符串替代.
             */
            val DEFAULT: ParamRule = object : ParamRule {
                override fun normalize(param: String): String {
                    return if (param.isNullOrBlank() || "null" == param) {
                        ""
                    } else param
                }
            }
            /**
             * Id 参数校验规则，对于空参数以及0均返回空字符串.
             */
            val ID: ParamRule = object : ParamRule {
                override fun normalize(param: String): String {
                    return if (TextUtils.isEmpty(param) || "null" == param || "0" == param) {
                        ""
                    } else param
                }
            }
        }
    }


    companion object {

    }
}

