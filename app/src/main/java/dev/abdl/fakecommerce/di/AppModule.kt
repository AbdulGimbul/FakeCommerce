package dev.abdl.fakecommerce.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.abdl.fakecommerce.BuildConfig
import dev.abdl.fakecommerce.features.auth.data.AuthRepository
import dev.abdl.fakecommerce.features.auth.data.AuthRepositoryImpl
import dev.abdl.fakecommerce.network.FakeCommerceHttpClientBuilder
import dev.abdl.fakecommerce.network.RequestHandler
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Singleton
//    @Provides
//    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
//            produceFile = { context.preferencesDataStoreFile(SessionHandler.DATA) }
//        )
//    }

    @Provides
    fun provideHttpClient(): HttpClient =
        FakeCommerceHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(BuildConfig.FAKE_STORE_HOST)
//            .port(1301)
            .build()

    @Provides
    fun provideRequestHandler(client: HttpClient): RequestHandler = RequestHandler(client)

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

//    @Provides
//    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl
}