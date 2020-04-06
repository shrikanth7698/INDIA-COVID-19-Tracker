package me.shrikanthravi.india.covid19app.ui.helpline

data class Helpline (
    var state: String? = null,
    var phoneNumbers: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Helpline

        if (state != other.state) return false
        if (!phoneNumbers.contentEquals(other.phoneNumbers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = state?.hashCode() ?: 0
        result = 31 * result + phoneNumbers.contentHashCode()
        return result
    }
}