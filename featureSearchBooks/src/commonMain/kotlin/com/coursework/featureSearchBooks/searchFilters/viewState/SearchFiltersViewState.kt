package com.coursework.featureSearchBooks.searchFilters.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.corePresentation.viewState.emptyComposeList

@Immutable
data class SearchFiltersViewState(
    val authorInput: String = "",
    val publicationYearInput: String = "",
    val topCategories: ComposeList<FilterViewState> = emptyComposeList(),
    val topLanguages: ComposeList<FilterViewState> = emptyComposeList(),
    val availabilities: ComposeList<FilterViewState> = emptyComposeList(),
    val topTeachers: ComposeList<FilterViewState> = emptyComposeList(),
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
) {

    companion object {

        val MockCategories = listOf(
            FilterViewState(
                id = 1,
                displayName = StringValue.RawString("Physics"),
                isSelected = false,
            ),
            FilterViewState(
                id = 2,
                displayName = StringValue.RawString("Analytic Geometry and Linear Algebra"),
                isSelected = false,
            ),
            FilterViewState(
                id = 3,
                displayName = StringValue.RawString("Programming"),
                isSelected = false,
            ),
            FilterViewState(
                id = 4,
                displayName = StringValue.RawString("Philosophy"),
                isSelected = false,
            ),
            FilterViewState(
                id = 5,
                displayName = StringValue.RawString("History"),
                isSelected = false,
            ),
            FilterViewState(
                id = 6,
                displayName = StringValue.RawString("Network"),
                isSelected = false,
            ),
        )
        val MockLanguages = listOf(
            FilterViewState(
                id = 1,
                displayName = StringValue.RawString("Armenian"),
                isSelected = false,
            ),
            FilterViewState(
                id = 2,
                displayName = StringValue.RawString("Russian"),
                isSelected = false,
            ),
            FilterViewState(
                id = 3,
                displayName = StringValue.RawString("English"),
                isSelected = false,
            ),
        )
        val MockTeachers = listOf(
            FilterViewState(
                id = 1,
                displayName = StringValue.RawString("E. Hovhannisyan"),
                isSelected = false,
            ),
            FilterViewState(
                id = 2,
                displayName = StringValue.RawString("T. Ganovich"),
                isSelected = false,
            ),
            FilterViewState(
                id = 3,
                displayName = StringValue.RawString("Tomeyan"),
                isSelected = false,
            ),
            FilterViewState(
                id = 4,
                displayName = StringValue.RawString("A. Baghdasaryan"),
                isSelected = false,
            ),
            FilterViewState(
                id = 5,
                displayName = StringValue.RawString("L. Andreasyan"),
                isSelected = false,
            ),
            FilterViewState(
                id = 6,
                displayName = StringValue.RawString("E. Virabyan"),
                isSelected = false,
            ),
        )
    }
}
