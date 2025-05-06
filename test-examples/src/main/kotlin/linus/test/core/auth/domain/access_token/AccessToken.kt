package linus.test.core.auth.domain.access_token

data class AccessToken(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }

    override fun toString(): String {
        return this.value
    }
}
