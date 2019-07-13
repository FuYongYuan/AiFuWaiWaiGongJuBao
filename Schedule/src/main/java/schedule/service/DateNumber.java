package schedule.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

class DateNumber {

    public DateNumberDef def;

    public int year;

    public int month;

    public int weekDay;

    public int day;

    public int hour;

    public int minute;

    public int second;

    public int nano;


    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }

    public static DateNumber fromLocalDateTime(LocalDateTime d) {
        DateNumber instance = new DateNumber();
        instance.year = d.getYear();
        instance.month = d.getMonthValue();
        instance.day = d.getDayOfMonth();
        instance.weekDay = d.getDayOfWeek().getValue();
        instance.hour = d.getHour();
        instance.minute = d.getMinute();
        instance.second = d.getSecond();
        instance.nano = d.getNano();
        instance.def = new DateNumberDef();
        return instance;
    }

    public static DateNumber of(int year, int month, int day, int hour, int minute, int second, int nano) {
        DateNumber instance = new DateNumber();
        instance.year = year;
        instance.month = month;
        instance.day = day;
        instance.weekDay = LocalDate.of(year, month, day).getDayOfWeek().getValue();
        instance.hour = hour;
        instance.minute = minute;
        instance.second = second;
        instance.nano = nano;
        instance.def = new DateNumberDef();
        return instance;
    }


    public DateNumber withNano(int nano) {
        DateNumber d = new DateNumber();
        d.year = year;
        d.month = month;
        d.weekDay = weekDay;
        d.day = day;
        d.hour = hour;
        d.minute = minute;
        d.second = second;
        d.nano = nano;
        d.def = def;
        return d;
    }

    public DateNumber withSecond(int second) {
        DateNumber d = new DateNumber();
        d.year = year;
        d.month = month;
        d.weekDay = weekDay;
        d.day = day;
        d.hour = hour;
        d.minute = minute;
        d.second = second;
        d.nano = nano;
        d.def = def;
        return d;
    }

    public DateNumber withMinute(int minute) {
        DateNumber d = new DateNumber();
        d.year = year;
        d.month = month;
        d.weekDay = weekDay;
        d.day = day;
        d.hour = hour;
        d.minute = minute;
        d.second = second;
        d.nano = nano;
        d.def = def;
        return d;
    }

    public DateNumber withHour(int hour) {
        DateNumber d = new DateNumber();
        d.year = year;
        d.month = month;
        d.weekDay = weekDay;
        d.day = day;
        d.hour = hour;
        d.minute = minute;
        d.second = second;
        d.nano = nano;
        d.def = def;
        return d;
    }

    public DateNumber withDayOfMonth(int day) {
        DateNumber d = new DateNumber();
        d.year = year;
        d.month = month;
        d.weekDay = LocalDate.of(year, month, day).getDayOfWeek().getValue();
        d.day = day;
        d.hour = hour;
        d.minute = minute;
        d.second = second;
        d.nano = nano;
        d.def = def;
        return d;
    }

    public DateNumber withMonth(int month) {
        DateNumber d = new DateNumber();
        d.year = year;
        d.month = month;
        d.weekDay = weekDay;
        d.day = day;
        d.hour = hour;
        d.minute = minute;
        d.second = second;
        d.nano = nano;
        d.def = def;
        return d;
    }

    public DateNumber withYear(int year) {
        DateNumber d = new DateNumber();
        d.year = year;
        d.month = month;
        d.weekDay = weekDay;
        d.day = day;
        d.hour = hour;
        d.minute = minute;
        d.second = second;
        d.nano = nano;
        d.def = def;
        return d;
    }


    public DateNumber plusNanos(int nanos) {
        return add(nanos, 6);
    }

    public DateNumber plusSeconds(int seconds) {
        return add(seconds, 5);
    }

    public DateNumber plusMinutes(int minutes) {
        return add(minutes, 4);
    }

    public DateNumber plusHours(int hours) {
        return add(hours, 3);
    }

    public DateNumber plusDays(int days) {
        return add(days, 2);
    }

    public DateNumber plusMonths(int months) {
        return add(months, 1);
    }

    public DateNumber plusYears(int years) {
        return add(years, 0);
    }


    private DateNumber add(int n, int field) {

        DateNumber d = new DateNumber();
        d.def = def;

        AddResult result = new AddResult();
        result.overflow = n;

        if (field >= 6) {
            result = def.nano.add(nano, result.overflow);
            d.nano = result.value;
        } else {
            d.nano = nano;
        }

        if (field >= 5) {
            result = def.second.add(second, result.overflow);
            d.second = result.value;
        } else {
            d.second = second;
        }

        if (field >= 4) {
            result = def.minute.add(minute, result.overflow);
            d.minute = result.value;
        } else {
            d.minute = minute;
        }

        if (field >= 3) {
            result = def.hour.add(hour, result.overflow);
            d.hour = result.value;
        } else {
            d.hour = hour;
        }

        if (field >= 2) {
            result = def.day.add(day, result.overflow);
            d.day = result.value;
        } else {
            d.day = day;
        }

        if (field >= 1) {
            result = def.month.add(month, result.overflow);
            d.month = result.value;
        } else {
            d.month = month;
        }

        d.year = year + result.overflow;


        while (true) {

            int dayCount = GregorianCalendar.getDaysOfMonth(d.month, d.year);
            if (d.day > dayCount) {
                d.month++;
                d.day -= dayCount;
            }


            int weekDay = LocalDate.of(d.year, d.month, d.day).getDayOfWeek().getValue();
            int adjustDays = 0;

            if (weekDay < def.week.getLower()) {
                adjustDays = def.week.getLower() - weekDay;
            } else if (weekDay > def.week.getUpper()) {
                adjustDays = def.week.getLower() - weekDay + 7;
            }


            if (adjustDays == 0) {
                break;
            }


            result = def.day.add(d.day, adjustDays);
            d.day = result.value;

            result = def.month.add(d.month, result.overflow);
            d.month = result.value;

            d.year += result.overflow;
        }


        d.weekDay = LocalDate.of(d.year, d.month, d.day).getDayOfWeek().getValue();
        return d;
    }
}
