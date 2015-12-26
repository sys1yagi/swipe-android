package com.sys1yagi.swipe.core.tool

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sys1yagi.swipe.core.entity.index.Index
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument
import com.sys1yagi.swipe.core.tool.json.JsonConverter

import org.hjson.JsonValue

class SwipeEntityDecoder {

    //TODO remove it
    private fun hjson(jsonString: String): String {
        return JsonValue.readHjson(jsonString).asObject().toString()
    }

    fun decodeToIndex(jsonConverter: JsonConverter, jsonString: String): Index {

        val json = JsonParser().parse(hjson(jsonString)).asJsonObject
        if (!json.has(KEY_TYPE)) {
            throw IllegalArgumentException("Invalid index swipe file. data:" + jsonString)
        }
        if (TYPE_INDEX != json.get(KEY_TYPE).asString) {
            throw IllegalArgumentException("Invalid index swipe file. data:" + jsonString)
        }
        return jsonConverter.fromJson(json, Index::class.java)
    }

    fun decodeToSwipe(jsonConverter: JsonConverter, jsonString: String): SwipeDocument {
        val json = JsonParser().parse(hjson(jsonString)).asJsonObject
        if (json.has(KEY_TYPE) && TYPE_INDEX == json.get(KEY_TYPE).asString) {
            throw IllegalArgumentException("Invalid contents swipe file. data:" + jsonString)
        }
        return jsonConverter.fromJson(json, SwipeDocument::class.java)
    }

    companion object {

        private val KEY_TYPE = "type"

        private val TYPE_INDEX = "net.swipe.list"
    }
}
