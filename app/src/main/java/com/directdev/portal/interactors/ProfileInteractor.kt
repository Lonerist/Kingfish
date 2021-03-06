package com.directdev.portal.interactors

import com.directdev.portal.network.NetworkHelper
import com.directdev.portal.repositories.ProfileRepository
import io.reactivex.Single
import org.json.JSONObject
import javax.inject.Inject

/**-------------------------------------------------------------------------------------------------
 * Created by chris on 8/26/17.
 *------------------------------------------------------------------------------------------------*/
class ProfileInteractor @Inject constructor(
        private val profileRepo: ProfileRepository,
        private val bimayApi: NetworkHelper
) {
    fun sync(cookie: String): Single<String> {
        return bimayApi.getUserProfile(cookie).map {
            val profileObject = JSONObject(it.string())
                    .getJSONArray("Profile")
                    .getJSONObject(0)
            profileRepo.save(profileObject)
            cookie
        }
    }
}