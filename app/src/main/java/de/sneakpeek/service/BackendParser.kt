package de.sneakpeek.service

import de.sneakpeek.data.ActualMovie
import de.sneakpeek.data.MoviePrediction
import de.sneakpeek.data.PredictedStudios
import de.sneakpeek.data.Prediction

class BackendParser {
    fun parseActualMovies(response: String): List<ActualMovie> {

        return response
                .split(System.getProperty("line.separator"))
                .map { it.trim() }
                .filter { !it.isEmpty() }
                .map {it.split("###") } // Splits Date
                .map { ActualMovie(week = it[0], title = it[1]) }
                .toList()
    }

    fun parseStudios(response: String): List<PredictedStudios> {

        return response
                .split(System.getProperty("line.separator"))
                .map { it.trim() }
                .filter { !it.isEmpty() }
                .map { it.split("###") }
                .map { PredictedStudios(it[0], it.subList(1, it.size).map { it.substring(1) }) }
                .toList()
    }

    fun parsePrediction(response: String): List<Prediction> {

        val predictions = ArrayList<Prediction>(30)

        val allPredictions = response
                .split(System.getProperty("line.separator"))
                .map { it.trim() }
                .filter { !it.isEmpty() }

        for (prediction in allPredictions) {

            val movieWithStudiosSplit = prediction.split("###")

            val positionOfSpace = movieWithStudiosSplit[0].indexOf(" ")
            val week = movieWithStudiosSplit[0]
                    .substring(positionOfSpace + 1)

            val movies = movieWithStudiosSplit
                    .subList(1, movieWithStudiosSplit.size)
                    .map { it.split("-", limit = 2) }
                    .map { MoviePrediction(it[1], it[0].toInt()) }

            predictions.add(Prediction(week, movies = movies))
        }

        return predictions
    }
}
