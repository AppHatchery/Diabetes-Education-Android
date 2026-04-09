package edu.emory.diabetes.education.presentation.fragments.newResources.screens.course

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.emory.diabetes.education.presentation.fragments.newResources.domain.CourseProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CourseUiState(
    val course: Course,
    val currentChapterIndex: Int = 0,
    val currentPageIndex: Int = 0,
    val scrollProgress: Int = 0,
    val isLoading: Boolean = true
) {
    val currentChapter: Chapter
        get() = course.chapters[currentChapterIndex]

    val currentPageUrl: String
        get() = currentChapter.pages[currentPageIndex].pageUrl

    val currentPageTitle: String
        get() = currentChapter.pages[currentPageIndex].title

    val currentPageFullUrl: String
        get() = course.getPageUrl(currentPageUrl)

    val isLastPageInChapter: Boolean
        get() = currentPageIndex >= currentChapter.pages.size - 1

    val isLastChapter: Boolean
        get() = currentChapterIndex >= course.chapters.size - 1

    val totalPagesInChapter: Int
        get() = currentChapter.pages.size

    val totalChapters: Int
        get() = course.chapters.size

    val completedChapterCount: Int
        get() = course.chapters.count { it.isCompleted }

    val pageLabel: String
        get() = if (totalPagesInChapter > 1) {
            "Page ${currentPageIndex + 1} of $totalPagesInChapter"
        } else ""
}

class CourseViewModel(
    course: Course,
    private val progressRepository: CourseProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseUiState(course = course))
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    init {
        loadSavedProgress()
    }

    private fun loadSavedProgress() {
        viewModelScope.launch {
            progressRepository.getCompletedChapterIds(
                courseId = _uiState.value.course.id
            ).collect { completedIds ->
                _uiState.update { state ->
                    val updatedChapters = state.course.chapters.map { chapter ->
                        chapter.copy(isCompleted = chapter.id in completedIds)
                    }
                    state.copy(
                        course = state.course.copy(chapters = updatedChapters),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun selectChapter(chapterIndex: Int) {
        _uiState.update {
            it.copy(
                currentChapterIndex = chapterIndex,
                currentPageIndex = 0,
                scrollProgress = 0
            )
        }
    }

    fun onNextPage(): NextAction {
        val state = _uiState.value
        return if (!state.isLastPageInChapter) {
            _uiState.update {
                it.copy(currentPageIndex = it.currentPageIndex + 1, scrollProgress = 0)
            }
            NextAction.NextPage
        } else {
            markCurrentChapterCompleted()
            NextAction.ChapterCompleted
        }
    }

    fun advanceToNextChapter() {
        _uiState.update {
            it.copy(
                currentChapterIndex = it.currentChapterIndex + 1,
                currentPageIndex = 0,
                scrollProgress = 0
            )
        }
    }

    fun updateScrollProgress(progress: Int) {
        _uiState.update { it.copy(scrollProgress = progress) }
    }

    private fun markCurrentChapterCompleted() {
        val state = _uiState.value
        val chapter = state.currentChapter

        // Synchronous optimistic update
        _uiState.update { s ->
            val updatedChapters = s.course.chapters.toMutableList()
            updatedChapters[s.currentChapterIndex] =
                updatedChapters[s.currentChapterIndex].copy(isCompleted = true)
            s.copy(course = s.course.copy(chapters = updatedChapters))
        }

        // Async persist
        viewModelScope.launch {
            progressRepository.markChapterCompleted(
                courseId = state.course.id,
                chapterId = chapter.id
            )
        }
    }

    fun onPreviousPage(): PreviousAction {
        return if (_uiState.value.currentPageIndex > 0) {
            _uiState.update {
                it.copy(currentPageIndex = it.currentPageIndex - 1, scrollProgress = 0)
            }
            PreviousAction.PreviousPage
        } else {
            PreviousAction.FirstPage // already on first page → caller should pop to CourseList
        }
    }

    enum class PreviousAction {
        PreviousPage,
        FirstPage
    }



    enum class NextAction {
        NextPage,
        ChapterCompleted
    }

    companion object {
        fun factory(courseId: Int) = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                val repository = CourseProgressRepository(application)
                val course = CourseDataProvider.getCourseById(courseId)
                CourseViewModel(course, repository)
            }
        }

    }
}
