package org.andstatus.todoagenda

import org.andstatus.todoagenda.widget.CalendarEntry
import org.junit.Assert
import org.junit.Test

/**
 * @author yvolk@yurivolkov.com
 */
class WrongDatesLostEventsTest : BaseWidgetTest() {
    /**
     * https://github.com/plusonelabs/calendar-widget/issues/205
     */
    @Test
    fun testIssue205() {
        val method = "testIssue205"
        val inputs = provider!!.loadResultsAndSettings(
            org.andstatus.todoagenda.test.R.raw.wrong_dates_lost_events
        )
        provider!!.addResults(inputs)
        playResults(method)
        Assert.assertEquals("Number of entries", 11, getFactory().widgetEntries.size.toLong())
        Assert.assertEquals("On Saturday", "Maker Fair", (getFactory().widgetEntries[4] as CalendarEntry).event.title)
        Assert.assertEquals("On Saturday", 6, getFactory().widgetEntries[4].entryDate.dayOfWeek.toLong())
        Assert.assertEquals("On Sunday", "Ribakovs", (getFactory().widgetEntries[7] as CalendarEntry).event.title)
        Assert.assertEquals("On Sunday", 7, getFactory().widgetEntries[7].entryDate.dayOfWeek.toLong())
    }
}