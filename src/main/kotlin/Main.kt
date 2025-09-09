import org.json.JSONObject
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    // Создаём HTTP-клиент
    val client = OkHttpClient()

    // Заголовок таблицы
    val header = listOf("City", "Min Temp", "Max Temp","Humidity", "Wind Speed", "Wind Dir")
    println(String.format("%-10s | %-10s | %-10s | %-10s | %-10s | %-10s",
        header[0], header[1], header[2], header[3], header[4], header[5]))

    // API ключ
    val apiKey = "ff5924da6a574f5a81a183300250709"
    val cities = listOf("Chisinau", "Madrid", "Kyiv", "Amsterdam")

    // Обходим список городов
    for (city in cities) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$city&days=1"
        val request = Request.Builder().url(url).build()

        // Выполняем запрос
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val body = response.body?.string() ?: ""
                val json = JSONObject(body)

                // Достаём прогноз на день
                val forecast = json.getJSONObject("forecast")
                    .getJSONArray("forecastday")
                    .getJSONObject(0)
                    .getJSONObject("day")

                // Извлекаем нужные данные
                val minTemp = forecast.getDouble("mintemp_c")
                val maxTemp = forecast.getDouble("maxtemp_c")
                val humidity = forecast.getDouble("avghumidity")
                val windSpeed = forecast.getDouble("maxwind_kph")
                val windDir = json.getJSONObject("current").getString("wind_dir")

                // Форматированный вывод строки таблицы
                println(String.format("%-10s | %-10.1f | %-10.1f | %-10.1f | %-10.1f | %-10s",
                    city, minTemp, maxTemp, humidity, windSpeed, windDir))
            }
        }
    }
}
