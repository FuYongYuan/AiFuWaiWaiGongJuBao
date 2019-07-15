import schedule.*;
import schedule.service.JobService;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

public class TestCucumber {

	public static void main(String[] args) {

	}


	private static void assertEquals(Object d1, Object d2) {

		boolean pass = false;

		if (d1 == null || d2 == null) {
			pass = d1 == null && d2 == null;
		}
		else {
			pass = d1.equals(d2);
		}

		if (!pass) {
			throw new IndexOutOfBoundsException();
		}
	}

	private static void pass(String testCaseName) {
		System.out.println(testCaseName + " pass.");
	}

	public static void testDemo() {

		Job job = Job.with(() -> {
			System.out.println(LocalDateTime.now());
		}).when(
				Month.at(7, 8).with(
						Hour.at(11, 18).with(
							Minute.at(0, 59).with(
								Second.at(5),
								Second.at(10),
								Second.at(15),
								Second.at(20),
								Second.at(25),
								Second.at(30),
								Second.at(35),
								Second.at(40),
								Second.at(45),
								Second.at(50),
								Second.at(55)
							)
						)
					)
			);

		JobService s = new JobService();
		s.addJob(job);


		s.start();
	}

	public static void test() {
		testBVT();
		lastDayOfFebraryOfCommonYear();
		febrary28thOfLeapYear();
		lastDayOfFebraryOfLeapYear();
		weekDay();
		monthDayAndWeekDay();
		currentTimeBeforeWeekDay();
		currentTimeAfterWeekDay();
		twoSchedules();
		timer();
	}

	private static void testBVT() {

		Schedule s =
				Month.at(1, 5).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(5),
								Second.at(10),
								Second.at(15),
								Second.at(20),
								Second.at(30),
								Second.at(40),
								Second.at(50),
								Second.at(55)
							)
						)
					);

	}

	private static void lastDayOfFebraryOfCommonYear() {

		Schedule s =
				Month.at(1, 5).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					);


	}

	private static void febrary28thOfLeapYear() {

		Schedule s =
				Month.at(1, 5).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					);

	}

	private static void lastDayOfFebraryOfLeapYear() {

		Schedule s =
				Month.at(1, 5).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					);
	}

	private static void weekDay() {

		Schedule s =
				WeekDay.at(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					);

	}

	private static void monthDayAndWeekDay() {

		Schedule s =
			Month.at(5,7).with(
				WeekDay.at(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					)
				);

	}

	private static void currentTimeBeforeWeekDay() {

		Schedule s =
			Month.at(5,7).with(
				WeekDay.at(DayOfWeek.SATURDAY).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					)
			);


	}

	private static void currentTimeAfterWeekDay() {

		Schedule s =
			Month.at(5,7).with(
				WeekDay.at(DayOfWeek.TUESDAY).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					)
			);


	}

	private static void twoSchedules() {

		Schedule s =
			Month.at(5,7).with(
				WeekDay.at(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					),
				WeekDay.at(DayOfWeek.SATURDAY).with(
						Hour.at(12, 18).with(
							Minute.at(50, 59).with(
								Second.at(50, 55)
							)
						)
					)
			);


	}

	private static void timer() {

		Schedule s =
			Month.at(5,7).with(
					Hour.at(12, 18).with(
							Minute.at(5).with(
								Timer.duration(Duration.ofSeconds(5))
							),
							Minute.at(10).with(
								Timer.duration(Duration.ofSeconds(6))
							),
							Minute.at(20).with(
								Timer.duration(Duration.ofSeconds(10))
							)
						)
			);


	}
}
