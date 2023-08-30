package org.liamjd.cantilever.corbel.viewModels


/**
 * The application has a number of fixed states; the application can only be in one state at a time
 */
enum class Mode {
    UNAUTHENTICATED,
    BUSY_AWAITING_AUTH,
    BUSY,
    VIEWING,
    NEW_ITEM,
    EDITING
}