package com.honoursigbeku.studyhubapp.data.datasource.remote


import com.honoursigbeku.studyhubapp.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


object RemoteStorageDataSourceProvider {

    val supabase = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL, supabaseKey = BuildConfig.SUPABASE_KEY
    ) {
        install(Postgrest)
    }
    val getInstance = SupabaseRemoteStorageDataSourceImpl(client = supabase)
}