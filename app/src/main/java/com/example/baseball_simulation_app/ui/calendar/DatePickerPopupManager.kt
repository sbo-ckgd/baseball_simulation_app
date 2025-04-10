package com.example.baseball_simulation_app.ui.calendar

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.PopupWindow
import java.util.Calendar

/**
 * DatePicker를 사용하는 팝업 관리자
 */
class DatePickerPopupManager(private val context: Context) {

    companion object {
        private const val FIXED_YEAR = 2024
        private val VALID_MONTHS = listOf(3, 4, 5, 6, 7, 8, 9, 10, 11) // 3월부터 11월까지만 허용
    }

    /**
     * 커스텀 DatePicker 팝업을 표시합니다
     */
    fun showDatePickerPopup(
        anchorView: View,
        currentDate: Calendar,
        onDateSelected: (year: Int, month: Int, day: Int) -> Unit
    ) {
        // 커스텀 DatePicker 뷰 생성
        val customDatePickerView = CustomDatePickerView(context).apply {
            setDate(
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )
            setOnDateSelectedListener { year, month, day ->
                onDateSelected(year, month, day)
            }
        }

        // 팝업 윈도우 생성
        val popupWindow = PopupWindow(
            customDatePickerView,
            anchorView.width,
            800, // DatePicker는 더 많은 공간 필요
            true
        )

        // 팝업 표시
        popupWindow.showAsDropDown(anchorView)
    }

    /**
     * DatePickerDialog를 표시합니다
     */
    // DatePickerPopupManager.kt
    fun showDatePickerDialog(
        calendar: Calendar,
        minDate: Long? = null,
        maxDate: Long? = null,
        dateSetListener: (year: Int, month: Int, dayOfMonth: Int) -> Unit
    ) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                dateSetListener(selectedYear, selectedMonth, selectedDay)
            },
            year,
            month,
            day
        )

        // 최소, 최대 날짜 설정
        minDate?.let { datePickerDialog.datePicker.minDate = it }
        maxDate?.let { datePickerDialog.datePicker.maxDate = it }

        datePickerDialog.show()
    }
}