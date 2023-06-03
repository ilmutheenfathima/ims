package com.company.ims.screen.calendar;

import com.company.ims.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Calendar;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UiController("CalendarScreen")
@UiDescriptor("calendar-screen.xml")
public class CalendarScreen extends Screen {

    private static final Logger logger = LoggerFactory.getLogger(CalendarScreen.class);

    private LocalDateTime todayDate;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
    @Autowired
    private Calendar<LocalDateTime> calendar;
    @Autowired
    private Label<String> monthLabel;

    @Autowired
    private CurrentAuthentication currentAuthentication;

    private List<UnifiedCalendarEvent> events;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        this.todayDate = LocalDateTime.now();
        this.startDay = todayDate.with(DayOfWeek.MONDAY);
        this.endDay = todayDate.with(DayOfWeek.SUNDAY);
        setCalendarRange();
        loadUserCalendarEvents();
    }

    private void setCalendarRange() {
        this.calendar.setStartDate(this.startDay);
        this.calendar.setEndDate(this.endDay);
        this.monthLabel.setValue(this.startDay.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + this.startDay.getYear());
    }

    @Subscribe("calendarPreviousBtn")
    public void onCalendarPreviousBtnClick(Button.ClickEvent event) {
        this.endDay = this.startDay.minusDays(1);
        this.startDay = this.endDay.with(DayOfWeek.MONDAY);
        setCalendarRange();
    }

    @Subscribe("calendarNextBtn")
    public void onCalendarNextBtnClick(Button.ClickEvent event) {
        this.startDay = this.endDay.plusDays(1);
        this.endDay = this.startDay.with(DayOfWeek.SUNDAY);
        setCalendarRange();
    }


    @Subscribe("todayBtn")
    public void onTodayBtnClick(Button.ClickEvent event) {
        this.startDay = todayDate.with(DayOfWeek.MONDAY);
        this.endDay = todayDate.with(DayOfWeek.SUNDAY);
        setCalendarRange();
    }

    private void loadUserCalendarEvents() {
        User user = (User) currentAuthentication.getUser();
        List<Classroom> classrooms;
        if (user instanceof Lecturer) {
            // --lecturer calendar events--
            // classrooms of lecturer
            classrooms = dataManager.load(Classroom.class)
                    .condition(
                            PropertyCondition.equal("lecturer", user)
                    ).fetchPlan("classroom-fetch-for-events")
                    .list();
        } else if (user instanceof Student) {
            // --student calendar events--
            // classrooms of lecturer
            classrooms = dataManager.load(Classroom.class)
                    .condition(
                            PropertyCondition.equal("enrolments.student", user)
                    )
                    .fetchPlan("classroom-fetch-for-events")
                    .list();

        } else {
            // --staff calendar events--
            // all classrooms
            classrooms = dataManager.load(Classroom.class)
                    .all()
                    .fetchPlan("classroom-fetch-for-events")
                    .list();
        }

        events = classrooms.stream()
                .map(this::convertClassroomsToEvents)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        addEventsToCalendar(events);
    }

    private List<UnifiedCalendarEvent> convertClassroomsToEvents(Classroom classroom) {
        List<UnifiedCalendarEvent> calendarEvents = new ArrayList<>();
        DayOfWeek dayOfWeek = null;
        if (classroom.getDay() != null) {
            try {
                dayOfWeek = DayOfWeek.valueOf(classroom.getDay().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                logger.error("Error converting classroom day to DayOfWeek", e);
            }
        }

        if (classroom.getStartDate() != null && classroom.getStartDate() != null
                && classroom.getStartTime() != null && classroom.getEndTime() != null && dayOfWeek != null) {

            LocalDate firstClassDate = classroom.getStartDate().with(dayOfWeek);
            if (firstClassDate.isBefore(classroom.getStartDate())) {
                firstClassDate = firstClassDate.plusDays(7);
            }
            LocalDateTime classStartTime = firstClassDate.atTime(classroom.getStartTime());
            LocalDateTime classEndTime = firstClassDate.atTime(classroom.getEndTime());

            while (!classStartTime.isAfter(classroom.getEndDate().atTime(LocalTime.MIDNIGHT))) {
                calendarEvents.add(UnifiedCalendarEvent.getEvent(classroom, classStartTime, classEndTime));

                classStartTime = classStartTime.plusDays(7);
                classEndTime = classEndTime.plusDays(7);
            }
        }
        return calendarEvents;
    }


    private void addEventsToCalendar(List<UnifiedCalendarEvent> events) {
        for (UnifiedCalendarEvent event : events) {
            calendar.getEventProvider().addEvent(event);
        }
    }
}