package com.coursework.featureEditBook.presentation.mapper

import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.domain.bookDetails.model.NamedItem
import com.coursework.featureEditBook.presentation.mapper.FilterViewStateMapper.Params
import com.coursework.utils.ParameterizedMapper

internal class FilterViewStateMapper : ParameterizedMapper<NamedItem, Params, FilterViewState> {

    data class Params(
        val isSelected: Boolean,
    )

    override fun map(from: NamedItem, params: Params): FilterViewState {
        return FilterViewState(
            id = from.id,
            displayName = StringValue.RawString(from.displayName),
            isSelected = params.isSelected
        )
    }
}