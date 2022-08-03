package edu.emory.diabetes.education.domain.repository

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun query(): Flow<List<*>>
}