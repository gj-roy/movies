package org.michaelbel.movies.repository.ktx

import java.util.Locale
import org.michaelbel.movies.network.GRAVATAR_URL
import org.michaelbel.movies.network.formatProfileImage
import org.michaelbel.movies.network.model.Account
import org.michaelbel.movies.persistence.database.entity.AccountDb

internal val Account.mapToAccountDb: AccountDb
    get() = AccountDb(
        accountId = id,
        avatarUrl = when {
            avatar.tmdbAvatar != null -> avatar.tmdbAvatar?.avatarPath.orEmpty().formatProfileImage
            avatar.grAvatar != null -> String.format(Locale.US, GRAVATAR_URL, avatar.grAvatar?.hash)
            else -> ""
        },
        language = lang,
        country = country,
        name = name,
        adult = includeAdult,
        username = username
    )