package org.liamjd.cantilever.corbel


data class AppState( var mode: Mode =  Mode.UNAUTHENTICATED)

enum class Mode {
    UNAUTHENTICATED,
    VIEWING,
    NEW_ITEM,
    EDITING
}