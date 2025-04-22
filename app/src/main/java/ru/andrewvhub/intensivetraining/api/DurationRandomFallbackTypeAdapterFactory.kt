package ru.andrewvhub.intensivetraining.api

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import ru.andrewvhub.intensivetraining.api.models.response.TrainingResponse.Companion.DURATION
import java.io.IOException
import kotlin.random.Random

/**
 * Это тупо бред, сервер возвращает время в строке, то в Int
 */
class DurationRandomFallbackTypeAdapterFactory(
    private val min: Int = 1,
    private val max: Int = 60
) : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType
        if (rawType == String::class.java || rawType.isPrimitive || rawType.isArray) {
            return null
        }
        val delegate: TypeAdapter<T> = gson.getDelegateAdapter(this, type)
        val elementAdapter: TypeAdapter<JsonElement> = gson.getAdapter(JsonElement::class.java)

        return object : TypeAdapter<T>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: T) = delegate.write(out, value)

            @Throws(IOException::class)
            override fun read(reader: JsonReader): T {
                val jsonElement = elementAdapter.read(reader)
                if (jsonElement.isJsonObject) {
                    val obj = jsonElement.asJsonObject
                    if (obj.has(DURATION)) {
                        val durElem = obj.get(DURATION)
                        if (!durElem.isJsonPrimitive || !durElem.asJsonPrimitive.isNumber) {
                            obj.addProperty(DURATION, Random.nextInt(min, max + 1))
                        }
                    }
                }
                return delegate.fromJsonTree(jsonElement)
            }
        }
    }
}