package com.example.data.local.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class AlbumRealmModel : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var artist: String = ""
    var thumbnailUrl: String = ""
    var genre: String? = null
    var releaseDate: String? = null
    var copyright: String? = null
    var url: String? = null
}