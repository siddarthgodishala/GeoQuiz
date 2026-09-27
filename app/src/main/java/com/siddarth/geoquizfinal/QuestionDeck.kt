package com.siddarth.geoquizfinal

/** Stable IDs keep stored answers attached to the right question. */
data class GeographyQuestion(
    val id: String,
    val topic: String,
    val statement: String,
    val answer: Boolean,
    val explanation: String
)

object QuestionDeck {
    val questions: List<GeographyQuestion> = listOf(
        GeographyQuestion("peru", "South America", "Lima is the capital of Peru.", true,
            "Lima is Peru's capital, on the country's Pacific coast."),
        GeographyQuestion("iceland", "Europe", "Iceland has a land border with Norway.", false,
            "Iceland is an island country in the North Atlantic and has no land borders."),
        GeographyQuestion("africa", "Continents", "The equator crosses Africa.", true,
            "The equator crosses several African countries, including Kenya and Uganda."),
        GeographyQuestion("mongolia", "Asia", "Mongolia has a coastline on the Pacific Ocean.", false,
            "Mongolia is landlocked between Russia and China."),
        GeographyQuestion("alps", "Mountains", "The Alps extend into Switzerland.", true,
            "Switzerland is one of the countries crossed by the Alps."),
        GeographyQuestion("cuba", "Islands", "Cuba is in the Indian Ocean.", false,
            "Cuba is a Caribbean island country, near the Gulf of Mexico and Atlantic Ocean."),
        GeographyQuestion("giza", "Africa", "Giza is in Egypt.", true,
            "Giza is an Egyptian city near Cairo, known for its pyramid complex."),
        GeographyQuestion("berlin", "Europe", "Berlin is the capital of Austria.", false,
            "Berlin is Germany's capital. Austria's capital is Vienna.")
    )
}
