package com.example.abcdialogue

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val log: JSONObject?= null
        var ss = log?.toString()
        println(log?.toString())
        assertEquals(4, 2 + 2)
        println("Dasdas")
    }
    @Test
    fun testJson(){
        var jsonString = "{" +
                "\"default_select\": false," +
                "\"hide_spec\": false," +
                "\"name\": \"尺寸-欧码\"," +
                "\"height\": {\"name\": \"尺寸-欧码\"}," +
                "\"size_component\": {" +
                "\"icon\": \"https://lf9-cm.ecombdstatic.com/obj/eden-cn/feoeh7nupuboz/d8813df8bce666e8243c544e0c4def25.png_tplv-5mmsx3fupr-image.webp\"," +
                "\"images\": [" +
                "{" +
                "\"height\": 612," +
                "\"uri\": \"ecom-shop-material/XPBzeueS_m_31cc5827c114a6d3f5ea7cfa28ec9ea2_sx_75926_www1125-612\"," +
                "\"url_list\": [" +
                "\"https://p3-item.ecombdimg.com/img/ecom-shop-material/XPBzeueS_m_31cc5827c114a6d3f5ea7cfa28ec9ea2_sx_75926_www1125-612~tplv-5mmsx3fupr-image.webp\"," +
                "\"https://p6-item.ecombdimg.com/img/ecom-shop-material/XPBzeueS_m_31cc5827c114a6d3f5ea7cfa28ec9ea2_sx_75926_www1125-612~tplv-5mmsx3fupr-image.webp\"" +
                "]," +
                "\"width\": 1125" +
                "}" +
                "]," +
                "\"text\": \"尺码表\"," +
                "\"width\": 16" +
                "}," +
                "\"spec_items\": [" +
                "{" +
                "\"default_select\": false," +
                "\"icon\": null," +
                "\"id\": \"1740582693260320\"," +
                "\"name\": \"蓝色M码110斤左右\"" +
                "}," +
                "{" +
                "\"default_select\": false," +
                "\"icon\": null," +
                "\"id\": \"1740582693260336\"," +
                "\"name\": \"蓝色L码130斤左右\"" +
                "}," +
                "{" +
                "\"default_select\": false," +
                "\"icon\": null," +
                "\"id\": \"1740582693261312\"," +
                "\"name\": \"蓝色xL码150斤左右\"" +
                "}," +
                "{" +
                "\"default_select\": false," +
                "\"icon\": null," +
                "\"id\": \"1740582693261328\"," +
                "\"name\": \"烟灰色M码110斤左右\"" +
                "}," +
                "{" +
                "\"default_select\": false," +
                "\"icon\": null," +
                "\"id\": \"1740582693261344\"," +
                "\"name\": \"烟灰色L码130斤左右\"" +
                "}," +
                "{" +
                "\"default_select\": false," +
                "\"icon\": null," +
                "\"id\": \"1740582693261360\"," +
                "\"name\": \"烟灰色XL码150斤左右\"" +
                "}" +
                "]," +
                "\"spec_type\": 0" +
                "}"
        var jsonString2 = "{\"room_id\":\"7161608387355003688\",\"media_app_id\":\"203317\",\"anchor_id\":\"_000E1LYd1F2ktFTiIBjLclcKLOrArzS2Bgo\",\"enter_method\":\"live_cover\",\"action_type\":\"draw\",\"follow_status\":\"0\",\"sdk_version\":\"2100\",\"_param_live_platform\":\"live\",\"enter_from_merge\":\"live_merge\",\"request_id\":\"20221103142025010208105166226C60E2\",\"enter_from\":\"live\",\"category_name\":\"live_merge_temai_live_cover\",\"carrier_type\":\"live_list_card\",\"live_list_channel\":\"live_shopping_cart\",\"ecom_scene_id\":\"1001\"}"
        val json = JSONObject(jsonString)

        println(json.optString("name"))
        println(json.optJSONObject("height"))
        val optJSONArray = json.optJSONArray("spec_items")


    }

    @Test
    fun rxjava() {

        println("RXJAVA" + "Observer.onNext:"+Thread.currentThread().name.toString())
        Observable.create(ObservableOnSubscribe<String> { emitter -> //事件产生的地方，比如保存文件、请求网络等
            if (10 % 2 == 1) {
                emitter.onNext("dasd")
            } else {
                emitter.onNext("dadas")
            }
            println("RXJAVA" + "Emitter.onNext:" + Thread.currentThread().name.toString())
        }).subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())

            .map {
                println("RXJAVA 6" + "Emitter.onNext:" + Thread.currentThread().name.toString())

                6
            }
            .observeOn(Schedulers.newThread())

            .subscribe(object : Observer<Int> {

                //这个方法在onNext之前被调用
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Int) {
                    println("RXJAVA" + "Observer.onNext:" + Thread.currentThread().name.toString())

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                //这个方法在onNext之后被调用
                override fun onComplete() {

                }
            })
        println("RXJAVA" + "Observer.onNext:"+Thread.currentThread().name.toString())
        Thread.sleep(3000)
    }
}

