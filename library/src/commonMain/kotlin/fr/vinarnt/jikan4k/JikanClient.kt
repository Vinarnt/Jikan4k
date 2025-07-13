package fr.vinarnt.jikan4k

import fr.vinarnt.jikan4k.apis.AnimeApi
import fr.vinarnt.jikan4k.apis.CharactersApi
import fr.vinarnt.jikan4k.apis.ClubsApi
import fr.vinarnt.jikan4k.apis.GenresApi
import fr.vinarnt.jikan4k.apis.MagazinesApi
import fr.vinarnt.jikan4k.apis.MangaApi
import fr.vinarnt.jikan4k.apis.PeopleApi
import fr.vinarnt.jikan4k.apis.ProducersApi
import fr.vinarnt.jikan4k.apis.RandomApi
import fr.vinarnt.jikan4k.apis.RecommendationsApi
import fr.vinarnt.jikan4k.apis.ReviewsApi
import fr.vinarnt.jikan4k.apis.SchedulesApi
import fr.vinarnt.jikan4k.apis.SeasonsApi
import fr.vinarnt.jikan4k.apis.TopApi
import fr.vinarnt.jikan4k.apis.UsersApi
import fr.vinarnt.jikan4k.apis.WatchApi
import fr.vinarnt.jikan4k.infrastructure.ApiClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import kotlinx.serialization.json.JsonBuilder

class JikanClient(
    httpClientEngine: HttpClientEngine? = null,
    httpClientConfig: ((HttpClientConfig<*>) -> Unit)? = null,
    jsonBuilder: JsonBuilder.() -> Unit = {},
) {
    private val apiClient =
        ApiClient(
            httpClientEngine = httpClientEngine,
            httpClientConfig = httpClientConfig,
            jsonBuilder = jsonBuilder,
        )

    val animes by lazy { AnimeApi(apiClient) }
    val characters by lazy { CharactersApi(apiClient) }
    val clubs by lazy { ClubsApi(apiClient) }
    val genres by lazy { GenresApi(apiClient) }
    val magazines by lazy { MagazinesApi(apiClient) }
    val mangas by lazy { MangaApi(apiClient) }
    val peoples by lazy { PeopleApi(apiClient) }
    val producers by lazy { ProducersApi(apiClient) }
    val random by lazy { RandomApi(apiClient) }
    val recommendations by lazy { RecommendationsApi(apiClient) }
    val reviews by lazy { ReviewsApi(apiClient) }
    val schedules by lazy { SchedulesApi(apiClient) }
    val seasons by lazy { SeasonsApi(apiClient) }
    val tops by lazy { TopApi(apiClient) }
    val users by lazy { UsersApi(apiClient) }
    val watch by lazy { WatchApi(apiClient) }
}
