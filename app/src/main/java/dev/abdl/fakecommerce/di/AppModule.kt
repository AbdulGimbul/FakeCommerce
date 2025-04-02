package dev.abdl.fakecommerce.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.abdl.fakecommerce.BuildConfig
import dev.abdl.fakecommerce.features.auth.data.AuthRepository
import dev.abdl.fakecommerce.features.auth.data.AuthRepositoryImpl
import dev.abdl.fakecommerce.features.home.data.ProductRepository
import dev.abdl.fakecommerce.features.home.data.ProductRepositoryImpl
import dev.abdl.fakecommerce.network.FakeCommerceHttpClientBuilder
import dev.abdl.fakecommerce.network.RequestHandler
import dev.abdl.fakecommerce.storage.SessionHandler
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile(SessionHandler.DATA) }
        )
    }

    @Provides
    fun provideHttpClient(sessionHandler: SessionHandler): HttpClient =
        FakeCommerceHttpClientBuilder(sessionHandler)
            .protocol(URLProtocol.HTTPS)
            .host(BuildConfig.FAKE_STORE_HOST)
//            .port(1301)
            .build()

    @Provides
    fun provideRequestHandler(client: HttpClient): RequestHandler = RequestHandler(client)

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideProductRepository(impl: ProductRepositoryImpl): ProductRepository = impl

//    @Provides
//    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl
}