package pl.humberd.notesapp.application.common

import java.util.*



inline class NullablePatchBox(val value: String?) {

}


fun <T> T.applyPatch(patch: T?): T {
    if (patch === null) {
        return this
    }

    return patch
}

fun <T> T?.applyPatch(patch: Optional<T>?): T? {
    if (patch === null) {
        return this
    }

    return patch.orElse(null)
}
